package top.klw8.alita.demo.cfg;


import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;
import top.klw8.alita.starter.cfg.base.SwaggerConfigBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SwaggerConfig
 * @Description: SwaggerConfig
 * @author klw
 * @date 2018年9月14日 09:54:14
 */
@Configuration  
@EnableSwagger2WebFlux
@Profile("dev")  // 仅在dev模式(application.yml中的spring.profiles.active=dev时)下才会执行该配制
public class SwaggerConfig extends SwaggerConfigBase  {

    
    @Bean
    public Docket customImplementation(){
	
	List<SecurityContext> securityContexts = new ArrayList<>();
	securityContexts.add(SecurityContext.builder()
                .securityReferences(securityReference())
                .forPaths(PathSelectors.ant("/order/**"))
                .build());
	
        return new Docket(DocumentationType.SWAGGER_2)
        	.groupName("【接口】")
                .select()
                .paths(PathSelectors.ant("/order/**"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
//                .globalOperationParameters(pars)
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts);
    }
    
    @Bean
    public Docket customImplementationDemo(){
	
	List<SecurityContext> securityContexts = new ArrayList<>();
	securityContexts.add(SecurityContext.builder()
                .securityReferences(securityReference())
                .forPaths(PathSelectors.ant("/*demo/**"))
                .build());
	
	
        return new Docket(DocumentationType.SWAGGER_2)
        	.groupName("【测试/MongoDemo】")
                .select()
                .paths(PathSelectors.ant("/*demo/**"))
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
//                .globalOperationParameters(pars)
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts);
    }

    @Bean
    public Docket customImplementationDemo2(){

        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder()
                .securityReferences(securityReference())
                .forPaths(PathSelectors.ant("/*mybatisdemo/**"))
                .build());


        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("【测试/MybatisDemo】")
                .select()
                .paths(PathSelectors.ant("/*mybatisdemo/**"))
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
//                .globalOperationParameters(pars)
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts);
    }
}
