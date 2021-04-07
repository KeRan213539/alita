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
package top.klw8.alita.entitys.authority;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.entitys.base.BaseEntity;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: SystemDataSecured
 * @Description: 数据权限表实体
 * @date 2020/4/30 11:47
 */
@TableName("sys_data_secured")
@Getter
@Setter
@ToString
public class SystemDataSecured extends BaseEntity implements IAssociatedApp {

    private static final long serialVersionUID = 4226666111547632645L;

    /**
     * @author klw
     * @Fields authorityName : 所属应用的应用标识
     */
    @TableField("app_tag")
    private String appTag;

    /**
     * @author klw(213539@qq.com)
     * @Description: 所属权限ID
     */
    @TableField(value = "authoritys_id", updateStrategy = FieldStrategy.IGNORED)
    private String authoritysId;

    /**
     * @author klw(213539@qq.com)
     * @Description: 资源标识
     */
    @TableField("resource")
    private String resource;

    /**
     * @author klw(213539@qq.com)
     * @Description: 备注/名称
     */
    @TableField("remark")
    private String remark;

    /**
     * @author klw(213539@qq.com)
     * @Description: 所属权限的权限url,冗余数据,非数据库字段
     */
    @TableField(value = "authority_url", exist = false)
    private String authorityUrl;

}
