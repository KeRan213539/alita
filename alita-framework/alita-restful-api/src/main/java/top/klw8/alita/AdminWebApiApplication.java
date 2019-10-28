package top.klw8.alita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.logging.LoggingSystem;
import top.klw8.alita.starter.annotations.BaseWebApiApplication;


/**
 * @ClassName: AdminWebApiApplication
 * @Description: admin 接口服务启动器
 * @author klw
 * @date 2018年9月29日 下午4:12:17
 */
public class AdminWebApiApplication extends BaseWebApiApplication {

    public static void main(String[] args) {
	// 彻底关闭 spring boot 自带的 LoggingSystem
	System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);  
        SpringApplication.run( AdminWebApiApplication.class, args );
    }
    
    
}
