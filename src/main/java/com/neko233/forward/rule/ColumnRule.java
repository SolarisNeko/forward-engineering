package com.neko233.forward.rule;

import com.neko233.forward.annotation.Column;

/**
 * @title:
 * @description:
 * @author: SolarisNeko
 * @date: 2021/7/4
 */
public class ColumnRule {
    /**
     * 基于 Column 规则
     * 构建 entity.field -> Column DDL 的 SQL
     */
    public static String generateSqlByAnnotation(Column column, String columnName, String defaultColumnType) {
        String columnSQL;

        String type = column.type();
        boolean isPk = column.PK();
        boolean isAutoIncrement = column.autoIncrement();
        boolean isNotNull = column.notNull();
        String comment = column.comment();

        if (type.isEmpty()) {
            // @Column.type 无输入 | 尾部空格, 必须保留
            columnSQL = "`" + columnName + "` " + defaultColumnType + " ";
        } else {
            // @Column.type 有输入, 组装 type | 尾部空格, 必须保留
            columnSQL = "`" + columnName + "` " + type + " ";
        }
        if (isPk) {
            columnSQL += "Primary Key ";
        } else {
            if (isNotNull) {
                columnSQL += "Not Null ";
            }
        }
        if (isAutoIncrement) {
            columnSQL += "auto_increment ";
        }
        if (!comment.isEmpty()) {
            columnSQL += ("comment '" + comment + "' ");
        }

        return columnSQL;
    }

    public static String generateSqlByDefault(String columnName, String defaultColumnTypeName) {
        // default 没有注释
        return "`" + columnName + "` " + defaultColumnTypeName;
    }
}
