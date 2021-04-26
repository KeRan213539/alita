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
package top.klw8.alita.web.authority.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiParam;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.entitys.authority.enums.ResourceType;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

/**
 * 资源权限保存请求参数
 * 2020/5/20 17:32
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class SaveAuthoritysResourceRequest {

    @NotEmpty(validatFailMessage = "id不能为空")
    @ApiParam(value = "id")
    private String id;


    /**
     * 所属权限ID
     */
    @Required(validatFailMessage = "所属权限ID不能为空")
    @NotEmpty(validatFailMessage = "所属权限ID不能为空")
    @ApiParam(value = "所属权限ID(全局资源权限传: PUBLIC_RESOURCE_AUTHORITY)", required=true)
    private String authoritysId;

    /**
     * 所属应用的应用标识
     */
    @NotEmpty(validatFailMessage = "所属应用标识不能为空")
    @ApiParam(value = "所属应用标识(全局资源权限必传,普通资源权限不传)")
    private String appTag;

    /**
     * 资源类型: 按钮/资源权限等.
     */
    @NotEmpty(validatFailMessage = "资源类型不能为空")
    @ApiParam(value = "资源类型: 按钮/资源权限等: <br /> BUTTON <br /> DATA")
    private ResourceType resType;

    /**
     * 资源标识
     */
    @Required(validatFailMessage = "资源标识不能为空")
    @NotEmpty(validatFailMessage = "资源标识不能为空")
    @ApiParam(value = "资源标识", required=true)
    private String resource;

    /**
     * 备注/名称
     */
    @ApiParam(value = "备注/名称")
    private String remark;

}
