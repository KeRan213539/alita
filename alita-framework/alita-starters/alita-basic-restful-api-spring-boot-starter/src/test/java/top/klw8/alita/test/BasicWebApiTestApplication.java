package top.klw8.alita.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.logging.LoggingSystem;
import top.klw8.alita.starter.BaseWebApiApplication;


/**
 * @ClassName: BasicWebApiTestApplication
 * @Description: 基础服务web api服务测试用启动器
 * @author klw
 * @date 2018年9月29日 下午4:12:17
 */
public class BasicWebApiTestApplication extends BaseWebApiApplication {

    public static void main(String[] args) {
        // 彻底关闭 spring boot 自带的 LoggingSystem
        System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);
        SpringApplication.run( BasicWebApiTestApplication.class, args );
    }
    
    
}
