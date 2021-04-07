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
package top.klw8.alita.web.authority.vo;


import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

import java.util.List;

/**
 * @ClassName: SaveRoleRequest
 * @Description: 保存角色的请求
 * @author klw
 * @date 2018年12月4日 下午5:18:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SaveRoleRequest {

    @NotEmpty(validatFailMessage = "角色ID不能为空")
    @ApiParam(value = "角色ID")
    private String roleId;

    @Required(validatFailMessage = "所属应用标识不能为空")
    @NotEmpty(validatFailMessage = "所属应用标识不能为空")
    @ApiParam(value = "所属应用标识", required=true)
    private String appTag;

    @Required(validatFailMessage = "角色名称不能为空")
    @NotEmpty(validatFailMessage = "角色名称不能为空")
    @ApiParam(value = "角色名称", required=true)
    private String roleName;
    
    @ApiParam(value = "备注")
    private String remark;

    @ApiParam(value = "要复制的角色ID,可以从该角色中复制权限到新的角色,如果此参数有值,则 auIdList 参数将被忽略")
    private String copyAuFromRoleId;

    @ApiParam(value = "该角色中的权限,如果要复制的角色ID有值,则此参数将被忽略")
    private List<String> auIdList;
    
}
