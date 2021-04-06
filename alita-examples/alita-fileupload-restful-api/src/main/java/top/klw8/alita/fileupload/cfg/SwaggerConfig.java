package top.klw8.alita.fileupload.cfg;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import top.klw8.alita.starter.cfg.base.SwaggerConfigBase;

/**
 * @ClassName: SwaggerConfig
 * @Description: SwaggerConfig
 * @author klw
 * @date 2018年9月14日 09:54:14
 */
@Configuration  
@Profile("dev")  // 仅在dev模式(application.yml中的spring.profiles.active=dev时)下才会执行该配制
public class SwaggerConfig extends SwaggerConfigBase  {

    @Bean
    public Docket customImplementation(){
	
	List<SecurityContext> securityContexts = new ArrayList<>();
	securityContexts.add(SecurityContext.builder()
                .securityReferences(securityReference())
                .forPaths(PathSelectors.ant("/oss/**"))
                .build());
	
        return new Docket(DocumentationType.SWAGGER_2)
        	.groupName("【接口】")
                .select()
                .paths(PathSelectors.ant("/oss/**"))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts);
    }
    
}
