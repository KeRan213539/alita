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
package top.klw8.alita.demo.cfg;

/**
 * 认证中心通用常量
 * 2018年11月23日 上午10:50:33
 */
public class SysContext {

    //===========================短信验证码相关===========================
    
    /**
     * SMS_CODE_TIMEOUT_SECOND : 短信验证码失效时间, 秒
     */
    public static final long SMS_CODE_TIMEOUT_SECOND = 300;
    
    /**
     * SMS_CODE_TIMEOUT_MSG : 短信验证码失效时间(描述)
     */
    public static final String SMS_CODE_TIMEOUT_MSG = "5分钟";
    
    /**
     * SMS_CODE_CACHE_PREFIX : 短信验证码缓存前缀
     */
    public static final String SMS_CODE_CACHE_PREFIX = "SMS_CODE_";
    
    /**
     * ONE_MINUTE : 一分钟
     */
    public static final long ONE_MINUTE = 60 * 1000;
    
}
