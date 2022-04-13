package com.neko233.forward.pojo;

import com.neko233.forward.annotation.hive.HiveConfig;
import lombok.Data;

/**
 * @author SolarisNeko
 * Date on 2022-04-12
 */
@Data
@HiveConfig(external = true, location = "/user")
public class ExternalUser {

    Integer id;
    String name;
    Integer age;
    String phone;
    String email;

}
