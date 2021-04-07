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
package com.yd.alita.gateway.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

/**
 * @ClassName: DynamicRouteService
 * @Description: 动态路由服务
 * @author klw
 * @date 2018年12月16日 下午8:27:02
 */
public class DynamicRouteService implements ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;

    /**
     * @param definition
     * @return 添加数量
     * @Title: addRoute
     * @author klw
     * @Description: 增加路由
     */
    public int addRoute(RouteDefinition definition) {
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        publisher.publishEvent(new RefreshRoutesEvent(this));
        return 1;
    }

    /**
     * @param definition
     * @return 更新数量
     * @Title: updateRoute
     * @author klw
     * @Description: 更新路由
     */
    public int updateRoute(RouteDefinition definition) {
        try {
            routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            // 异常说明没有该路由可以删除,直接略过,继续增加
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            publisher.publishEvent(new RefreshRoutesEvent(this));
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * @param id
     * @return
     * @Title: deleteRoute
     * @author klw
     * @Description: 删除路由
     */
    public Mono<ResponseEntity<Object>> deleteRoute(String id) {
        return routeDefinitionWriter.delete(Mono.just(id))
                .then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())))
                .onErrorResume(t -> t instanceof NotFoundException,
                        t -> Mono.just(ResponseEntity.notFound().build()));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

}
