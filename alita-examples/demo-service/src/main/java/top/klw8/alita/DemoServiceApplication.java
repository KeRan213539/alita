package top.klw8.alita;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.logging.LoggingSystem;


/**
 * @ClassName: DemoServiceApplication
 * @Description: 订单服务启动器
 * @author klw
 * @date 2018年9月29日 上午11:43:34
 */
@MapperScan("top.klw8.alita.service.mybatisdemo.services.user.mapper")
public class DemoServiceApplication extends BaseServiceApplication {

    public static void main(String[] args) {
	System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);  // 彻底关闭 spring boot 自带的 LoggingSystem
//	SpringApplication.run( DemoServiceApplication.class, args );
        new SpringApplicationBuilder(DemoServiceApplication.class)
        .web(WebApplicationType.NONE) // 非 Web 应用
        .run(args);
    }
    
}
