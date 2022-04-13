package com.neko233.forward.annotation.hive;

import java.lang.annotation.*;

/**
 * @author SolarisNeko
 * Date on 2022-04-12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface HiveConfig {

    boolean external() default false;

    String terminatedBy() default ",";

    String storedAs() default "textfile";

    String location() default "";

    String tblProperties() default "";

}
