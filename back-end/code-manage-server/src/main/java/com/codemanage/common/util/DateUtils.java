package com.codemanage.common.util;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 日期时间工具类
 * @author hyh
 * @since 2022-05-30
 **/
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    /**
     * 获取最大日期
     * @param lastInventTime
     * @param lastSaleTime
     * @param lastPurchaseTime
     * @return
     */
    public static String getMaxDate(String lastInventTime, String lastSaleTime, String lastPurchaseTime) {
        String lastTime = null;
        // 封装日期集合
        List<String> dateList = new ArrayList<>();
        if (StringUtils.isNotBlank(lastInventTime)) {
            dateList.add(lastInventTime);
        }
        if (StringUtils.isNotBlank(lastSaleTime)) {
            dateList.add(lastSaleTime);
        }
        if (StringUtils.isNotBlank(lastPurchaseTime)) {
            dateList.add(lastPurchaseTime);
        }
        if (CollectionUtils.isNotEmpty(dateList)) {
            // 集合升序排列
//            dateList = dateList.stream().sorted().collect(Collectors.toList());
            // 集合降序则最大值排在第一位
            dateList = dateList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            lastTime = dateList.get(0);
        }
        return lastTime;
    }

    public static long getDayByDateTime(String lastTime, LocalDateTime currentTime) {
        long days = 0;
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotBlank(lastTime)) {
                LocalDateTime lastDateTime = LocalDateTime.parse(lastTime, df);
                days = Duration.between(lastDateTime, currentTime).toDays();
            }
        } catch (Exception e) {
            e.printStackTrace();
            days = -1;
        }
        return days;
    }

    /**
     * 获取当前时间
     * @return
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    public static String defaultFormatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String timeFormat = sdf.format(date);
        return timeFormat;
    }

    public static Date parseDate(String str, String... parsePatterns) {
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(str, parsePatterns);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
