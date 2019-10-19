package top.klw8.alita;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.logging.LoggingSystem;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: BasicServiceTestApplication
 * @Description: basic service 测试
 * @date 2019/10/16 17:16
 */
public class BasicServiceTestApplication extends BaseServiceApplication {

    public static void main(String[] args) {
        System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);  // 彻底关闭 spring boot 自带的 LoggingSystem
        new SpringApplicationBuilder(BasicServiceTestApplication.class)
                .web(WebApplicationType.NONE) // 非 Web 应用
                .run(args);
    }

}
