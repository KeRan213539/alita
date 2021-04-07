/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.demo.cfg;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
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
