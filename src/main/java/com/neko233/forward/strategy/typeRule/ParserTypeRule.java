package com.neko233.forward.strategy.typeRule;

import com.neko233.forward.constant.MysqlTypeEnum;

/**
 * @title:
 * @description:
 * @author: SolarisNeko
 * @date: 2021/7/4
 */
public class ParserTypeRule {

    public static String chooseDefaultColumnTypeByType(String classType) {
        switch (classType) {
            case "int":
            case "java.lang.Integer": {
                return MysqlTypeEnum.INT.typeName;
            }
            case "float":
            case "java.lang.Float": {
                return MysqlTypeEnum.FLOAT.typeName;
            }
            case "double":
            case "java.lang.Double": {
                return MysqlTypeEnum.DOUBLE.typeName;
            }
            case "long":
            case "java.lang.Long": {
                return MysqlTypeEnum.BIG_INT.typeName;
            }
            case "java.lang.String": {
                return MysqlTypeEnum.VARCHAR.typeName;
            }
            case "java.math.BigDecimal": {
                return MysqlTypeEnum.DECIMAL.typeName;
            }
            case "boolean":
            case "java.lang.Boolean": {
                return MysqlTypeEnum.BOOLEAN.typeName;
            }
            case "date":
            case "java.util.Date": {
                // datetime = "yyyy-MM-dd hh:mm:ss"
                return MysqlTypeEnum.DATE.typeName;
            }
            case "byte":
            case "java.lang.Byte": {
                return MysqlTypeEnum.BLOB.typeName;
            }
            default: {
                return MysqlTypeEnum.VARCHAR.typeName;
            }
        }
    }
}
