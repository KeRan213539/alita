package top.klw8.alita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.logging.LoggingSystem;

import top.klw8.alita.starter.annotations.BaseWebApiApplication;


/**
 * @ClassName: DemoWebApiApplication
 * @Description: order 接口服务启动器
 * @author klw
 * @date 2018年9月29日 下午4:12:17
 */

public class DemoWebApiApplication extends BaseWebApiApplication {

    public static void main(String[] args) {
	System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);  // 彻底关闭 spring boot 自带的 LoggingSystem
        SpringApplication.run( DemoWebApiApplication.class, args );
//        RedisUtil.set("test_111222", "testtesttest", 300L, RedisTagEnum.REDIS_TAG_TEST);
    }
    
    
}
