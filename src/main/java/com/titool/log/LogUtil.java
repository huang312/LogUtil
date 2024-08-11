package com.titool.log;

import org.slf4j.Logger;

/**
 * @Descrpition
 * @Date 2024/8/11
 */
public class LogUtil {

    public static void info(Logger logger, Object... objects) {
        if (logger != null && logger.isInfoEnabled()) {
            logger.info(getLogString(objects));
        }
    }

    public static void error(Logger logger, Throwable t, Object... objects) {
        if (logger != null && logger.isErrorEnabled()) {
            logger.error(getLogString(objects), t);
        }
    }

    private static String getLogString(Object... objects) {
        StringBuilder sb = new StringBuilder();
        for (Object object : objects) {
            sb.append(object.toString());
        }
        return sb.toString();
    }




}
