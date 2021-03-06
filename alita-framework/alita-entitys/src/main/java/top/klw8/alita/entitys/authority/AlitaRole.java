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
package top.klw8.alita.entitys.authority;

import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.base.BaseEntity;

/**
 * 系统角色
 * 2018年11月28日 上午11:54:15
 */
@TableName("alita_role")
@Getter
@Setter
//@EqualsAndHashCode(callSuper = false, exclude = {"authorityList"})
//@ToString(callSuper = false, exclude ={"authorityList"})
public class AlitaRole extends BaseEntity implements IAssociatedApp {

    private static final long serialVersionUID = -2919173399468066019L;

    /**
     * authorityName : 所属应用的应用标识
     */
    @TableField("app_tag")
    private String appTag;
    
    /**
     * roleName : 角色名称
     */
    @TableField("role_name")
    private String roleName;
    
    /**
     * remark : 备注
     */
    @TableField("remark")
    private String remark;
    
    /**
     * authorityList : 角色下的权限(冗余数据)
     */
    @TableField(exist=false)
    private List<AlitaAuthoritysMenu> authorityList;

    /**
     * 角色拥有的资源权限
     */
    @TableField(exist=false)
    private List<AlitaAuthoritysResource> authoritysResourceList;
    
}
