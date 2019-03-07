package top.klw8.alita.test.log4j2Test.test2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: Test2
 * @Description: 
 * @author klw
 * @date 2018年3月15日 下午2:11:45
 */
public class Test2 {

    private static Logger logger = LoggerFactory.getLogger(Test2.class);
    
    public void doSomeing() {
	logger.info("测试2写了一个info日志=============rrr");
    }
    
}
