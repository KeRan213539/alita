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
 * 应用渠道vo.
 *
 * 2020/9/9 16:51
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class AuthoritysAppChannelRequest {
    
    /**
     * 渠道标识(主键)
     */
    @NotEmpty(validatFailMessage = "channelTag不能为空")
    @Required(validatFailMessage = "channelTag不能为空")
    @ApiParam(value = "channelTag")
    private String channelTag;
    
    /**
     * 渠道所属应用的标识
     */
    @NotEmpty(validatFailMessage = "appTag不能为空")
    @Required(validatFailMessage = "appTag不能为空")
    @ApiParam(value = "appTag")
    private String appTag;
    
    /**
     * 渠道密码(明文)
     */
    @NotEmpty(validatFailMessage = "渠道密码不能为空")
    @Required(validatFailMessage = "渠道密码不能为空")
    @ApiParam(value = "渠道密码")
    private String channelPwd;
    
    /**
     * 渠道支持的登录方式,多种方式用逗号隔开
     */
    @NotEmpty(validatFailMessage = "渠道支持的登录方式不能为空")
    @Required(validatFailMessage = "渠道支持的登录方式不能为空")
    @ApiParam(value = "渠道支持的登录方式,多种方式用逗号隔开")
    private String channelLoginType;
    
    /**
     * 备注
     */
    @ApiParam(value = "备注")
    private String remark;

}
