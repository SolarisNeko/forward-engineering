package com.neko233.forward.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author SolarisNeko
 * Date on 2022-04-13
 */
public class CamelCaseUtilTest {

    @Test
    public void testToBigCamelUpperName() throws Exception {
        String result = CamelCaseUtil.toBigCamelUpperName("UserWithEmail");
        Assertions.assertEquals("USER_WITH_EMAIL", result);
    }

    @Test
    public void testToBigCamelLowerName() throws Exception {
        String result = CamelCaseUtil.toBigCamelLowerName("UserWithEmail");
        Assertions.assertEquals("user_with_email", result);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme