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
package top.klw8.alita.gateway.cfg;

import java.util.concurrent.Executor;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import lombok.extern.slf4j.Slf4j;
import top.klw8.alita.gateway.route.DynamicRouteServiceImpl;

/**
 * @ClassName: NacosRouteConfig
 * @Description: 从nacos中读取路由配制的配制
 * @author klw
 * @date 2018年12月16日 下午8:53:20
 */
//@Configuration
//@EnableConfigurationProperties(NacosRouteConfigBean.class)
@Slf4j
/**
 * 当时参考的: http://springcloud.cn/view/412
 * TODO: 因为以下原因暂时不做动态配制路由功能(已把当前类相关配制注解注释):
 * 1.没有清空当前所有配制的方式,只能做到动态添加和删除指定的
 * 2.现有方式是为启动的时候通过代码(或者配制文件)静态配制提供的,所以不会有需要清空已有配制的情况
 * 3.等spring cloud gateway官方还提供支持动态的方式,或者有了清空所有配制,或者更好的方式
 */
public class NacosRouteConfig {

    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;
    
    @Resource
    private NacosRouteConfigBean nacosRouteConfigBean;
    
    /**
     * 监听Nacos Server下发的动态路由配置
     * @param dataId
     * @param group
     */
    @Bean
    public void dynamicRouteByNacosListener (){
	
	String nacosServerAddr = nacosRouteConfigBean.getNacosServerAddr();
	String dataid = nacosRouteConfigBean.getDataid();
	if(StringUtils.isBlank(nacosServerAddr)) {
	    log.error("---------------------------------------------------------------------");
	    log.error("alita.gatewayServer.routesCfgInNacos.nacosServerAddr 没有配制!");
	    log.error("---------------------------------------------------------------------");
	    System.exit(1); 
	}
	if(StringUtils.isBlank(dataid)) {
	    log.error("---------------------------------------------------------------------");
	    log.error("alita.gatewayServer.routesCfgInNacos.dataid 没有配制!");
	    log.error("---------------------------------------------------------------------");
	    System.exit(1); 
	}
	
	String group = nacosRouteConfigBean.getGroup();
	
	
        try {
            ConfigService configService=NacosFactory.createConfigService(nacosServerAddr);
            String content = configService.getConfig(dataid, group, 5000);
            System.out.println(content);
            configService.addListener(dataid, group, new Listener()  {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    RouteDefinition definition= JSON.parseObject(configInfo,RouteDefinition.class);
                    dynamicRouteService.updateRoute(definition);
                }
                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException ex) {
            log.error("---------------------------------------------------------------------");
	    log.error("nacos监听异常,程序退出", ex);
	    log.error("---------------------------------------------------------------------");
	    System.exit(1); 
        }
    }
    
}
