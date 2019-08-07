package top.klw8.alita.authorization.cfg;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName: SwaggerConfig
 * @Description: SwaggerConfig
 * @author klw
 * @date 2018年9月14日 09:54:14
 */
@Configuration  
@EnableSwagger2
@Profile("dev")  // 仅在dev模式(application.yml中的spring.profiles.active=dev时)下才会执行该配制
public class SwaggerConfig extends WebMvcConfigurationSupport  {

    public static final String VERSION = "1.0.0";
    
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("认证API")
                .description("&nbsp;")
//                .license("Apache 2.0")
//                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("")
                .version(VERSION)
                .contact(new Contact("klw","", "213539@qq.com"))
                .build();
    }
    
    @Bean
    public Docket customImplementation(){
	// 统一参数
//	ParameterBuilder cookiesPar = new ParameterBuilder();  
//        List<Parameter> pars = new ArrayList<Parameter>();  
//        cookiesPar.name(SysConstant.COOKIE_KEY_SESSION_ID).description("session id").modelRef(new ModelRef("string")).parameterType("cookie").required(false).build();  
//        pars.add(cookiesPar.build());  
	
        return new Docket(DocumentationType.SWAGGER_2)
        	.groupName("【接口】")
                .select()
                .paths(PathSelectors.ant("/oauth/**"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
//                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/static/");
        
        registry.addResourceHandler("/js/**")
        	.addResourceLocations("classpath:/static/js/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }
}
