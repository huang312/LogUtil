package com.titool.log;

/**
 * @Descrpition
 * @Date 2024/8/11
 */
public class StringUtil {
    public static String defaultIfBlank(String str, String defaultStr) {
        if (str == null || str.isEmpty() || str.isBlank()) {
            return defaultStr;
        }
        return str;
    }

    public static String[] split(String s, String regex) {
        return s.split(regex);
    }
}
