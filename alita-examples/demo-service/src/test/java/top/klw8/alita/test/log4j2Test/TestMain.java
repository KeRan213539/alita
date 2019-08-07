package top.klw8.alita.test.log4j2Test;

import top.klw8.alita.test.log4j2Test.test1.Test1;
import top.klw8.alita.test.log4j2Test.test2.Test2;

/**
 * @ClassName: TestMain
 * @Description: 
 * @author klw
 * @date 2018年3月15日 下午2:27:39
 */
public class TestMain {
    
    public static void main(String[] args) {
	Test1 t1 = new Test1();
	Test2 t2 = new Test2();
	t1.doSomeing();
	t2.doSomeing();
    }
    
}
