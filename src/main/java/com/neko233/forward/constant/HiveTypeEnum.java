package com.neko233.forward.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @title:
 * @description:
 * @author: SolarisNeko
 * @date: 2021/7/5
 */
@Getter
@AllArgsConstructor
public enum HiveTypeEnum {
    INT("int"),
    FLOAT("float"),
    DOUBLE("double"),
    BIGINT("bigint"),
    STRING("string"),
    DECIMAL("decimal"),
    BOOLEAN("boolean"),
    DATE("date"),
    BYTE("byte"),
    ;

    public String typeName;

}
