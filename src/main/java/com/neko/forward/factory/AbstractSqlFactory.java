package com.neko.forward.factory;

import com.neko.forward.constant.DbType;
import com.neko.pojo.dto.TableDTO;

import java.util.List;

/**
 * @title: 构建 SQL
 * @description: 抽象工厂 - 构建 DDL, DML
 * @author: SolarisNeko
 * @date: 2021/7/4
 */
public abstract class AbstractSqlFactory {


    public static AbstractSqlFactory getFactoryByDbType(DbType dbType) {
        switch (dbType) {
            case MYSQL: {
                return new MysqlFactory();
            }
            default: {
                return new MysqlFactory();
            }
        }
    }

    /**
     * 制造 Create Table SQL - MySQL
     * */
    public String makeTableSqlForMySQL(TableDTO tableDTO, List<String> columnSqlList) {
        // 后续考虑多线程扫描包
        StringBuffer sb = new StringBuffer();

        String engine = tableDTO.getEngine();
        String charset = tableDTO.getCharset();

        // table header
        sb.append("Create Table " + tableDTO.getTableName() + "( " );
        for (String columnSQL : columnSqlList) {
            sb.append(columnSQL);
        }
        // table foot
        sb.append(" ) ");
        if (!engine.isEmpty()) {
            sb.append("engine = " + engine + ", ");
        }
        if (!charset.isEmpty()) {
            sb.append("charset = " + charset);
        }
        sb.append(";");

        // Windows 换行 | SQL之间, 有1空行
        sb.append("\r\n\r\n");

        return sb.toString();
    }


    /**
     * 获取要调用的 tableDTO, columnList 信息
     * */
    public String getCreateTableSQLByClassName(Class<?> targetClass) {

        // 1、table
        TableDTO tableDTO = TableFactory.getTableNameByClass(targetClass);

        // 2、table.column
        List<String> columnSqlList = ColumnFactory.getColumnSqlList(targetClass);

        // 构建 Table SQL
        String tableSQL = this.makeTableSqlForMySQL(tableDTO, columnSqlList);;


        return tableSQL;
    }
}
