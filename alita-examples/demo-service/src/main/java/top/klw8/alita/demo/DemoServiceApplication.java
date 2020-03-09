package top.klw8.alita.demo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.logging.LoggingSystem;
import top.klw8.alita.BaseServiceApplication;


/**
 * @author klw
 * @ClassName: DemoServiceApplication
 * @Description: 订单服务启动器
 * @date 2018年9月29日 上午11:43:34
 */
@EnableDubbo(scanBasePackages = {"top.klw8.alita.service.mybatisdemo.providers"})
@MapperScan("top.klw8.alita.service.mybatisdemo.services.user.mapper")
public class DemoServiceApplication extends BaseServiceApplication {

    public static void main(String[] args) {
        System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);  // 彻底关闭 spring boot 自带的 LoggingSystem
//	    SpringApplication.run( DemoServiceApplication.class, args );
        new SpringApplicationBuilder(DemoServiceApplication.class)
                .web(WebApplicationType.NONE) // 非 Web 应用
                .run(args);
    }

}
