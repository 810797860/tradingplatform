package com.secondhand.tradingplatformcommon.util;

/**
 * @author 81079
 */

public class StringUtil {
    public StringUtil() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
