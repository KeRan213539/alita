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

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.springframework.util.Assert;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * @ClassName: AnalyzerUtil
 * @Description: 分词器工具类(目前使用IK)
 * @author klw
 * @date 2017年11月17日 下午12:23:04
 */
public class AnalyzerUtil {

    /**
     * @Title: strAnalyzerReturnsWithSpace
     * @Description: 字符串分词【智能分词】,返回一个分词结果字符串,每个分词中间以空格隔开
     * @param str  
     * @return  分词结果字符串,每个分词中间以空格隔开
     */
    public static String strAnalyzerReturnsWithSpace(String str) {
	Assert.hasText(str, "字符串不能为空");
	Reader input = new StringReader(str);
	IKSegmenter ik = new IKSegmenter(input, true);
	StringBuilder sb = new StringBuilder();
	boolean isFirst = true;
	try {
	    while (true) {
		Lexeme lex = ik.next();
		if (lex == null) {
		    break;
		} else {
		    if(isFirst) {
			isFirst = false;
		    } else {
			sb.append(" ");
		    }
		    sb.append(lex.getLexemeText());
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return sb.toString();
    }
    
    /**
     * @Title: strAnalyzerReturnsWithSpaceNoSmart
     * @Description: 字符串分词【非智能分词】,返回一个分词结果字符串,每个分词中间以空格隔开
     * @param str
     * @return 分词结果字符串,每个分词中间以空格隔开
     */
    public static String strAnalyzerReturnsWithSpaceNoSmart(String str) {
	Assert.hasText(str, "字符串不能为空");
	Reader input = new StringReader(str);
	IKSegmenter ik = new IKSegmenter(input, false);
	StringBuilder sb = new StringBuilder();
	boolean isFirst = true;
	try {
	    while (true) {
		Lexeme lex = ik.next();
		if (lex == null) {
		    break;
		} else {
		    if(isFirst) {
			isFirst = false;
		    } else {
			sb.append(" ");
		    }
		    sb.append(lex.getLexemeText());
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return sb.toString();
    }
    
    /**
     * @Title: strAnalyzerInnerSpace
     * @Description: 把字符串中每个字都拆分,每个拆分中间以空格隔开
     * @param str
     * @return
     */
    public static String strAnalyzerInnerSpace(String str) {
	Assert.hasText(str, "字符串不能为空");
	StringBuilder sb = new StringBuilder();
	char[] chars = str.toCharArray();
	for(int i = 0; i < chars.length; i++) {
	    char c = chars[i];
	    if(c == ' ') {
		continue;
	    }
	    sb.append(c);
	    if((i + 1) != chars.length) {
		sb.append(" ");
	    }
	}
	return sb.toString();
    }
    
//    public static void main(String[] args) {
//	String str = "毛利小五郎 dota 跳伞 翼装飞行 滑雪 自由潜水";
//	System.out.println(strAnalyzerReturnsWithSpaceNoSmart(str));
//	
//    }
}
