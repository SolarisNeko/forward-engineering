package com.neko233.forward.factory;

import com.neko233.forward.entity.TableMetaData;

import java.util.List;

/**
 * @title:
 * @description:
 * @author: SolarisNeko
 * @date: 2021/7/11
 */
public class MysqlFactory extends DatabaseAbstractFactory {


    /**
     * 制造 Create Table SQL - MySQL
     * */
    @Override
    public String makeTableSqlForMySQL(TableMetaData tableMetaData, List<String> columnSqlList) {
        StringBuilder sqlBuilder = new StringBuilder();

        String engine = tableMetaData.getEngine();
        String charset = tableMetaData.getCharset();

        // table header
        sqlBuilder.append("Create Table " + tableMetaData.getTableName().toLowerCase() + "( " );
        for (String columnSQL : columnSqlList) {
            sqlBuilder.append(columnSQL);
        }
        // table foot
        sqlBuilder.append(" ) ");
        if (!engine.isEmpty()) {
            sqlBuilder.append("engine = " + engine + ", ");
        }
        if (!charset.isEmpty()) {
            sqlBuilder.append("charset = " + charset);
        }
        sqlBuilder.append(";");

        // Windows 换行 | SQL之间, 有1空行
        sqlBuilder.append("\r\n\r\n");

        return sqlBuilder.toString();
    }


    /**
     * 获取要调用的 tableDTO, columnList 信息
     * */
    @Override
    public String getCreateTableSQLByClassName(Class<?> targetClass) {

        // 1、table 信息
        TableMetaData tableMetaData = TableFactory.getTableNameByClass(targetClass);

        // 2、column 信息
        List<String> columnSqlList = ColumnFactory.getColumnSqlList(targetClass);

        // 3、开始构建 Table SQL
        String tableSQL = this.makeTableSqlForMySQL(tableMetaData, columnSqlList);;


        return tableSQL;
    }

}
