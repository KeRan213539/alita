/*
 * Copyright 2018-2021, ranke (213539@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
