package com.yd.alita.gateway.cfg;

import com.yd.alita.gateway.route.AutoUpdateRouteTask;
import com.yd.alita.gateway.route.DynamicRouteService;
import com.yd.alita.gateway.route.IRoutesUpdateHandle;
import com.yd.alita.gateway.route.nacos.NacosRoutesUpdateHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.*;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AutoUpdateRouteConfig
 * @Description: 自动更新路由的配制
 * @date 2019/11/16 16:37
 */
@Configuration
@Import({DynamicRouteService.class, NacosRoutesUpdateHandle.class, InitAutoUpdateRouteListener.class})
public class AutoUpdateRouteConfig {


}
