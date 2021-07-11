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
public abstract class DatabaseAbstractFactory {

    /**
     * 生成对应的工厂
     * */
    public static DatabaseAbstractFactory getFactoryByDbType(DbType dbType) {
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
    public abstract String makeTableSqlForMySQL(TableDTO tableDTO, List<String> columnSqlList);

    /**
     * 获取要调用的 tableDTO, columnList 信息
     * */
    public abstract String getCreateTableSQLByClassName(Class<?> targetClass);
}
