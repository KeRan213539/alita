package top.klw8.alita.demo.cfg;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
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
    public Docket customImplementationDemo(){
	
	List<SecurityContext> securityContexts = new ArrayList<>();
	securityContexts.add(SecurityContext.builder()
                .securityReferences(securityReference())
                .forPaths(PathSelectors.ant("/*/mongo-demo/**"))
                .build());
	
	
        return new Docket(DocumentationType.SWAGGER_2)
        	.groupName("【MongoDemo】")
                .select()
                .paths(PathSelectors.ant("/*/mongo-demo/**"))
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
                .forPaths(PathSelectors.ant("/*/mybatis-demo/**"))
                .build());


        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("【MybatisDemo】")
                .select()
                .paths(PathSelectors.ant("/*/mybatis-demo/**"))
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
//                .globalOperationParameters(pars)
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts);
    }

    @Bean
    public Docket customImplementationDemo3(){

        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder()
                .securityReferences(securityReference())
                .forPaths(PathSelectors.ant("/*/dataau/**"))
                .build());


        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("【数据权限demo】")
                .select()
                .paths(PathSelectors.ant("/*/dataau/**"))
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
//                .globalOperationParameters(pars)
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts);
    }

    @Bean
    public Docket customImplementationDemo4(){

        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder()
                .securityReferences(securityReference())
                .forPaths(PathSelectors.ant("/*/admin/**"))
                .build());


        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("【权限管理】")
                .select()
                .paths(PathSelectors.ant("/*/admin/**"))
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
//                .globalOperationParameters(pars)
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts);
    }
}
