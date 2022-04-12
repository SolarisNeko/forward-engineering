package com.neko233.forward.strategy.generate;

import com.neko233.forward.annotation.hive.HiveConfig;
import com.neko233.forward.constant.HiveTypeEnum;
import com.neko233.forward.metadata.TableMetaData;
import com.neko233.forward.util.CharacterUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author SolarisNeko
 * Date on 2022-04-12
 */
public class HiveGenerateStrategy implements GenerateStrategy {

    @Override
    public String generateSuffixConfig(TableMetaData tableMetaData) {
        Class<?> originalClass = tableMetaData.getOriginalClass();
        HiveConfig annotation = originalClass.getAnnotation(HiveConfig.class);
        String terminatedBy;
        String storeAs;
        String location;
        String tblProperties;
        if (annotation == null) {
            terminatedBy = ",";
            storeAs = "textfile";
            location = "/" + CharacterUtil.toBigCamelLowerName(originalClass.getSimpleName());
            tblProperties = "";
        } else {
            terminatedBy = annotation.terminatedBy();
            storeAs = annotation.storedAs();
            location = annotation.location();
            tblProperties = annotation.tblProperties();
        }
        checkHiveParams(originalClass, terminatedBy, storeAs, location);
        return "\nROW FORMAT delimited fields terminated by '" + terminatedBy + "'\n" +
                "STORED AS " + storeAs + "\n" +
                "LOCATION  '" + location + "'\n" +
                "tblProperties( " + tblProperties + " )";
    }


    @Override
    public String parseColumnRule(Class<?> fieldType) {
        switch (fieldType.getTypeName()) {
            case "int":
            case "java.lang.Integer": {
                return HiveTypeEnum.INT.typeName;
            }
            case "float":
            case "java.lang.Float": {
                return HiveTypeEnum.FLOAT.typeName;
            }
            case "double":
            case "java.lang.Double": {
                return HiveTypeEnum.DOUBLE.typeName;
            }
            case "long":
            case "java.lang.Long": {
                return HiveTypeEnum.BIGINT.typeName;
            }
            case "java.lang.String": {
                return HiveTypeEnum.STRING.typeName;
            }
            case "java.math.BigDecimal": {
                return HiveTypeEnum.DECIMAL.typeName;
            }
            case "boolean":
            case "java.lang.Boolean": {
                return HiveTypeEnum.BOOLEAN.typeName;
            }
            case "date":
            case "java.util.Date": {
                return HiveTypeEnum.DATE.typeName;
            }
            case "byte":
            case "java.lang.Byte": {
                return HiveTypeEnum.BYTE.typeName;
            }
            default: {
                throw new RuntimeException("暂不支持该类型, 请自己实现..");
            }
        }
    }

    private void checkHiveParams(Class<?> originalClass, String terminatedBy, String storeAs, String location) {
        if (StringUtils.isBlank(terminatedBy)) {
            throw new RuntimeException("terminatedBy is blank. class = " + originalClass.getName());
        }
        if (StringUtils.isBlank(storeAs)) {
            throw new RuntimeException("storeAs is blank. class = " + originalClass.getName());
        }
        if (StringUtils.isBlank(location)) {
            throw new RuntimeException("location is blank. class = " + originalClass.getName());
        }
    }

}
