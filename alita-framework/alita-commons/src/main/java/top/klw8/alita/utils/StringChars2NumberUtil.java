package top.klw8.alita.utils;


/**
 * @ClassName: StringChars2NumberUtil
 * @Description: 字母字符串转数字工具类
 * @author klw
 * @date 2018年12月25日 下午3:51:03
 */
public class StringChars2NumberUtil {

    /**
     * @Title: stringChars2Number
     * @author klw
     * @Description: 将一个任意字符串转换为数字
     * @param str
     * @return
     */
    public static long stringChars2Number(String str) {
	String result = "";
	String last = null;
	char[] chars = str.toCharArray();
	for(char ch : chars) {
	    last = String.copyValueOf(result.toCharArray());
	    result += String.valueOf((int)ch).charAt(0);
	    try {
		Long.valueOf(result);
	    }catch (Exception e) {
		return Long.valueOf(last);
	    }
	}
	return Long.valueOf(result);
    }
    
}
