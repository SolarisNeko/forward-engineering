package com.neko233.forward.factory;

import com.neko233.forward.annotation.Column;
import com.neko233.forward.rule.ColumnRule;
import com.neko233.forward.rule.ParserTypeRule;
import com.neko233.forward.util.CharacterUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @title:
 * @description:
 * @author: SolarisNeko
 * @date: 2021/7/4
 */
public class ColumnFactory {
    /**
     * return List<String>
     * 每个 String 都是 entity.field 对应的 建表SQL
     */
    public static List<String> getColumnSqlList(Class<?> targetClass) {
        List<String> columnSqlList = new ArrayList<>();

        // get Field[]
        Field[] fields = targetClass.getDeclaredFields();

        // 处理 field
        for (int i = 0; i < fields.length; i++) {
            // get field
            Field field = fields[i];

            // get field.type
            Class<?> type = field.getType();
            String defaultColumnTypeName = ParserTypeRule.chooseDefaultColumnTypeByType(type.getName());


            /** 构建单个 column SQL 需要的元素 */
            String fieldName = field.getName();
            String columnName = CharacterUtil.toBigCamelLowerName(fieldName);

            // 批量处理 field 的 @interface = @Column 的部分。
            String columnSQL = "";
            Column column = field.getAnnotation(Column.class);
            if (column == null) {
                // 无 @Column = 无侵入式
                columnSQL = ColumnRule.buildDefaultColumnSql(columnName, defaultColumnTypeName);
            } else {
                // 有 @Column
                columnSQL = ColumnRule.buildColumnSqlonRule(column, columnName, defaultColumnTypeName);
            }

            /** 最后1个Column, 不加 "," */
            if (i != (fields.length - 1)) {
                columnSQL += ", ";
            }

            // 添加1个 Column 的 DDL SQL
            columnSqlList.add(columnSQL);
        }

        return columnSqlList;
    }
}
