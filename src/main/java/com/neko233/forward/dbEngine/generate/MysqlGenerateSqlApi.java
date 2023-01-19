package com.neko233.forward.dbEngine.generate;

import com.neko233.forward.dbEngine.GenerateSqlApi;
import com.neko233.forward.dbEngine.constant.MysqlTypeEnum;
import com.neko233.forward.dbEngine.metadata.ColumnMetaData;
import com.neko233.forward.dbEngine.metadata.TableMetaData;

/**
 * @title:
 * @description:
 * @author: SolarisNeko
 * @date: 2021/7/11
 */
public class MysqlGenerateSqlApi implements GenerateSqlApi {


    @Override
    public String generateTableSql(TableMetaData tableMetaData, ColumnMetaData columnMetaData) {
        StringBuilder sqlBuilder = new StringBuilder();
        // table header
        sqlBuilder.append("Drop table if exists ")
                .append(tableMetaData.getTableName().toLowerCase())
                .append(";\n")
                .append("Create Table if not exists ")
                .append(tableMetaData.getTableName().toLowerCase())
                .append("( ");
        for (String columnSql : generateColumnSqlList(columnMetaData)) {
            sqlBuilder.append(columnSql);
        }
        // table foot
        sqlBuilder.append(" ) ");
        return sqlBuilder.toString();
    }

    @Override
    public String tableConfig(TableMetaData tableMetaData) {
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
    public String fieldTypeToColumnType(Class<?> fieldType) {
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
