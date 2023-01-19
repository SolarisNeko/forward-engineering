package com.neko233.forward.dbEngine.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @date 2021/7/4
 * @author SolarisNeko
 */
@Getter
@AllArgsConstructor
public enum DbType {

    MYSQL("mysql"),
    HIVE("hive"),
//    ORACLE("oracle"),
//    SQLSERVER("sqlserver"),
//    POSTGRE("postgre"),
//    GAUSSDB("guass")
    ;

    private String name;

}
