package top.klw8.alita.test.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @ClassName: ExceptionTest
 * @Description: 
 * @author klw
 * @date 2018年11月6日 下午4:45:12
 */
public class ExceptionTest {

    public static void main(String[] args) {
	try {
	    throw new Exception("xxxx");
	}catch (Throwable e) {
	    StringWriter stringWriter = new StringWriter();
	    e.printStackTrace(new PrintWriter(stringWriter));
	    System.out.println(stringWriter.toString());
	}

    }

}
