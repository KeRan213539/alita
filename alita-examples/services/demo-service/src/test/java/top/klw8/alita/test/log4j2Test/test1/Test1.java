package top.klw8.alita.test.log4j2Test.test1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: Test1
 * @Description: 
 * @author klw
 * @date 2018年3月15日 下午2:11:37
 */
public class Test1 {

    private static Logger logger = LoggerFactory.getLogger(Test1.class);
    
    public void doSomeing() {
//	for(int i = 0; i < 100; i++) {
	    logger.error("测试1写了一个错误日志====rrr");
//	}
    }

}
