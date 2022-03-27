package com.neko233.forward.entity;

import lombok.Data;

/**
 * @title:
 * @description:
 * @author: SolarisNeko
 * @date: 2021/7/11
 */
@Data
public class TableMetaData {

    private String tableName;

    private String charset;

    private String engine;

    private String comment;

}
