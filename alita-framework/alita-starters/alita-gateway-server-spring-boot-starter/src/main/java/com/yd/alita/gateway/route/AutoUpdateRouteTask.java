package com.yd.alita.gateway.route;


import lombok.extern.slf4j.Slf4j;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AutoUpdateRouteTask
 * @Description: 自动更新路由的任务
 * @date 2019/11/16 16:36
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
