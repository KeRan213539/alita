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
import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.validator.annotations.MobilePhoneNumber;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Password;
import top.klw8.alita.validator.annotations.Required;

/**
 * @author klw
 * @ClassName: AlitaUserAccountVo
 * @Description: 用户信息
 * @date 2019-01-25 16:26:07
 */
@Getter
@Setter
public class AlitaUserAccountVo {

    private static final long serialVersionUID = 7921978011249125659L;

    @ApiParam(value = "用户手机号", required = true)
    @Required(validatFailMessage = "用户手机号不能为空")
    @NotEmpty(validatFailMessage = "用户手机号不能为空")
    @MobilePhoneNumber
    private String userPhoneNum;

    @ApiParam(value = "用户密码", required = true)
    @Required(validatFailMessage = "用户密码不能为空")
    @NotEmpty(validatFailMessage = "用户密码不能为空")
    @Password
    private String userPwd;

}
