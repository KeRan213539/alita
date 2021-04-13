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
package top.klw8.alita.service.result.code;

import top.klw8.alita.service.result.IResultCode;
import top.klw8.alita.service.result.ISubResultCode;

/**
 * 通用返回码
 * 2019/6/10 16:13
 */
public enum CommonResultCodeEnum implements ISubResultCode {

    OK("200", "请求处理成功"),

    ERROR("500", "请求处理出现错误"),

    NO_TOKEN("401", "请求中没有token"),

    TOKEN_ERR("403", "请求中没有token或者token不正确"),

    NO_PRIVILEGES("403", "没有权限"),

    REGISTER_NO("5001", "用户未注册"),

    BAD_PARAMETER("5002", "参数错误"),

    LOGIN_TIMEOUT("5003", "登录超时"),

    DATA_SECURED_NO_RES("5004", "数据权限注解没有配制资源和解析器"),

    APP_TAG_NOT_EXIST("5005", "【alita.authority.app.tag】 未配制"),


    ;


    /**
     * 所属分类, 写死
     * 2019/6/6 17:41
     * @Param
     * @return
     */
    private IResultCode classify = ResultCodeEnum.COMMON;

    /**
     * 业务code(3位)
     */
    private String subCode;


    /**
     * code 对应的消息
     */
    private String codeMsg;

    CommonResultCodeEnum(String subCode, String codeMsg){
        this.subCode = subCode;
        this.codeMsg = codeMsg;
    }

    @Override
    public String getCode() {
        return classify.getCode() + subCode;
    }

    @Override
    public String getCodeMsg() {
        return codeMsg;
    }

    @Override
    public IResultCode getClassify() {
        return classify;
    }
}
