package top.klw8.alita;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.logging.LoggingSystem;

import top.klw8.alita.BaseServiceApplication;


/**
 * @ClassName: AdminServiceApplication
 * @Description: 后台管理服务启动器
 * @author klw
 * @date 2018-11-09 11:19:10
 */
@MapperScan("top.klw8.alita.service.authority.mapper")
public class AdminServiceApplication extends BaseServiceApplication {

    public static void main(String[] args) {
//	Hooks.onOperatorDebug();
	System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);  // 彻底关闭 spring boot 自带的 LoggingSystem
//	SpringApplication.run( AdminServiceApplication.class, args );
	new SpringApplicationBuilder(AdminServiceApplication.class)
        .web(WebApplicationType.NONE) // 非 Web 应用
        .run(args);
	
    }
    
}
