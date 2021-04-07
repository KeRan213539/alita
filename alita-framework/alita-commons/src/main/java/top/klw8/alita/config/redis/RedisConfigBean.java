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
package top.klw8.alita.config.redis;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @ClassName: RedisConfigBean
 * @Description: redis配制bean
 * @author klw
 * @date 2018年10月31日 上午10:33:40
 */
@ConfigurationProperties(prefix="alita.redis")
@Getter
@Setter
public class RedisConfigBean {

    /**
     * @author klw(213539@qq.com)
     * @Description: 是否开启 RedisUtils,默认false
     */
    private boolean enabled = false;

    /**
     * @author klw(213539@qq.com)
     * @Description: 默认的连接是否是集群,默认false
     */
    private Boolean defaultIsCluster = false;
    
    private String defaultHost;
    
    private Integer defaultPort;
    
    private String defaultPass;

    private List<String> extendHosts;
    
    private List<Integer> extendPorts;
    
    private List<String> extendPasss;

    /**
     * @author klw(213539@qq.com)
     * @Description: 扩展的连接是否是集群,默认false
     */
    private List<Boolean> extendIsCluster;
    
    /**
     * @author klw
     * @Fields extendBeanId : 其他的redis服务器的 spring bean id
     */
    private List<String> extendBeanIds;
    
}
