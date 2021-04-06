package top.klw8.alita.starter.cfg.base;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author klw
 * @ClassName: SwaggerConfigBase
 * @Description: SwaggerConfig 的父类,每个项目自己配制 Swagger, 继承这个base
 * @date 2018年12月6日 上午10:01:31
 */
public class SwaggerConfigBase {

    public static final String VERSION = "1.0.0";

    private List<SecurityReference> defaultAuth;

    private List<SecurityScheme> securitySchemes;

    protected SwaggerConfigBase() {
        securitySchemes = new ArrayList<>();
        securitySchemes.add(new ApiKey("Authorization", "Authorization", "header"));

        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        defaultAuth = new ArrayList<>();
        defaultAuth.add(new SecurityReference("Authorization", authorizationScopes));
    }

    protected ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("后台管理API").description("&nbsp;")
                 .license("Apache 2.0")
                 .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("").version(VERSION)
                .contact(new Contact("klw", "", "213539@qq.com")).build();
    }


    protected List<SecurityScheme> securitySchemes() {
        return securitySchemes;
    }

    protected List<SecurityReference> securityReference() {
        return defaultAuth;
    }

    @Bean
    public Docket customImplementationDevHelper() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("【开发工具】")
                .select()
                .paths(PathSelectors.ant("/devHelper/**"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .apiInfo(apiInfo());
    }

}
