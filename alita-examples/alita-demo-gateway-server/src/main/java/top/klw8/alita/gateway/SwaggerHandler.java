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
package top.klw8.alita.gateway;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import springfox.documentation.swagger.web.*;

import java.util.Optional;

/**
 * @author klw
 * @ClassName: SwaggerHandler
 * @Description: swagger的端点
 * @date 2019/7/12 16:53
 */
@RestController
@RequestMapping("/swagger-resources")
public class SwaggerHandler {
    @Autowired(required = false)

    private SecurityConfiguration securityConfiguration;

    @Autowired(required = false)

    private UiConfiguration uiConfiguration;

    private final SwaggerResourcesProvider swaggerResources;

    @Autowired

    public SwaggerHandler(SwaggerResourcesProvider swaggerResources) {

        this.swaggerResources = swaggerResources;

    }

    @GetMapping("/configuration/security")

    public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {

        return Mono.just(new ResponseEntity<>(

                Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()), HttpStatus.OK));

    }

    @GetMapping("/configuration/ui")

    public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {

        return Mono.just(new ResponseEntity<>(

                Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK));

    }

    @GetMapping("")

    public Mono<ResponseEntity> swaggerResources() {

        return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));

    }
}
