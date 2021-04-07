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
package top.klw8.alita.demo.web.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import top.klw8.alita.validator.annotations.MobilePhoneNumber;
import top.klw8.alita.validator.annotations.NotEmpty;

/**
 * @ClassName: SendSMSCodeRequest
 * @Description: 发送短信验证码接口请求类
 * @author klw
 * @date 2018年11月22日 下午4:33:41
 */
@Data
public class SendSMSCodeRequest {

    @ApiParam(value = "用户手机号(手机号,用户名二选一)")
    @NotEmpty(validatFailMessage="手机号不能为空")
    @MobilePhoneNumber(validatFailMessage="手机号格式不正确")
    private String userMobileNo;

    @ApiParam(value = "用户名(手机号,用户名二选一)")
    @NotEmpty(validatFailMessage="用户名不能为空")
    private String userName;

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取用户标识,优先获取userName,如果userName为空,则获取userMobileNo
     * @Date 2019/10/21 14:47
     * @param:
     * @return java.lang.String
     */
    public String getUserLoginIdentifier(){
        return userName != null ? userName : userMobileNo;
    }
    
}
