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
package top.klw8.alita.starter.common;

/**
 * webapi 通用常量
 * 2018年12月6日 下午2:05:53
 */
public class WebApiContext {

    /**
     * CACHE_PREFIX_USER_AU : 用户的权限缓存前缀
     */
    public static final String CACHE_PREFIX_USER_AUS = "USER_AUS_";

    /**
     * 用户的资源权限缓存前缀
     */
    public static final String CACHE_PREFIX_USER_AUTHORITYS_RESOURCE = "USER_AUTHORITYS_RESOURCE_";

    /**
     * 用户资源权限缓存中的全局资源权限的KEY
     */
    public static final String CACHE_KEY_USER_PUBLIC_AUTHORITYS_RESOURCE = "USER_PUBLIC_AUTHORITYS_RESOURCE";
    
    /**
     * USER_AUS_TIME_OUT_SECOND : 用户的权限缓存时长(秒)
     */
    public static final long USER_AUS_TIME_OUT_SECOND = 60 * 60;
    
    /**
     * USER_AUS_TIME_OUT_SECOND_DEV : dev模式下用户的权限缓存时长(秒)
     */
    public static final long USER_AUS_TIME_OUT_SECOND_DEV = 60 * 60 * 24 * 30;
    
}
