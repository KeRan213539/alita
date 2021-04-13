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
package top.klw8.alita.service.pojos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;

import java.util.List;

/**
 * 给视图用的权限pojo, 可以标识用户是否拥有该权限
 * 2019/10/18 11:14
 */
@Getter
@Setter
@ToString
public class SystemAuthorityPojo implements java.io.Serializable, Comparable<SystemAuthorityPojo> {


    private static final long serialVersionUID = 7006777206739758923L;
    /**
     * id : 主键ID
     */
    private String id;

    /**
     * authorityName : 所属应用的应用标识
     */
    private String appTag;

    /**
     * authorityName : 权限名称
     */
    private String authorityName;

    /**
     * 权限所属目录的ID
     */
    private String catlogId;

    /**
     * URL类型权限所属MENU类型权限的ID
     */
    private String menuId;

    /**
     * authorityUrl : 权限动作,根据类型来,如果是url那就放url,如果是菜单就放前端识别的视图标识(相对路径)
     */
    private String authorityAction;

    /**
     * authorityType : 权限类型
     */
    private AuthorityTypeEnum authorityType;

    /**
     * showIndex : 作为菜单的显示顺序,非菜单为0
     */
    private Integer showIndex;

    /**
     * remark : 备注
     */
    private String remark;

    /**
     * 该权限中的数据权限
     */
    private List<SystemDataSecuredPojo> dsList;

    /**
     * 该权限中的按钮权限
     */
    private List<SystemAuthorityPojo> menuList;

    /**
     * 当前用户是否拥有该权限
     */
    private boolean currUserHas = false;


    @Override
    public int compareTo(SystemAuthorityPojo o) {
        return this.showIndex - o.showIndex;
    }
}
