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

import io.swagger.annotations.ApiParam;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

/**
 * 
 * 2020-07-17
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class AuthorityAppRequest {

    @NotEmpty(validatFailMessage = "appTag不能为空")
    @Required(validatFailMessage = "appTag不能为空")
    @ApiParam(value = "appTag")
    private String appTag;


    @ApiParam(value = "appName")
    private String appName;

    @ApiParam(value = "remark")
    private String remark;
}
