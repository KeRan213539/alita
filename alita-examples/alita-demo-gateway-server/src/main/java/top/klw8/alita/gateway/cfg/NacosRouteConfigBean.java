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
package top.klw8.alita.gateway.cfg;

import lombok.Getter;
import lombok.Setter;

/**
 * 从nacos中读取路由配制的配制Bean
 * 2018年12月16日 下午9:14:10
 */
@Getter
@Setter
/**
 * 当时参考的: http://springcloud.cn/view/412
 * TODO: 因为以下原因暂时不做动态配制路由功能(已把当前类相关配制注解注释):
 * 1.没有清空当前所有配制的方式,只能做到动态添加和删除指定的
 * 2.现有方式是为启动的时候通过代码(或者配制文件)静态配制提供的,所以不会有需要清空已有配制的情况
 * 3.等spring cloud gateway官方还提供支持动态的方式,或者有了清空所有配制,或者更好的方式
 */
public class NacosRouteConfigBean {

    /**
     * nacosServerAddr : nacos 服务器地址
     */
    private String nacosServerAddr;
    
    /**
     * group : nacos配制中心的group
     */
    private String group = "DEFAULT_GROUP";
    
    /**
     * dataid : nacos配制中心的 dataid
     */
    private String dataid;
    
}
