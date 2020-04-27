package top.klw8.alita;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.logging.LoggingSystem;


/**
 * @ClassName: DemoServiceApplication
 * @Description: demo服务启动器
 * @author klw
 * @date 2018年9月29日 上午11:43:34
 */
@EnableDubbo(scanBasePackages = {"top.klw8.alita.service.demo.service.impl","top.klw8.alita.service.demo.providers.impl"})
@MapperScan("top.klw8.alita.service.demo.mapper")
public class DemoMybatisServiceApplication extends BaseServiceApplication {

    public static void main(String[] args) {
	System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);  // 彻底关闭 spring boot 自带的 LoggingSystem
        new SpringApplicationBuilder(DemoMybatisServiceApplication.class)
        .web(WebApplicationType.NONE) // 非 Web 应用
        .run(args);
    }
    
}
