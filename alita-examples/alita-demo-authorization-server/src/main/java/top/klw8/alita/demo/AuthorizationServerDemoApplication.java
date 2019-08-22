package top.klw8.alita.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.annotation.ComponentScan;
import top.klw8.alita.base.springctx.EnableSpringApplicationContextUtil;


/**
 * @author klw
 * @ClassName: AuthorizationWebApiApplication
 * @Description: 认证 接口服务启动器
 * @date 2018-11-01 17:08:20
 */

@SpringBootApplication
@EnableSpringApplicationContextUtil
@ComponentScan(basePackages = {"top.klw8.alita.demo","top.klw8.alita.starter.authorization.cfg"})
public class AuthorizationServerDemoApplication {

    public static void main(String[] args) {
        System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);  // 彻底关闭 spring boot 自带的 LoggingSystem
        SpringApplication.run(AuthorizationServerDemoApplication.class, args);
    }

}
