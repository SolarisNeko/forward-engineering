package com.neko233.forward.strategy.generate;

import com.neko233.forward.constant.MysqlTypeEnum;
import com.neko233.forward.metadata.ColumnMetaData;
import com.neko233.forward.metadata.TableMetaData;

/**
 * @title:
 * @description:
 * @author: SolarisNeko
 * @date: 2021/7/11
 */
public class MysqlGenerateStrategy implements GenerateStrategy {

    @Override
    public String generateSuffixConfig(TableMetaData tableMetaData) {
        StringBuilder suffixConfigBuilder = new StringBuilder();
        if (!tableMetaData.getEngine().isEmpty()) {
            suffixConfigBuilder.append("engine = ").append(tableMetaData.getEngine()).append(", ");
        }
        if (!tableMetaData.getCharset().isEmpty()) {
            suffixConfigBuilder.append("charset = ").append(tableMetaData.getCharset());
        }
        return suffixConfigBuilder.toString();
    }

    @Override
    public String parseColumnRule(Class<?> fieldType) {
        switch (fieldType.getTypeName()) {
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
