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
 * 权限相关
 * 2019/8/15 16:52
 */
public enum AuthorityResultCodeEnum implements ISubResultCode {

    CATLOG_NOT_EXIST("001", "权限目录不存在"),

    ROLE_NOT_EXIST("002", "角色不存在"),

    AUTHORITY_NOT_EXIST("003", "权限不存在"),

    USER_NOT_EXIST("004", "用户不存在"),

    SYSTEM_AUTHORITYS_RESOURCE_HAS_EXIST("005", "该资资源权限已存在(全局或者同权限中,资源权限资源标识不能相同)"),

    ;
    /**
     * 所属分类, 写死
     * 2019/6/6 17:41
     * @Param
     * @return
     */
    private IResultCode classify = ResultCodeEnum.AUTHORITY;

    /**
     * 业务code(3位)
     */
    private String subCode;


    /**
     * code 对应的消息
     */
    private String codeMsg;

    AuthorityResultCodeEnum(String subCode, String codeMsg){
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
