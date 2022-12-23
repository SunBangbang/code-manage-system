package com.codemanage.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 转换工具类
 * @author hyh
 * @since  2022-05-30
 **/
public class Convert {
    private final static Logger log = LoggerFactory.getLogger(Convert.class);

    private Convert() {
    }

    public static Date tryConvertToDate(Object obj, Date defaultValue) {
        if (obj == null) {
            return defaultValue;
        }

        if (obj instanceof String) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return sdf.parse(obj.toString());
            } catch (ParseException e) {
                log.error(e.getMessage(), e);
                return defaultValue;
            }
        }

        if (obj instanceof Date) {
            return (Date) obj;
        }

        return defaultValue;
    }

    public static double tryConvertToDouble(Object obj, Double defaultValue) {
        if (obj == null) {
            return defaultValue;
        }

        if (obj instanceof Double) {
            return (Double) obj;
        }

        try {
            return Double.parseDouble(obj.toString());
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            return defaultValue;
        }
    }
}
