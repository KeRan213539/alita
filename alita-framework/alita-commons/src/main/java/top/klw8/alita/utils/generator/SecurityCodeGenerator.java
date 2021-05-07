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
package top.klw8.alita.utils.generator;

import java.util.Arrays;
import java.util.Random;

/**
 * 验证码生成器
 * 2018-11-21 11:06:06
 */
public class SecurityCodeGenerator {

    // 字符集合(除去易混淆的数字0、数字1、字母l、字母o、字母O)
    private static final char[] CHAR_CODE = { 
	    '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 
	    'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 
	    'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' 
    };

    /**
     * 纯数字验证码 长度6
     * @return
     */
    public static String numberCode6() {
	return new String(getSecurityCode(6, SecurityCodeLevel.Simple, false));
    }

    /**
     * 生成数字字母混合验证码 长度4
     * @return
     */
    public static String mixCode4() {
	return new String(getSecurityCode(4, SecurityCodeLevel.Hard, false));
    }
    
    /**
     * 生成指定长度的数字字母混合随机字符串
     * @param length
     * @return
     */
    public static String mixCode(int length) {
	return new String(getSecurityCode(length, SecurityCodeLevel.Hard, false));
    }
    
    /**
     * 生成10到32位之间长度的随机字符串
     * @return
     */
    public static String mixCodeRandomLength() {
	int random = new Random().nextInt(32);
	if(random < 10) {
	    random = random + 10;
	}
	return new String(getSecurityCode(random, SecurityCodeLevel.Hard, false));
    }

    /**
     * * 获取验证码，指定长度、难度、是否允许重复字符
     * 
     * @param length 长度
     * @param level 难度
     * @param isCanRepeat 是否允许重复字符
     * @return 验证码
     */
    private static char[] getSecurityCode(int length, SecurityCodeLevel level, boolean isCanRepeat) {
	// 随机抽取len个字符
	int len = length;
	char[] code; // 根据不同的难度截取字符数组
	switch (level) {
	case Simple: {
	    code = Arrays.copyOfRange(CHAR_CODE, 0, 9);
	    break;
	}
	case Medium: {
	    code = Arrays.copyOfRange(CHAR_CODE, 0, 33);
	    break;
	}
	case Hard: {
	    code = Arrays.copyOfRange(CHAR_CODE, 0, CHAR_CODE.length);
	    break;
	}
	default: {
	    code = Arrays.copyOfRange(CHAR_CODE, 0, CHAR_CODE.length);
	}
	}
	// 字符集合长度
	int n = code.length;
	// 抛出运行时异常
	if (len > n && isCanRepeat == false) {
	    throw new RuntimeException(String.format("调用SecurityCodeGenerator.getSecurityCode(%1$s,%2$s,%3$s)出现异常，" + "当isCanRepeat为%3$s时，传入参数%1$s不能大于%4$s", len, level, isCanRepeat, n));
	}
	// 存放抽取出来的字符
	char[] result = new char[len];
	// 判断能否出现重复的字符
	if (isCanRepeat) {
	    for (int i = 0; i < result.length; i++) {
		// 索引 0 and n-1
		int r = (int) (Math.random() * n);
		// 将result中的第i个元素设置为codes[r]存放的数值
		result[i] = code[r];
	    }
	} else {
	    for (int i = 0; i < result.length; i++) {
		// 索引 0 and n-1
		int r = (int) (Math.random() * n);
		// 将result中的第i个元素设置为codes[r]存放的数值
		result[i] = code[r];
		// 必须确保不会再次抽取到那个字符，因为所有抽取的字符必须不相同。
		// 因此，这里用数组中的最后一个字符改写codes[r]，并将n减1
		code[r] = code[n - 1];
		n--;
	    }
	}
	return result;
    }
    
    public static void main(String[] args) {
	for(int i = 0; i < 20; i++){
	    System.out.println(SecurityCodeGenerator.numberCode6());
	}
	System.out.println("===========================================");
	for(int i = 0; i < 10; i++){
	    System.out.println(SecurityCodeGenerator.mixCode4());
	}
    }
}
