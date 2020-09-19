package top.klw8.alita.test;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import top.klw8.alita.web.cfg.BasicWebApisSwaggerConfig;

/**
 * @ClassName: SwaggerConfig
 * @Description: SwaggerConfig
 * @author klw(213539@qq.com)
 * @date 2019/11/4 15:08
 */
@Configuration
@Profile("dev")  // 仅在dev模式(application.yml中的spring.profiles.active=dev时)下才会执行该配制
public class SwaggerConfig extends BasicWebApisSwaggerConfig {

    public SwaggerConfig() {
        super();
    }

}