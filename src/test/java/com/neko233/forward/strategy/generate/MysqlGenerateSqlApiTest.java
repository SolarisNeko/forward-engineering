package com.neko233.forward.strategy.generate;

import com.neko233.forward.ForwardEngine;
import com.neko233.forward.dbEngine.constant.DbType;
import com.neko233.forward.dto.User;
import org.junit.jupiter.api.Test;

/**
 * @author SolarisNeko
 * Date on 2022-04-12
 */
public class MysqlGenerateSqlApiTest {

    @Test
    public void mysqlTest() {
        ForwardEngine.runClass(DbType.MYSQL, User.class);
    }

}