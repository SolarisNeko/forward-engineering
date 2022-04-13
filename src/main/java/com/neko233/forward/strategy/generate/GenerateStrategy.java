package com.neko233.forward.strategy.generate;

import com.neko233.forward.annotation.Column;
import com.neko233.forward.constant.DbType;
import com.neko233.forward.metadata.ColumnMetaData;
import com.neko233.forward.metadata.TableMetaData;
import com.neko233.forward.rule.ColumnRule;
import com.neko233.forward.util.CamelCaseUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Template + Strategy 构建 SQL
 * @author SolarisNeko
 * @date 2021/7/4
 */
public interface GenerateStrategy {

    /**
     * 策略容器
     */
    Map<String, GenerateStrategy> STRATEGY_MAP = new HashMap<String, GenerateStrategy>() {{
        put(DbType.MYSQL.getName(), new MysqlGenerateStrategy());
        put(DbType.HIVE.getName(), new HiveGenerateStrategy());
    }};

    /**
     * 注册自定义的策略
     * @param key 策略名
     * @param strategy 策略API
     */
    static void registerStrategy(String key, GenerateStrategy strategy) {
        STRATEGY_MAP.merge(key, strategy, (v1, v2) -> {
            throw new RuntimeException("Already have this key = " + key);
        });
    }

    /**
     * 生成对应的工厂
     * @param dbType 选择的db类型
     * @return 生成策略
     * */
    static GenerateStrategy getStrategyByDbType(String dbType) {
        GenerateStrategy generateStrategy = STRATEGY_MAP.get(dbType);
        if (generateStrategy == null) {
            throw new RuntimeException("Un-support/Non-register Strategy");
        }
        return generateStrategy;
    }

    /**
     * 获取要调用的 tableDTO, columnList 信息
     * @param aClass 要生成的类
     * @return 建表 SQL
     */
    default String generateCreateSqlByClass(Class<?> aClass) {
        // 1、table 信息
        TableMetaData tableMetaData = TableMetaData.getTableMetaDataByClass(aClass);
        // 2、column 信息
        ColumnMetaData columnMetaData = ColumnMetaData.getColumnMetaDataByClass(aClass);
        // 3、开始构建 Table SQL
        return generateTableSql(tableMetaData, columnMetaData) + generateSuffixConfig(tableMetaData) + ";\r\n";
    }


    /**
     * 制造 Create Table SQL - MySQL
     * @param tableMetaData 表元数据
     * @param columnMetaData 列元数据
     * @return 完整的 Table SQL
     */
    default String generateTableSql(TableMetaData tableMetaData, ColumnMetaData columnMetaData) {
        StringBuilder sqlBuilder = new StringBuilder();
        // table header
        sqlBuilder.append("Drop table if exists ")
                .append(tableMetaData.getTableName().toLowerCase())
                .append(";\n")
                .append("Create Table if not exists ")
                .append(tableMetaData.getTableName().toLowerCase())
                .append("( ");
        for (String columnSql : getColumnSqlList(columnMetaData)) {
            sqlBuilder.append(columnSql);
        }
        // table foot
        sqlBuilder.append(" ) ");
        return sqlBuilder.toString();
    }

    /**
     * 获取列 SQL
     * @param columnMetaData 列元数据
     * @return 多条列SQL
     */
    default List<String> getColumnSqlList(ColumnMetaData columnMetaData) {
        List<String> columnSqlList = new ArrayList<>();
        List<Field> columnList = columnMetaData.getColumnList();

        // 处理 field
        for (int i = 0; i < columnList.size(); i++) {
            // get field
            Field field = columnList.get(i);

            // get field.type
            String defaultColumnTypeName = parseColumnRule(field.getType());

            // 构建单个 column SQL 需要的元素
            String columnName = CamelCaseUtil.toBigCamelLowerName(field.getName());

            // 批量处理 field 的 @interface = @Column 的部分。
            String columnSql;
            Column column = field.getAnnotation(Column.class);
            if (column == null) {
                // 无 @Column = 无侵入式
                columnSql = ColumnRule.generateSqlByDefault(columnName, defaultColumnTypeName);
            } else {
                // 有 @Column = 自定义
                columnSql = ColumnRule.generateSqlByAnnotation(column, columnName, defaultColumnTypeName);
            }
            // 最后1个Column, 不加 ","
            if (i != (columnList.size() - 1)) {
                columnSql += ", ";
            }
            columnSqlList.add(columnSql);
        }
        return columnSqlList;
    }

    /**
     * 解析 column 的规则
     * @param fieldType 类型名字
     * @return SQL 类型名
     */
    String parseColumnRule(Class<?> fieldType);

    /**
     * 生成后缀内容
     * @param tableMetaData 表元数据
     * @return 文本
     */
    String generateSuffixConfig(TableMetaData tableMetaData);



}
