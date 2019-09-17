package top.klw8.alita;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
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
// 前面这个是demo里的dubbo提供者包, 后面那个是 alita-basic-services-spring-boot-starter 中的dubbo提供者包
// dubbo 只扫描一次 @EnableDubbo, 所以只能配到一起. 同样的配制配到配制文件里也可以,注解和配制文件二选一
// 配制文件优先级大于注解, 配制文件中配制了的话注解就不生效
@EnableDubbo(scanBasePackages = {"top.klw8.alita.service.mybatisdemo.providers","top.klw8.alita.providers.admin"})
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
