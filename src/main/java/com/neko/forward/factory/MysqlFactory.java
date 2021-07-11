package com.neko.forward.factory;

import com.neko.pojo.dto.TableDTO;

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
    @Override
    public String getCreateTableSQLByClassName(Class<?> targetClass) {

        // 1、table 信息
        TableDTO tableDTO = TableFactory.getTableNameByClass(targetClass);

        // 2、column 信息
        List<String> columnSqlList = ColumnFactory.getColumnSqlList(targetClass);

        // 3、开始构建 Table SQL
        String tableSQL = this.makeTableSqlForMySQL(tableDTO, columnSqlList);;


        return tableSQL;
    }

}
