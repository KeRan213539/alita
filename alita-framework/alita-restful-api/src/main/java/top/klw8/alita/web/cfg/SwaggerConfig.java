package top.klw8.alita.web.cfg;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.annotations.Api;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;
import top.klw8.alita.starter.cfg.base.SwaggerConfigBase;

/**
 * @ClassName: SwaggerConfig
 * @Description: "@Profile('dev')" 仅在dev模式(application.yml中的spring.profiles.active=dev时)下才会执行该配制
 * @author klw
 * @date 2018年9月14日 09:54:14
 */
@Configuration
@EnableSwagger2WebFlux
@Profile("dev")
public class SwaggerConfig extends SwaggerConfigBase  {

    public SwaggerConfig() {
	super();
    }
    
    @Bean
    public Docket customImplementation(){
	List<SecurityContext> securityContexts = new ArrayList<>();
	securityContexts.add(SecurityContext.builder()
                .securityReferences(securityReference())
                .forPaths(PathSelectors.ant("/admin/**"))
                .build());
	
        return new Docket(DocumentationType.SWAGGER_2)
        	.groupName("【接口】")
                .select()
                .paths(PathSelectors.ant("/admin/**"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts);
    }
    
    
}
