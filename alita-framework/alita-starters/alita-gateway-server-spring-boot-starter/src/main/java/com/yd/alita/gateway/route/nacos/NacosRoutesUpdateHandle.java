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
package com.yd.alita.gateway.route.nacos;

import com.yd.alita.gateway.route.DynamicRouteService;
import com.yd.alita.gateway.route.IRoutesUpdateHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: NacosRoutesUpdateHandle
 * @Description: 从Nacos中更新路由
 * @date 2019/11/18 11:19
 */
@Slf4j
public class NacosRoutesUpdateHandle implements IRoutesUpdateHandle {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String myServiceName;

    @Autowired
    private DynamicRouteService dynamicRouteService;

    private Map<String, RouteDefinition> cachedRouteMap = new HashMap<>();

    /**
     * @author klw(213539@qq.com)
     * @Description: 从nacos中获取服务并更新路由
     * @Date 2019/11/18 9:11
     * @param:
     * @return void
     */
    @Override
    public void routesUpdate() {
        // 从nacos命名空间中获取到的所有服务名称，这其中还包含了自己的服务名
        List<String> serviceNames = discoveryClient.getServices();
        Map<String, RouteDefinition> toAddRouteMap = new HashMap<>();
        Map<String, String> serviceNamesMap = new HashMap<>();
        Map<String, RouteDefinition> toRemoveRouteMap = new HashMap<>();
        // 服务列表和缓存中的路由对比,找出新的服务待添加
        for (String serviceName : serviceNames) {
            if (!serviceName.equals(myServiceName)) {

                List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
                if(instances.size() < 1){
                    continue;
                }

                if(cachedRouteMap.get(serviceName) == null){
                    RouteDefinition routeDefinition = new RouteDefinition();
                    routeDefinition.setId(UUID.randomUUID().toString());
                    routeDefinition.setUri(URI.create("lb://" + serviceName));
                    PredicateDefinition predicateDefinition = new PredicateDefinition();
                    predicateDefinition.setName("Path");
                    predicateDefinition.addArg("pattern", "/" + serviceName + "/**");
                    routeDefinition.getPredicates().add(predicateDefinition);
                    FilterDefinition filterDefinition = new FilterDefinition();
                    filterDefinition.setName("Hystrix");
                    filterDefinition.addArg("name", serviceName + "Hystrix");
                    filterDefinition.addArg("fallbackUri", "forward:/fallback");
                    routeDefinition.getFilters().add(filterDefinition);
                    toAddRouteMap.put(serviceName, routeDefinition);
                }
                // 将serviceName加到临时map中,以方便下面查找需要移除的
                serviceNamesMap.put(serviceName, "1");
            }
        }
        // 缓存中的路由和服务列表对比,找出需要移除的
        cachedRouteMap.forEach((key, value) -> {
            if(serviceNamesMap.get(key) == null){
                toRemoveRouteMap.put(key, value);
            }
        });

        // 将待添加的加到路由和缓存中
        toAddRouteMap.forEach((key, value) -> {
            dynamicRouteService.addRoute(value);
            cachedRouteMap.put(key, value);
            log.info("+++++++服务【{}】已添加到路由中", key);
        });

        // 将待移除的从路由和缓存中移除
        toRemoveRouteMap.forEach((key, value) -> {
            dynamicRouteService.deleteRoute(value.getId());
            cachedRouteMap.remove(key);
            log.info("-------服务【{}】已从路由中移除", key);
        });
    }
}
