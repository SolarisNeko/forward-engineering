package com.neko.forward.constant;

/**
 * @title:
 * @description:
 * @author: SolarisNeko
 * @date: 2021/7/5
 */
public enum ColumnTypeEnum {
    INT("int"),
    FLOAT("float"),
    DOUBLE("double"),
    BIG_INT("bigint"),
    VARCHAR("varchar(255)"),
    DECIMAL("decimal"),
    BOOLEAN("boolean"),
    DATE("datetime"),
    BLOB("blob"),
    ;


    public String typeName;

    private ColumnTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
