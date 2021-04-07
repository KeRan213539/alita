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
package top.klw8.alita.service.pojos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: SystemDataSecured
 * @Description: 给视图用的数据权限pojo, 可以标识用户是否拥有该权限
 * @date 2020-05-15 10:47:43
 */
@Getter
@Setter
@ToString
public class SystemDataSecuredPojo implements java.io.Serializable {

    private static final long serialVersionUID = 4226666111547632645L;

    /**
     * @author klw
     * @Fields id : 主键ID
     */
    private String id;

    /**
     * @author klw
     * @Fields authorityName : 所属应用的应用标识
     */
    private String appTag;

    /**
     * @author klw(213539@qq.com)
     * @Description: 所属权限ID
     */
    private String authoritysId;

    /**
     * @author klw(213539@qq.com)
     * @Description: 资源标识
     */
    private String resource;

    /**
     * @author klw(213539@qq.com)
     * @Description: 备注/名称
     */
    private String remark;

    /**
     * @author klw(213539@qq.com)
     * @Description: 当前用户是否拥有该权限
     */
    private boolean currUserHas = false;

}
