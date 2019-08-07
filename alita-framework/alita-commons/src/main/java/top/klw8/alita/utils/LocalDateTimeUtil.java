package top.klw8.alita.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;


/**
 * @ClassName: LocalDateTimeUtil
 * @Description: LocalDateTime的日期时间工具类
 * @author klw
 * @date 2019-01-29 11:23:26
 */
public class LocalDateTimeUtil {

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
     * @param startDateTime(yyyyMMddHHmmss)
     *            开始时间
     * @param endDateTime(yyyyMMddHHmmss)
     *            结束时间
     * @param distanceType
     *            计算间隔类型:年y 天d 小时h 分钟m 秒s
     * @return
     */
    public static String getDistanceDT(String startDateTime, String endDateTime, String distanceType) {
	return getDistanceDT(startDateTime, endDateTime, distanceType, "yyyyMMddHHmmss");
    }

    /**
     * 比对从给定时间到今天的时间间隔
     * 
     * @param startDateTime(yyyy-MM-dd)
     *            开始时间
     * @param distanceType
     *            计算间隔类型:年y 天d 小时h 分钟m 秒s
     * @return
     */
    public static String getDistanceDT(String startDateTime, String distanceType) {
	return getDistanceDT(startDateTime, LocalDateTimeUtil.getTodayChar14En(), distanceType, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 比对从给定时间到今天的时间间隔
     * 
     * @param startDateTime(yyyy-MM-dd)
     *            开始时间
     * @param distanceType
     *            计算间隔类型:年y 天d 小时h 分钟m 秒s
     * @return
     */
    public static String getDistanceDT(LocalDateTime startDateTime, String distanceType) {
	return getDistanceDT(startDateTime, LocalDateTime.now(), distanceType);
    }

    /**
     * 比对两个时间间隔
     * 
     * @param startDateTime
     *            开始时间
     * @param endDateTime
     *            结束时间
     * @param distanceType
     *            计算间隔类型:年y 天d 小时h 分钟m 秒s
     * @param format
     *            传进来的日期格式
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
     * @param startDateTime
     *            开始时间
     * @param endDateTime
     *            结束时间
     * @param distanceType
     *            计算间隔类型:年y 天d 小时h 分钟m 秒s
     * @param format
     *            传进来的日期格式
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
     * @Title: getMilliByTime
     * @author klw
     * @Description: 获取指定日期的毫秒
     * @param time
     * @return
     */
    public static Long getMilliByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * @Title: convertDateToLDT
     * @author klw
     * @Description: Date转换为LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * @Title: convertLDTToDate
     * @author klw
     * @Description: LocalDateTime转换为Date
     * @param time
     * @return
     */
    public static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }
    
    /**
     * @Title: getSecondsByTime
     * @author klw
     * @Description: 获取指定日期的秒
     * @param time
     * @return
     */
    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * @Title: formatTime
     * @author klw
     * @Description: 获取指定时间的指定格式
     * @param time
     * @param pattern
     * @return
     */
    public static String formatTime(LocalDateTime time,String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * @Title: formatNow
     * @author klw
     * @Description: 获取当前时间的指定格式
     * @param pattern
     * @return
     */
    public static String formatNow(String pattern) {
        return  formatTime(LocalDateTime.now(), pattern);
    }

    /**
     * @Title: plus
     * @author klw
     * @Description: 日期加上一个数,根据field不同加不同值,field为ChronoUnit.*
     * @param time
     * @param number
     * @param field
     * @return
     */
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }

    /**
     * @Title: minu
     * @author klw
     * @Description: 日期减去一个数,根据field不同减不同值,field参数为ChronoUnit.*
     * @param time
     * @param number
     * @param field
     * @return
     */
    public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field){
        return time.minus(number,field);
    }

    /**
     * @Title: betweenTwoTime
     * @author klw
     * @Description: 获取两个日期的差  field参数为ChronoUnit.*
     * @param startTime
     * @param endTime
     * @param field
     * @return
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) return period.getYears();
        if (field == ChronoUnit.MONTHS) return period.getYears() * 12 + period.getMonths();
        return field.between(startTime, endTime);
    }

    /**
     * @Title: getDayStart
     * @author klw
     * @Description: 获取一天的开始时间，2017,7,22 00:00
     * @param time
     * @return
     */
    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    /**
     * @Title: getDayEnd
     * @author klw
     * @Description: 获取一天的结束时间，2017,7,22 23:59:59.999999999
     * @param time
     * @return
     */
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return time.withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999);
    }
    
    /**
     * @Title: compareTime
     * @author klw
     * @Description: 比较传入的时间(yyyy-MM-dd)与当前时间先后(仅比较年月日,忽略时分秒)
     * @param startTime
     * @return  -1 晚于当前时间  0 与当前时间相等  1 早于当前时间
     */
    public static int compareTime(String startTime) {
	return compareTime(formatToLDT(startTime, "yyyy-MM-dd"));
    }
    
    /**
     * @Title: compareTime
     * @author klw
     * @Description: 比较传入的时间与当前时间先后(仅比较年月日,忽略时分秒)
     * @param startTime
     * @return  -1 晚于当前时间  0 与当前时间相等  1 早于当前时间
     */
    public static int compareTime(LocalDateTime startTime) {
	String today = getTodayChar8En();
	String startDay = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	
	LocalDateTime todayLDT = formatToLDT(today, "yyyy-MM-dd");
	LocalDateTime startDayLDT = formatToLDT(startDay, "yyyy-MM-dd");
	int result = todayLDT.compareTo(startDayLDT);
	if(result == 0) {
	    return 0;
	}
	if(result < 0) {
	    return -1;
	}
	return 1;
    }
    
    /**
     * @Title: formatToLDT
     * @author klw
     * @Description: 根据传入的字符串日期和对应的格式,创建 LocalDateTime
     * @param dateStr
     * @param dateFormatter
     * @return
     */
    public static LocalDateTime formatToLDT(String dateStr, String dateFormatter) {
	//yyyy-MM-dd HH:mm:ss
	return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(dateFormatter));
    }
    
    /**
     * @Title: formatToLDT
     * @author klw
     * @Description: 根据 yyyy-MM-dd HH:mm:ss 格式的字符串创建 LocalDateTime
     * @param dateStr
     * @return
     */
    public static LocalDateTime formatToLDT(String dateStr) {
	return formatToLDT(dateStr, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * @Title: formatToLDT
     * @author klw
     * @Description: 根据传入的字符串日期和对应的格式,创建 LocalDate
     * @param dateStr
     * @param dateFormatter
     * @return
     */
    public static LocalDate formatToLD(String dateStr, String dateFormatter) {
	return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(dateFormatter));
    }
    
    /**
     * @Title: formatToLDT
     * @author klw
     * @Description: 根据 yyyy-MM-dd 格式的字符串创建 LocalDate
     * @param dateStr
     * @return
     */
    public static LocalDate formatToLD(String dateStr) {
	return formatToLD(dateStr, "yyyy-MM-dd");
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
