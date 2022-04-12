package com.neko233.forward.strategy.generate;

import com.neko233.forward.ForwardEngine;
import com.neko233.forward.constant.DbType;
import com.neko233.forward.pojo.User;
import org.junit.Test;

/**
 * @author SolarisNeko
 * Date on 2022-04-12
 */
public class HiveGenerateStrategyTest {

    @Test
    public void testHiveGenerate() {
        ForwardEngine.runClass(DbType.HIVE, User.class);
    }

}