package top.klw8.alita.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: ValidateUtil
 * @Description: 一些常用效验
 * @author klw
 * @date 2018-11-22 09:48:57
 */
public class ValidateUtil {

    /**
     * @Title: checkStrByRegex
     * @Description: 根据正则表达式效验字符串
     * @param str
     * @param regex
     * @return
     */
    public static boolean checkStrByRegex(String str, String regex) {
	return str.matches(regex);
    }
    
    /**
     * @Title: checkIpFormatting
     * @Description: 检查IP格式是否正确
     * @param ip
     * @return
     */
    public static boolean checkIpFormatting(String ip) {
	String regex = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";
	return ip.matches(regex);
    }
    
    /**
     * @Title: checkDateFormatting
     * @Description: 检查传入的字符串是否是指定的日期格式
     * @param dateStr
     * @param formatStr
     * @return
     */
    public static boolean checkDateFormatting(String dateStr, String formatStr) {
	DateFormat formatter = new SimpleDateFormat(formatStr);
	try {
	    formatter.parse(dateStr);
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }
    
    /**
     * @Title: checkLngLatRange
     * @Description: 检查经纬度值是否在经纬度范围内(经度: -90~90, 纬度: -180~180)
     * @param statusCode
     * @param message
     * @param lng
     * @param lat
     */
    public static boolean checkLatLngRange(Double lat, Double lng) {
	if(lat == null || lng == null) {
	    return false;
	}
	if(lat < -180D || lat > 180D || lng < -90D || lng > 90D) {
	    return false;
	}
	return true;
    }
    
    /**
     * @Title: checkLongitudeRange
     * @author klw
     * @Description: 检查经度范围
     * @param lng
     * @return
     */
    public static boolean checkLongitudeRange(Double lng) {
	if(lng == null) {
	    return false;
	}
	if(lng < -90D || lng > 90D) {
	    return false;
	}
	return true;
    }
    
    /**
     * @Title: checkLatitudeRange
     * @author klw
     * @Description: 检查纬度范围
     * @param lat
     * @return
     */
    public static boolean checkLatitudeRange(Double lat) {
	if(lat == null) {
	    return false;
	}
	if(lat < -180D || lat > 180D) {
	    return false;
	}
	return true;
    }
    
    
    /**
     * @Title: isMobileNO
     * @Description: 验证手机号格式是否正确
     * @param mobileNo
     * @return
     */
    public static boolean isMobileNO(String mobileNo) {
	String telRegex = "^1[34578]\\d{9}$";
	return checkStringByRegx(mobileNo, telRegex);
    }

    /**
     * @Title: isChinaIdCardNo
     * @Description: 检查是否是身份证号
     * @param idCardNo
     * @return
     */
    public static boolean isChinaIdCardNo(String idCardNo) {
	String idCardRegex = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";
	return checkStringByRegx(idCardNo, idCardRegex);
    }

    /**
     * @Title: checkStringByRegx
     * @Description: 根据正则表达式验证字符串
     * @param str
     * @param regx
     * @return
     */
    private static boolean checkStringByRegx(String str, String regx) {
	if (StringUtils.isBlank(str)) {
	    return false;
	}
	return str.matches(regx);
    }
    
    /**
     * @Title: checkDateFormatting
     * @Description: 检查日期格式是否是 yyyyMMddHHmmss
     * @param dateStr
     * @return
     */
    public static boolean checkDateFormatting_yyyyMMddHHmmss(String dateStr) {
	try {
	    Long.parseLong(dateStr);
	} catch (Exception e) {
	    return false;
	}
	return ValidateUtil.checkDateFormatting(dateStr, "yyyyMMddHHmmss");
    }
}
