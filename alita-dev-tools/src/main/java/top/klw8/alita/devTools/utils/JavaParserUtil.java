package top.klw8.alita.devTools.utils;

import java.io.File;
import java.io.FileNotFoundException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ParserConfiguration.LanguageLevel;
import com.github.javaparser.ast.CompilationUnit;

/**
 * @ClassName: JavaParserUtil
 * @Description: java文件解析工具类
 * @author klw
 * @date 2019年2月26日 上午10:04:57
 */
public class JavaParserUtil {

    public static final JavaParser javaParser = new JavaParser(
	    new ParserConfiguration().setLanguageLevel(LanguageLevel.JAVA_8));

    public static ParseResult<CompilationUnit> parse(String filePathText) {
	try {
	    return javaParser.parse(new File(filePathText));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	return null;
    }

    public static String parseFieldComment(String fieldName, String commentContent) {
	 String tempStr = commentContent.substring(commentContent.indexOf(fieldName) + fieldName.length())
		.replace(":", "").trim();
	 if(tempStr.indexOf("\r\n") != -1) {
	     tempStr = tempStr.substring(0, tempStr.indexOf("\r\n"));
	 }
	 if(tempStr.indexOf("\n") != -1) {
	     tempStr = tempStr.substring(0, tempStr.indexOf("\n"));
	 }
	 return tempStr;
    }
    
    public static String parseClassComment(String commentContent) {
	return parseFieldComment("@Description", commentContent);
    }

}
