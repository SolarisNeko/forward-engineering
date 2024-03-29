package com.neko233.forward.util;

import com.neko233.forward.dbEngine.exception.NekoException;

/**
 * @title 处理 char 工具
 * @description: 目前主要用来处理 [ Big Camel + Small Camel ]
 * @author: SolarisNeko
 * @date: 2021/7/4
 */
public class CamelCaseUtil {

    /**
     * 转换成 Big Camel（大驼峰）的 Upper Case 版本!
     * 例如: SystemUser -> SYSTEM_USER
     */
    public static String toBigCamelUpperName(String name) {
        StringBuilder sb = new StringBuilder();
        char[] chars = name.toCharArray();
        if (chars.length == 0) {
            throw new NekoException("命名, 转化成 Big Camel - Upper Case 异常!");
        }

        // 首字母不处理
        sb.append(chars[0]);
        // [1, n] 字母, 遇到大写, 进行大驼峰处理
        for (int index = 1; index < chars.length; index++) {
            char aChar = chars[index];
            final boolean upperCase = Character.isUpperCase(aChar);
            if (upperCase) {
                // is Upper Case
                sb.append("_").append(aChar);
            } else {
                // is Lower Case
                final char upperChar = Character.toUpperCase(aChar);
                sb.append(upperChar);
            }
        }
        return sb.toString();
    }

    /**
     * 转换成 Big Camel（大驼峰）的 lower case 版本!
     * 例如: SystemUser -> system_user
     */
    public static String toBigCamelLowerName(String name) {
        StringBuilder sb = new StringBuilder();
        // 转化成 char[] 流
        char[] chars = name.toCharArray();
        if (chars.length == 0) {
            throw new NekoException("命名, 转化成 Big Camel - Lower Case 异常!");
        }
        // 首字母不处理
        sb.append(Character.toLowerCase(chars[0]));
        // [1, n] 字母, 遇到大写, 进行大驼峰处理
        for (int index = 1; index < chars.length; index++) {
            char aChar = chars[index];
            boolean upperCase = Character.isUpperCase(aChar);
            if (upperCase) {
                // Upper Case
                sb.append("_" + Character.toLowerCase(aChar));
            } else {
                // Lower Case
                char upperChar = Character.toLowerCase(aChar);
                sb.append(upperChar);
            }
        }
        return sb.toString();
    }
}
