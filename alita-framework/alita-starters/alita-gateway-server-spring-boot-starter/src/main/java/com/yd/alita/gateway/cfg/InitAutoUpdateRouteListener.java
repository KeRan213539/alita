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
 * @author klw(213539 @ qq.com)
 * @ClassName: InitAutoUpdateRouteListener
 * @Description: 初始化自动更新路由的监听
 * @date 2020/5/8 17:27
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
