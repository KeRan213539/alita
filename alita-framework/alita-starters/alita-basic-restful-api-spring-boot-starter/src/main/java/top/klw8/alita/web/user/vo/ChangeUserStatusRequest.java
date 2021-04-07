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

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ChangeUserStatusRequest
 * @Description: 禁用/启用用户接口请求
 * @date 2019/11/1 15:32
 */
@Getter
@Setter
public class ChangeUserStatusRequest {

    @Required(validatFailMessage = "用户ID不能为空")
    @NotEmpty(validatFailMessage = "用户ID不能为空")
    @ApiParam(value = "用户ID", required = true)
    private String userId;

    @Required(validatFailMessage = "是否启用不能为空")
    @NotEmpty(validatFailMessage = "是否启用不能为空")
    @ApiParam(value = "是否启用(true为启用,false为禁用)",
            required = true, allowableValues= "true,false")
    private Boolean enabled;
}
