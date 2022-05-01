package com.example.demo.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期工具类
 *
 * @author gaoyy
 * @date 2019/7/5 16:46
 */
@Slf4j
public class DateUtil {
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_HMS = "HH:mm:ss";
    public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";

    /**
     * 获取日期，格式为yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return String
     */
    public static Date parseYMDHMSDateYmd(String date) {
        if (null == date) {
            return null;
        }
        try {
            SimpleDateFormat destsmf = new SimpleDateFormat(DATE_FORMAT_YMDHMS);
            return destsmf.parse(date);
        } catch (Exception e) {
            log.error("格式化日期出错，date=" + date, e);
            return null;
        }
    }

    /**
     * 获取年月日
     *
     * @param date
     * @return
     */
    public static String getYyyyMMdd(Date date) {
        if (null == date) {
            return null;
        }
        try {
            SimpleDateFormat destsmf = new SimpleDateFormat(DATE_FORMAT_YMD);
            return destsmf.format(date);
        } catch (Exception e) {
            log.error("格式化日期出错，date=" + date, e);
            return null;
        }
    }

    public static String getYyyyMMddHHMMDDStr(Date date) {
        if (null == date) {
            return null;
        }
        try {
            SimpleDateFormat destsmf = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS);
            return destsmf.format(date);
        } catch (Exception e) {
            log.error("格式化日期出错，date=" + date, e);
            return null;
        }
    }

    /**
     * 获取年月日
     *
     * @param date
     * @return
     */
    public static String getYyyyMMddHHMMDDString(Date date) {
        if (null == date) {
            return null;
        }
        try {
            SimpleDateFormat destsmf = new SimpleDateFormat(DATE_FORMAT_YMDHMS);
            return destsmf.format(date);
        } catch (Exception e) {
            log.error("格式化日期出错，date=" + date, e);
            return null;
        }
    }

    /**
     * 获取时分秒
     *
     * @param date
     * @return
     */
    public static String getHHmmss(Date date) {
        if (null == date) {
            return null;
        }
        try {
            SimpleDateFormat destsmf = new SimpleDateFormat(DATE_FORMAT_HMS);
            return destsmf.format(date);
        } catch (Exception e) {
            log.error("格式化日期出错，date=" + date, e);
            return null;
        }
    }

    /**
     * 获取搜索格式日志字符串 2018-12-01T19:00:00Z
     *
     * @param dateStr
     * @return
     */
    public static String getSearchDateStr(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return StringUtils.EMPTY;
        }
        Date date = parseYMDHMSDateYmd(dateStr);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String queryTime = sdf.format(date);
        return queryTime;
    }

    /**
     * 获取日期字符串，格式为yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return String
     */
    public static String parseYMDHMSDate2Str(Date date) {
        if (null == date) {
            return null;
        }
        try {
            SimpleDateFormat destsmf = new SimpleDateFormat(DATE_FORMAT_YMDHMS);
            return destsmf.format(date);
        } catch (Exception e) {
            log.error("格式化日期出错，date=" + date, e);
            return null;
        }
    }


    /**
     * @return Date
     * @Desc 将long时间戳转换为日期
     * @date 2017/03/30
     */
    public static Date convertLongToDate(Long unixTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(unixTime);
        return c.getTime();
    }

    /**
     * 将long类型的timestamp转为LocalDateTime
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 将LocalDateTime转为long类型的timestamp
     *
     * @param localDateTime
     * @return
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * 将LocalDateTime转为Date
     *
     * @param localDateTime
     * @return
     */
    public static Date getDateOfLocalDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 获取当前时间字符串  格式：yyyyMMddHHmmss
     *
     * @return
     */
    public static String getNowDateString() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return pattern.format(LocalDateTime.now());
    }

    /**
     * 判断当前时间在时间区间内
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
    public static void main(String[] args) throws Exception {
        SimpleDateFormat simpledf = new SimpleDateFormat("yyyy-MM-dd");
        String startDateOne = "2020-01-09";
        String endDateOne = "2020-01-15";
        String startDateTwo = "2020-01-09";
        String endDateTwo = "2020-06-30";
        Date qssjHasDate = simpledf.parse(startDateOne);
        Date jssjHasDate = simpledf.parse(endDateOne);
        Date qssjNoHasDate = simpledf.parse(startDateTwo);
        Date jssjNoHasDate = simpledf.parse(endDateTwo);
        System.out.println(IsInterSection(qssjHasDate, jssjHasDate, qssjNoHasDate, jssjNoHasDate));

    }

    /***
     *
     * @param startDateOne 第一个时间段的开始时间
     * @param endDateOne 第一个时间段的结束时间
     * @param startDateTwo 第二个时间段的开始时间
     * @param endDateTwo 第二个时间段的结束时间
     * @return
     */
    public static Boolean IsInterSection(Date startDateOne, Date endDateOne, Date startDateTwo, Date endDateTwo) {
        Date maxStartDate = startDateOne;
        if (maxStartDate.before(startDateTwo)) {
            maxStartDate = startDateTwo;
        }
        Date minEndDate = endDateOne;
        if (endDateTwo.before(minEndDate)) {
            minEndDate = endDateTwo;
        }
        if (maxStartDate.before(minEndDate) || (maxStartDate.getTime() == minEndDate.getTime())) {
            return true;
        } else {
            return false;
        }
    }
}
