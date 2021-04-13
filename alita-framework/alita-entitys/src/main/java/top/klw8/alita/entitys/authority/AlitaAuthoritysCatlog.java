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


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.base.BaseEntity;

import java.util.List;

/**
 * 权限的目录
 * 2018年11月28日 上午11:53:50
 */
@TableName("alita_authoritys_catlog")
@Getter
@Setter
//@EqualsAndHashCode(callSuper = false, exclude = {"authorityList"})
//@ToString(callSuper = false, exclude ={"authorityList"})
public class AlitaAuthoritysCatlog extends BaseEntity implements IAssociatedApp {

    private static final long serialVersionUID = -8415317762418810314L;

    /**
     * 上级ID,为空表式顶级.
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * authorityName : 所属应用的应用标识
     */
    @TableField("app_tag")
    private String appTag;

    /**
     * catlogName : 目录名称
     */
    @TableField("catlog_name")
    private String catlogName;
    
    /**
     * showIndex : 显示顺序
     */
    @TableField("show_index")
    private Integer showIndex;
    
    /**
     * remark : 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * authorityList : 目录下的权限(冗余数据)
     */
    @TableField(exist=false)
    private List<AlitaAuthoritysMenu> authorityList;
    
}
