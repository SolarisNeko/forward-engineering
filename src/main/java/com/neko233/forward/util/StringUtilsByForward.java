package com.neko233.forward.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author LuoHaoJun on 2023-01-19
 **/
public class StringUtilsByForward {

    /**
     * 首字母大写
     * @param content
     * @return
     */
    public static String firstWordUpperCase(String content) {
        if (StringUtils.isBlank(content)) {
            return content;
        }
        if (content.length() == 1) {
            return content.toUpperCase();
        }
        return content.substring(0, 1).toUpperCase() + content.substring(1);
    }

    /**
     * 首字母小写
     * @param content
     * @return
     */
    public static String firstWordLowerCase(String content) {
        if (StringUtils.isBlank(content)) {
            return content;
        }
        if (content.length() == 1) {
            return content.toLowerCase();
        }
        return content.substring(0, 1).toLowerCase() + content.substring(1);
    }

}
