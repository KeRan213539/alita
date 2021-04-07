/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;


/**
 * @author klw
 * @ClassName: LocalDateTimeUtil
 * @Description: LocalDateTime的日期时间工具类
 * @date 2019-01-29 11:23:26
 */
public class LocalDateTimeUtil {

    /**
     * @author xp
     * @Description: 判断日期是否在范围内，以当前日期为参照往前多少天，往后多少天<BR/>支持Date,LocalDate,LocalDateTime
     * @Date 2019/11/19 14:12
     * @param: object (Date|LocalDate|LocalDateTime)
     * @param: before
     * @param: after
     * @return boolean
     */
    public static boolean isDateWithinDaysRange(Object object, Integer before, Integer after) {
        if(object instanceof Date){
            Date targetDate=(Date)object;
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE,-before);
            Date beginDate=calendar.getTime();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE,after);
            Date endDate=calendar.getTime();
            return DateTimeUtil.isDateWithinRange(targetDate,beginDate,endDate);
        }else if(object instanceof LocalDate){
            LocalDate targetDate=(LocalDate) object;
            LocalDate beginDate=LocalDate.now().plusDays(-before);
            LocalDate endDate=LocalDate.now().plusDays(after);
            return LocalDateTimeUtil.isLocalDateWithinRange(targetDate,beginDate,endDate);
        }else if(object instanceof LocalDateTime){
            LocalDateTime targetDate=(LocalDateTime) object;
            LocalDateTime beginDate=LocalDateTime.now().plusDays(-before);
            LocalDateTime endDate=LocalDateTime.now().plusDays(after);
            return LocalDateTimeUtil.isLocalDateTimeWithinRange(targetDate,beginDate,endDate);
        }else{
            return false;
        }
    }
    
    /**
     * @author xp
     * @Description: 判断LocalDateTime是否在指定日期范围之内
     * @Date 2019/11/19 10:51
     * @param: formatStr
     * @param: dateStr
     * @param: beginDateStr
     * @param: endDateStr
     * @return boolean
     */
    public static boolean isLocalDateTimeWithinRange(String formatStr, String dateStr, String beginDateStr, String endDateStr) {
        LocalDateTime targetDate=LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(formatStr));
        LocalDateTime beginDate=LocalDateTime.parse(beginDateStr, DateTimeFormatter.ofPattern(formatStr));
        LocalDateTime endDate=LocalDateTime.parse(endDateStr, DateTimeFormatter.ofPattern(formatStr));
        return isLocalDateTimeWithinRange(targetDate,beginDate,endDate);
    }
    
    /**
     * @author xp
     * @Description: 判断LocalDateTime是否在指定日期范围之内
     * @Date 2019/11/19 10:50
     * @param: targetDate
     * @param: beginDate
     * @param: endDate
     * @return boolean
     */
    private static boolean isLocalDateTimeWithinRange(LocalDateTime targetDate, LocalDateTime beginDate, LocalDateTime endDate) {
        if (targetDate.isAfter(beginDate) && targetDate.isBefore(endDate)) {
            return true;
        }else if(targetDate.isEqual(beginDate) || targetDate.isEqual(endDate)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * @author xp
     * @Description: 判断LocalDate是否在指定日期范围之内
     * @Date 2019/11/19 10:47
     * @param: formatStr
     * @param: dateStr
     * @param: beginDateStr
     * @param: endDateStr
     * @return boolean
     */
    public static boolean isLocalDateWithinRange(String formatStr,String dateStr,String beginDateStr,String endDateStr){
        LocalDate targetDate=LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(formatStr));
        LocalDate beginDate=LocalDate.parse(beginDateStr, DateTimeFormatter.ofPattern(formatStr));
        LocalDate endDate=LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern(formatStr));
        return isLocalDateWithinRange(targetDate,beginDate,endDate);
    }
    
    /**
     * @author xp
     * @Description: 判断LocalDate是否在指定日期范围之内
     * @Date 2019/11/19 10:32
     * @param: date
     * @param: beginDate
     * @param: endDate
     * @return boolean
     */
    public static boolean isLocalDateWithinRange(LocalDate date,LocalDate beginDate,LocalDate endDate){
        if (date.isAfter(beginDate) && date.isBefore(endDate)) {
            return true;
        }else if(date.isEqual(beginDate) || date.isEqual(endDate)){
            return true;
        }else {
            return false;
        }
    }
    
    /**
     * 获取今日年份
     *
     * @return yyyy
     */
    public static String getTodayYear() {
        return String.valueOf(LocalDateTime.now().getYear());
    }

    /**
     * 获取今日月份
     *
     * @return MM
     */
    public static String getTodayMonth() {
        return String.valueOf(LocalDateTime.now().getMonthValue());
    }


    /**
     * 获取今日日期 dd
     *
     * @return dd
     */
    public static String getTodayDay() {
        return String.valueOf(LocalDateTime.now().getDayOfMonth());
    }

    /**
     * 获取今日小时数
     *
     * @return MM
     */
    public static String getTodayHour() {
        return String.valueOf(LocalDateTime.now().getHour());
    }


    /**
     * 获取短日月 MMdd
     *
     * @return MMdd
     */
    public static String getTodayChar4() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMdd"));
    }

    /**
     * 获取短日月 MM-dd
     *
     * @return MMdd
     */
    public static String getTodayChar4En() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd"));
    }

    /**
     * 返回年月 yyyyMM
     *
     * @return yyyyMM
     */
    public static String getTodayChar6() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
    }

    /**
     * 返回年月 yyyy-MM
     *
     * @return yyyyMM
     */
    public static String getTodayChar6En() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    /**
     * 返回年月日 yyyyMMdd
     *
     * @return yyyyMMdd
     */
    public static String getTodayChar8() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * 返回年月日 yyyy-MM-dd
     *
     * @return yyyy-MM-dd
     */
    public static String getTodayChar8En() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }


    /**
     * 返回 年月日小时分 yyyyMMddHHmm
     *
     * @return yyyyMMddHHmm
     */
    public static String getTodayChar12() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
    }

    /**
     * 返回 年月日小时分 yyyy-MM-dd HH:mm
     *
     * @return yyyyMMddHHmm
     */
    public static String getTodayChar12En() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /**
     * 返回 年月日小时分秒 yyyyMMddHHmmss
     *
     * @return
     */
    public static String getTodayChar14() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }


    /**
     * 返回 年月日小时分秒 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getTodayChar14En() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


    /**
     * 返回 年月日小时分秒 毫秒 yyyyMMddHHmmssS
     *
     * @return yyyyMMddHHmmssS
     */
    public static String getTodayChar17() {
        String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssS"));
        int length = dateString.length();

        if (length < 17) {
            String endStr = dateString.substring(14, length);
            int len = endStr.length();
            for (int i = 0; i < 3 - len; i++) {
                endStr = "0" + endStr;
            }
            dateString = dateString.substring(0, 14) + endStr;
        }
        return dateString;
    }

    /**
     * 返回 年月日小时分秒 毫秒 yyyy-MM-dd HH:mm:ss S
     *
     * @return
     */
    public static String getTodayChar17En() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss S"));
    }

    /**
     * 返回 年月日小时分秒 毫秒 yyyy年MM月dd日
     *
     * @return
     */
    public static String getToDateCn() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"));
    }

    /**
     * 返回 年-月-日 yyyy-MM-dd
     *
     * @return
     */
    public static String getToDateEn() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }


    /**
     * 比对两个时间间隔
     *
     * @param startDateTime(yyyyMMddHHmmss) 开始时间
     * @param endDateTime(yyyyMMddHHmmss)   结束时间
     * @param distanceType                  计算间隔类型:年y 天d 小时h 分钟m 秒s
     * @return
     */
    public static String getDistanceDT(String startDateTime, String endDateTime, String distanceType) {
        return getDistanceDT(startDateTime, endDateTime, distanceType, "yyyyMMddHHmmss");
    }

    /**
     * 比对从给定时间到今天的时间间隔
     *
     * @param startDateTime(yyyy-MM-dd) 开始时间
     * @param distanceType              计算间隔类型:年y 天d 小时h 分钟m 秒s
     * @return
     */
    public static String getDistanceDT(String startDateTime, String distanceType) {
        return getDistanceDT(startDateTime, LocalDateTimeUtil.getTodayChar14En(), distanceType, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 比对从给定时间到今天的时间间隔
     *
     * @param startDateTime(yyyy-MM-dd) 开始时间
     * @param distanceType              计算间隔类型:年y 天d 小时h 分钟m 秒s
     * @return
     */
    public static String getDistanceDT(LocalDateTime startDateTime, String distanceType) {
        return getDistanceDT(startDateTime, LocalDateTime.now(), distanceType);
    }

    /**
     * 比对两个时间间隔
     *
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param distanceType  计算间隔类型:年y 天d 小时h 分钟m 秒s
     * @param format        传进来的日期格式
     * @return
     */
    public static String getDistanceDT(String startDateTime, String endDateTime, String distanceType, String format) {
        DateTimeFormatter tempDateFormat = DateTimeFormatter.ofPattern(format);
        LocalDateTime startDate = LocalDateTime.parse(startDateTime, tempDateFormat);
        LocalDateTime endDate = LocalDateTime.parse(endDateTime, tempDateFormat);
        return getDistanceDT(startDate, endDate, distanceType);
    }

    /**
     * 比对两个时间间隔
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param distanceType  计算间隔类型:年y 天d 小时h 分钟m 秒s
     * @return
     */
    public static String getDistanceDT(LocalDateTime startDate, LocalDateTime endDate, String distanceType) {
        String strResult = "";
        long lngDistancVal = 0;
        try {
            if (distanceType.equals("y")) {
                lngDistancVal = (getMilliByTime(endDate) - getMilliByTime(startDate)) / (1000 * 60 * 60 * 24) / 366;
            }
            if (distanceType.equals("d")) {
                lngDistancVal = (getMilliByTime(endDate) - getMilliByTime(startDate)) / (1000 * 60 * 60 * 24);
            } else if (distanceType.equals("h")) {
                lngDistancVal = (getMilliByTime(endDate) - getMilliByTime(startDate)) / (1000 * 60 * 60);
            } else if (distanceType.equals("m")) {
                lngDistancVal = (getMilliByTime(endDate) - getMilliByTime(startDate)) / (1000 * 60);
            } else if (distanceType.equals("s")) {
                lngDistancVal = (getMilliByTime(endDate) - getMilliByTime(startDate)) / (1000);
            }
            strResult = String.valueOf(lngDistancVal);
        } catch (Exception e) {
            strResult = "0";
        }
        return strResult;
    }

    /**
     * @param time
     * @return
     * @Title: getMilliByTime
     * @author klw
     * @Description: 获取指定日期的毫秒
     */
    public static Long getMilliByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * @param date
     * @return
     * @Title: convertDateToLDT
     * @author klw
     * @Description: Date转换为LocalDateTime
     */
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * @param time
     * @return
     * @Title: convertLDTToDate
     * @author klw
     * @Description: LocalDateTime转换为Date
     */
    public static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @param time
     * @return
     * @Title: getSecondsByTime
     * @author klw
     * @Description: 获取指定日期的秒
     */
    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * @param time
     * @param pattern
     * @return
     * @Title: formatTime
     * @author klw
     * @Description: 获取指定时间的指定格式
     */
    public static String formatTime(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取指定时间的yyyy-MM-dd HH:mm:ss格式
     * @Date 2019/11/1 17:12
     * @param: time
     * @return java.lang.String
     */
    public static String formatTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * @param date
     * @param pattern
     * @return
     * @Title: formatTime
     * @author klw
     * @Description: 获取指定时间的指定格式
     */
    public static String formatDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取指定时间的yyyy-MM-dd格式
     * @Date 2019/11/1 17:12
     * @param: date
     * @return java.lang.String
     */
    public static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * @param pattern
     * @return
     * @Title: formatNow
     * @author klw
     * @Description: 获取当前时间的指定格式
     */
    public static String formatNow(String pattern) {
        return formatTime(LocalDateTime.now(), pattern);
    }

    /**
     * @param time
     * @param number
     * @param field
     * @return
     * @Title: plus
     * @author klw
     * @Description: 日期加上一个数, 根据field不同加不同值, field为ChronoUnit.*
     */
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }

    /**
     * @param time
     * @param number
     * @param field
     * @return
     * @Title: minu
     * @author klw
     * @Description: 日期减去一个数, 根据field不同减不同值, field参数为ChronoUnit.*
     */
    public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field) {
        return time.minus(number, field);
    }

    /**
     * @param startTime
     * @param endTime
     * @param field
     * @return
     * @Title: betweenTwoTime
     * @author klw
     * @Description: 获取两个日期的差  field参数为ChronoUnit.*
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) return period.getYears();
        if (field == ChronoUnit.MONTHS) return period.getYears() * 12 + period.getMonths();
        return field.between(startTime, endTime);
    }

    /**
     * @param time
     * @return
     * @Title: getDayStart
     * @author klw
     * @Description: 获取一天的开始时间，2017,7,22 00:00
     */
    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    /**
     * @param time
     * @return
     * @Title: getDayEnd
     * @author klw
     * @Description: 获取一天的结束时间，2017,7,22 23:59:59.999999999
     */
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return time.withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999);
    }

    /**
     * @param startTime
     * @return -1 晚于当前时间  0 与当前时间相等  1 早于当前时间
     * @Title: compareTime
     * @author klw
     * @Description: 比较传入的时间(yyyy - MM - dd)与当前时间先后(仅比较年月日, 忽略时分秒)
     */
    public static int compareTime(String startTime) {
        return compareTime(formatToLDT(startTime, "yyyy-MM-dd"));
    }

    /**
     * @param startTime
     * @return -1 晚于当前时间  0 与当前时间相等  1 早于当前时间
     * @Title: compareTime
     * @author klw
     * @Description: 比较传入的时间与当前时间先后(仅比较年月日, 忽略时分秒)
     */
    public static int compareTime(LocalDateTime startTime) {
        String today = getTodayChar8En();
        String startDay = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        LocalDateTime todayLDT = formatToLDT(today, "yyyy-MM-dd");
        LocalDateTime startDayLDT = formatToLDT(startDay, "yyyy-MM-dd");
        int result = todayLDT.compareTo(startDayLDT);
        if (result == 0) {
            return 0;
        }
        if (result < 0) {
            return -1;
        }
        return 1;
    }

    /**
     * @param dateStr
     * @param dateFormatter
     * @return
     * @Title: formatToLDT
     * @author klw
     * @Description: 根据传入的字符串日期和对应的格式, 创建 LocalDateTime
     */
    public static LocalDateTime formatToLDT(String dateStr, String dateFormatter) {
        //yyyy-MM-dd HH:mm:ss
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(dateFormatter));
    }

    /**
     * @param dateStr
     * @return
     * @Title: formatToLDT
     * @author klw
     * @Description: 根据 yyyy-MM-dd HH:mm:ss 格式的字符串创建 LocalDateTime
     */
    public static LocalDateTime formatToLDT(String dateStr) {
        return formatToLDT(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @param dateStr
     * @param dateFormatter
     * @return
     * @Title: formatToLDT
     * @author klw
     * @Description: 根据传入的字符串日期和对应的格式, 创建 LocalDate
     */
    public static LocalDate formatToLD(String dateStr, String dateFormatter) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(dateFormatter));
    }

    /**
     * @param dateStr
     * @return
     * @Title: formatToLDT
     * @author klw
     * @Description: 根据 yyyy-MM-dd 格式的字符串创建 LocalDate
     */
    public static LocalDate formatToLD(String dateStr) {
        return formatToLD(dateStr, "yyyy-MM-dd");
    }

    /*
     * @author klw(213539@qq.com)
     * @Description: 获取指定时间的那一天的开始时间(0时0秒0分)
     * @Date 2019/10/15 16:35
     * @param: dateTime
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime dayBegin(LocalDateTime dateTime){
        return LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MIN);
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取指定时间的那一天的结束时间(23时59秒59分)
     * @Date 2019/10/15 16:39
     * @param: dateTime
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime dayEnd(LocalDateTime dateTime){
        return LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MAX);
    }

    /*
     * @author klw(213539@qq.com)
     * @Description: 获取指定时间的那一天的开始时间(0时0秒0分)
     * @Date 2019/10/15 16:35
     * @param: dateTime
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime dayBegin(LocalDate date){
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取指定时间的那一天的结束时间(23时59秒59分)
     * @Date 2019/10/15 16:39
     * @param: dateTime
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime dayEnd(LocalDate date){
        return LocalDateTime.of(date, LocalTime.MAX);
    }

    /*
     * @author klw(213539@qq.com)
     * @Description: 获取今天的开始时间(0时0秒0分)
     * @Date 2019/10/15 16:35
     * @param: dateTime
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime getTodayBegin(){
        return dayBegin(LocalDateTime.now());
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取今天的结束时间(23时59秒59分)
     * @Date 2019/10/15 16:39
     * @param: dateTime
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime getTodayEnd(){
        return dayEnd(LocalDateTime.now());
    }

    public static void main(String[] args) {

        // 获取当前时间的LocalDateTime对象
        // LocalDateTime.now();

        // 根据年月日构建LocalDateTime
        // LocalDateTime.of();

        // 比较日期先后
        // LocalDateTime.now().isBefore(),
        // LocalDateTime.now().isAfter(),

//	System.out.println(LocalDateTimeUtil.getDistanceDT("2018-01-15 11:00:00", "m"));

        System.out.println(formatToLD("2018-08-08"));
    }



}
