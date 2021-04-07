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
package top.klw8.alita.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author klw
 * @ClassName: GatewayServerApplication
 * @Description: 启动 spring cloud gateway (路由网关)
 * @date 2018-11-14 15:42:54
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class GatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class);
    }

    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }

    @Autowired
    DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    String myServiceName;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        // 获取从nacos命名空间中获取到的所有服务名称，这其中还包含了自己的服务名
        List<String> servers = discoveryClient.getServices();
        // 循环将注册服务都添加到route中
        String serviceId = "";
        RouteLocatorBuilder.Builder builder1 = builder.routes();
        for (String server : servers) {
            if (!server.equals(myServiceName)) {
                // 获取服务列表
                List<ServiceInstance> instances = discoveryClient.getInstances(server);
                for (ServiceInstance instance : instances) {
//                    serviceId = instance.getServiceId();
//                    break;
                    builder1.route(p -> p
                                    .path("/" + instance.getServiceId() + "/**")
                                    .filters(f -> f
                                            .hystrix(config -> config
                                                    .setName(instance.getServiceId() + "Hystrix")
                                                    .setFallbackUri("forward:/fallback")))
                                    .uri("lb://" + instance.getServiceId()));
                }
            }
        }
        return builder1.build();
    }

}
