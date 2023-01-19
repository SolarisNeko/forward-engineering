package com.neko233.forward.strategy.generate;

import com.neko233.forward.ForwardEngine;
import com.neko233.forward.dbEngine.constant.DbType;
import com.neko233.forward.dto.ExternalUser;
import com.neko233.forward.dto.User;
import org.junit.jupiter.api.Test;

/**
 * @author SolarisNeko
 * Date on 2022-04-12
 */
public class HiveGenerateSqlApiTest {

    @Test
    public void baseHiveUser() {
        String actual = ForwardEngine.runClass(DbType.HIVE, User.class);
        String target = "Drop table if exists external_user;\n" +
                "Create external Table if not exists external_user( `id` int, `name` string, `age` int, `phone` string, `email` string ) \n" +
                "ROW FORMAT delimited fields terminated by ','\n" +
                "STORED AS textfile\n" +
                "LOCATION  '/user'\n" +
                "tblProperties(  );";
//        Assertions.assertEquals(target.length(), actual.length());
    }

    @Test
    public void ExternalUser() {
        String actual = ForwardEngine.runClass(DbType.HIVE, ExternalUser.class);
        String target = "Drop table if exists external_user;\n" +
                "Create external Table if not exists external_user( `id` int, `name` string, `age` int, `phone` string, `email` string ) \n" +
                "ROW FORMAT delimited fields terminated by ','\n" +
                "STORED AS textfile\n" +
                "LOCATION  '/user'\n" +
                "tblProperties(  );\n";
//        Assertions.assertEquals(target.length(), actual.length());
    }

}