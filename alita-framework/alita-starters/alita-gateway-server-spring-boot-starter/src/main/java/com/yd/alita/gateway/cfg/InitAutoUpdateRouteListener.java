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
package com.yd.alita.gateway.cfg;

import com.yd.alita.gateway.route.AutoUpdateRouteTask;
import com.yd.alita.gateway.route.IRoutesUpdateHandle;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 初始化自动更新路由的监听
 * 2020/5/8 17:27
 */
public class InitAutoUpdateRouteListener implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${spring.cloud.nacos.discovery.serverAddr}")
    private String nacosServerAddr;

    @Value("${spring.cloud.nacos.discovery.autoUpdateRoute.enable:true}")
    private boolean isAutoUpdateRoute;

    @Value("${spring.cloud.nacos.discovery.autoUpdateRoute.runSecond:30}")
    private int taskRunSecond;

    @Autowired
    private IRoutesUpdateHandle routesUpdateHandle;

    private ExecutorService executor;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(StringUtils.isNotBlank(nacosServerAddr)){
            routesUpdateHandle.routesUpdate();
            if(isAutoUpdateRoute){
                if(taskRunSecond < 1){
                    taskRunSecond = 30;
                }
                initAutoUpdateRoute(new AutoUpdateRouteTask(routesUpdateHandle, taskRunSecond));
            }
        }
    }

    private void initAutoUpdateRoute(Runnable task){
        executor = Executors.newSingleThreadExecutor(r -> {
            Thread thread = new Thread(r, "autoUpdateRouteTasks");
            thread.setDaemon(true);
            return thread;
        });
        executor.execute(task);
    }

}
