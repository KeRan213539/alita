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


import lombok.extern.slf4j.Slf4j;

/**
 * 自动更新路由的任务
 * 2019/11/16 16:36
 */
@Slf4j
public class AutoUpdateRouteTask implements Runnable {

    private long SLEEP_TIME_MILLIS;

    private IRoutesUpdateHandle routesUpdateHandle;

    public AutoUpdateRouteTask(IRoutesUpdateHandle routesUpdateHandle, int taskRunSecond){
        this.routesUpdateHandle = routesUpdateHandle;
        SLEEP_TIME_MILLIS = taskRunSecond * 1000;
    }

    @Override
    public void run() {
        while (true){
            log.debug("=============【AutoUpdateRouteTask】开始运行===================");
            routesUpdateHandle.routesUpdate();
            log.debug("=============【AutoUpdateRouteTask】运行结束===================");
            try {
                Thread.sleep(SLEEP_TIME_MILLIS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
