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

import java.util.List;

/**
 * 返回给视图的菜单目录
 * 2019/10/18 10:25
 */
@Getter
@Setter
@ToString
public class SystemAuthorityCatlogPojo implements java.io.Serializable, Comparable<SystemAuthorityCatlogPojo> {

    private static final long serialVersionUID = 3822658664344722701L;
    /**
     * id : 主键ID
     */
    private String id;

    /**
     * authorityName : 所属应用的应用标识
     */
    private String appTag;

    /**
     * roleName : 角色名称
     */
    private String catlogName;

    /**
     * showIndex : 显示顺序
     */
    private Integer showIndex;

    /**
     * remark : 备注
     */
    private String remark;

    /**
     * authorityList : 角色下的权限
     */
    private List<SystemAuthorityPojo> authorityList;

    @Override
    public int compareTo(SystemAuthorityCatlogPojo o) {
        return this.showIndex - o.showIndex;
    }
}
