package com.neko233.forward.dbEngine;

import com.neko233.forward.dbEngine.annotation.Column;
import com.neko233.forward.dbEngine.constant.DbType;
import com.neko233.forward.dbEngine.generate.HiveGenerateSqlApi;
import com.neko233.forward.dbEngine.generate.MysqlGenerateSqlApi;
import com.neko233.forward.dbEngine.rule.DefaultColumnRule;
import com.neko233.forward.dbEngine.metadata.ColumnMetaData;
import com.neko233.forward.dbEngine.metadata.TableMetaData;
import com.neko233.forward.util.CamelCaseUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Template + Strategy 构建 SQL
 *
 * @author SolarisNeko
 * date on 2021/7/4
 */
public interface GenerateSqlApi {

    // ----------------------------------------  API  --------------------------------------------------


    /**
     * 获取要调用的 tableDTO, columnList 信息
     *
     * @param aClass 要生成的类
     * @return 建表 SQL
     */
    default String generateCreateSqlByClass(Class<?> aClass) {
        // 1、table 信息
        TableMetaData tableMetaData = TableMetaData.getTableMetaDataByClass(aClass);
        // 2、column 信息
        ColumnMetaData columnMetaData = ColumnMetaData.getColumnMetaDataByClass(aClass);
        // 3、开始构建 Table SQL
        return generateTableSql(tableMetaData, columnMetaData) + tableConfig(tableMetaData) + ";\r\n";
    }


    /**
     * 制造 Create Table SQL - MySQL
     *
     * @param tableMetaData  表元数据
     * @param columnMetaData 列元数据
     * @return 完整的 Table SQL
     */
    String generateTableSql(TableMetaData tableMetaData, ColumnMetaData columnMetaData);


    /**
     * object field type -> column type name
     *
     * @param fieldType 字段类型
     * @return SQL 类型名
     */
    String fieldTypeToColumnType(Class<?> fieldType);

    /**
     * 表配置.
     *
     * @param tableMetaData 表元数据
     * @return 生成用于 create Table (...) ${here} 部分的 config 内容. 从而定制不同的 DB Engine / config
     */
    String tableConfig(TableMetaData tableMetaData);

    /**
     * 获取列 SQL
     *
     * @param columnMetaData 列元数据
     * @return 多条列SQL
     */
    default List<String> generateColumnSqlList(ColumnMetaData columnMetaData) {
        List<String> columnSqlList = new ArrayList<>();
        List<Field> fieldList = columnMetaData.getFieldList();

        // 处理 field
        for (int i = 0; i < fieldList.size(); i++) {
            // get field
            Field field = fieldList.get(i);

            // get field.type
            String defaultColumnTypeName = fieldTypeToColumnType(field.getType());

            // 构建单个 column SQL 需要的元素
            String columnName = fieldNameToColumnName(field.getName());

            // 批量处理 field 的 @interface = @Column 的部分。
            String columnSql;
            Column column = field.getAnnotation(Column.class);
            if (column == null) {
                // 无 @Column = 无侵入式
                columnSql = generateColumnSqlByDefault(columnName, defaultColumnTypeName);
            } else {
                // 有 @Column = 自定义
                columnSql = generateColumnSqlByAnnotation(columnName, column, defaultColumnTypeName);
            }
            // 最后1个Column, 不加 ","
            if (i != (fieldList.size() - 1)) {
                columnSql += ", ";
            }
            columnSqlList.add(columnSql);
        }
        return columnSqlList;
    }

    /**
     * 基于 @Column 生成 column SQL
     *
     * @param columnName            列名
     * @param columnAnnotation      注解
     * @param defaultColumnTypeName 默认类型名
     * @return column SQL. demo = `id` bigint auto_increment primary key
     */
    default String generateColumnSqlByAnnotation(String columnName, Column columnAnnotation, String defaultColumnTypeName) {
        return DefaultColumnRule.generateSqlByAnnotation(columnName, columnAnnotation, defaultColumnTypeName);
    }

    /**
     * 基于 @Column 生成 column SQL
     *
     * @param columnName            列名
     * @param defaultColumnTypeName 默认类型名
     * @return column SQL. demo = `id` bigint auto_increment primary key
     */
    default String generateColumnSqlByDefault(String columnName, String defaultColumnTypeName) {
        return DefaultColumnRule.generateSqlByDefault(columnName, defaultColumnTypeName);
    }

    /**
     * field Name -> Column Name, you can set your rule~
     *
     * @param fieldName 字段名
     * @return columnName
     */
    default String fieldNameToColumnName(String fieldName) {
        return CamelCaseUtil.toBigCamelLowerName(fieldName);
    }


}
