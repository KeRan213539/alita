/*
 * Copyright 2018-2021, ranke (213539@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.web.cfg;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;

import io.swagger.annotations.Api;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import top.klw8.alita.starter.cfg.base.SwaggerConfigBase;

/**
 * 基础服务web接口的swagger配制,使用基础服务的webapi的swagger直接继承该类
 * 2019/11/4 15:08
 */
public class BasicWebApisSwaggerConfig extends SwaggerConfigBase {

    public BasicWebApisSwaggerConfig() {
        super();
    }

    @Bean
    public Docket customImplementationAu() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder()
                .securityReferences(securityReference())
                .forPaths(PathSelectors.ant("/**/admin/**"))
                .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("【权限管理】")
                .select()
                .paths(PathSelectors.ant("/**/admin/**"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts);
    }

    @Bean
    public Docket customImplementationUser() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder()
                .securityReferences(securityReference())
                .forPaths(PathSelectors.ant("/**/user/**"))
                .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("【用户】")
                .select()
                .paths(PathSelectors.ant("/**/user/**"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts);
    }


}
