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
package top.klw8.alita.web.user.vo;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: SaveUserRolesRequest
 * @Description: 保存用户拥有的角色(替换原有角色)接口请求
 * @date 2019/10/29 16:36
 */
@Getter
@Setter
public class SaveUserRolesRequest {

    @Required("用户ID不能为空")
    @NotEmpty("用户ID不能为空")
    @ApiParam(value = "用户ID", required = true)
    private String userId;

    @Required("多个角色ID不能为空")
    @NotEmpty("多个角色ID不能为空")
    @ApiParam(value = "多个角色ID", required = true)
    private List<String> roleIds;

    @NotEmpty("appTag并不能为空")
    @ApiParam(value = "操作的角色所属应用的appTag")
    private String appTag;
}
