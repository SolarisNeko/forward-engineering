package com.neko233.forward.dbEngine;

import com.neko233.forward.dbEngine.constant.DbType;
import com.neko233.forward.dbEngine.generate.HiveGenerateSqlApi;
import com.neko233.forward.dbEngine.generate.MysqlGenerateSqlApi;

import java.util.HashMap;
import java.util.Map;

/**
 * Template + Strategy 构建 SQL
 *
 * @author SolarisNeko
 * @date 2021/7/4
 */
public interface GenerateSqlApiFactory {

    /**
     * 策略容器
     */
    Map<String, GenerateSqlApi> STRATEGY_MAP = new HashMap<String, GenerateSqlApi>() {{
        put(DbType.MYSQL.getName(), new MysqlGenerateSqlApi());
        put(DbType.HIVE.getName(), new HiveGenerateSqlApi());
    }};

    /**
     * 注册自定义的策略
     *
     * @param dbTypeName 策略名
     * @param strategy   策略API
     */
    static void register(String dbTypeName, GenerateSqlApi strategy) {
        STRATEGY_MAP.merge(dbTypeName, strategy, (v1, v2) -> {
            throw new RuntimeException("Already exist dbType = " + dbTypeName);
        });
    }

    /**
     * 生成对应的工厂
     *
     * @param dbType 选择的db类型
     * @return 生成策略
     */
    static GenerateSqlApi choose(String dbType) {
        GenerateSqlApi generateSqlApi = STRATEGY_MAP.get(dbType);
        if (generateSqlApi == null) {
            String eMsg = String.format("not find this Strategy. check your dbType = %s is register? or your lifecycle is error.", dbType);
            throw new RuntimeException(eMsg);
        }
        return generateSqlApi;
    }

}
