package top.klw8.alita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.logging.LoggingSystem;
import top.klw8.alita.base.springctx.EnableSpringApplicationContextUtil;


/**
 * @ClassName: AuthorizationWebApiApplication
 * @Description: 认证 接口服务启动器
 * @author klw
 * @date 2018-11-01 17:08:20
 */

@SpringBootApplication
@EnableSpringApplicationContextUtil
public class AuthorizationServerDemoApplication {

    public static void main(String[] args) {
	System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);  // 彻底关闭 spring boot 自带的 LoggingSystem
        SpringApplication.run( AuthorizationServerDemoApplication.class, args );
    }

}