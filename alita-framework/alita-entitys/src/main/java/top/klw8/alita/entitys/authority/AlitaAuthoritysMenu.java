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
import lombok.ToString;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.base.BaseEntity;

import java.util.List;

/**
 * 权限菜单表(url, 菜单)
 * 2018年11月28日 上午11:52:04
 */
@TableName(value = "alita_authoritys_menu")
@Getter
@Setter
@ToString
public class AlitaAuthoritysMenu extends BaseEntity implements IAssociatedApp {
    
    private static final long serialVersionUID = 4226666111547632644L;

    /**
     * authorityName : 所属应用的应用标识
     */
    @TableField("app_tag")
    private String appTag;

    /**
     * catlog : 权限所属权限目录
     */
    @TableField(exist=false)
    private AlitaAuthoritysCatlog catlog;

    /**
     * authorityName : 权限名称
     */
    @TableField("authority_name")
    private String authorityName;

    /**
     * 权限所属目录的ID
     */
    @TableField("catlog_id")
    private String catlogId;

    /**
     * URL类型权限所属MENU类型权限的ID
     */
    @TableField("menu_id")
    private String menuId;

    /**
     * 权限组名称，冗余字段
     */
    @TableField(value="catlog_name",exist=false)
    private String catlogName;

    /**
     * authorityUrl : 权限动作,根据类型来,如果是url那就放url,如果是菜单就放前端识别的视图标识(相对路径)
     */
    @TableField("authority_action")
    private String authorityAction;
    
    /**
     * authorityType : 权限类型
     */
    @TableField("authority_type")
    private AuthorityTypeEnum authorityType;
    
    /**
     * showIndex : 作为菜单的显示顺序,非菜单为0
     */
    @TableField("show_index")
    private Integer showIndex;
    
    /**
     * remark : 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * authorityList : 权限下的数据权限
     */
    @TableField(exist=false)
    private List<AlitaAuthoritysResource> dataSecuredList;
    
}
