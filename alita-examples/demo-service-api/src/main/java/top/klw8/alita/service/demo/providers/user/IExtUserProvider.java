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
package top.klw8.alita.service.demo.providers.user;

import top.klw8.alita.entitys.demo.mybatis.ExtUserInfo;
import top.klw8.alita.service.result.JsonResult;

import java.util.concurrent.CompletableFuture;

/**
 * 用户扩展信息dubbo提供者定义
 * 2019-08-19 14:40
 */
public interface IExtUserProvider {

    /**
     * 添加新的用户扩展信息
     * 16:27 2019-08-20
     * @param: extUserInfo
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     **/
    CompletableFuture<JsonResult> addUserExtInfo(ExtUserInfo extUserInfo);

    /**
     * 根据编号删除用户的扩展信息
     * 16:31 2019-08-20
     * @param: userId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     **/
    CompletableFuture<JsonResult> deleteUserExtInfo(String userId);

    /**
     * 更新用户的扩展信息
     * 16:32 2019-08-20
     * @param: extUserInfo
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     **/
    CompletableFuture<JsonResult> updateUserExtInfo(ExtUserInfo extUserInfo);

    /**
     * 用于获取用户的扩展信息
     * 14:42 2019-08-19
     * @param: userId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.entitys.demo.mybatis.ExtUserInfo>
     **/
    CompletableFuture<JsonResult> findExtByUserId(String userId);

    /**
     * 根据emial获取用户扩展信息
     * 14:46 2019-08-19
     * @param: userEmail
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     **/
    CompletableFuture<JsonResult> findExtByEmail(String userEmail);

    /**
     * 根据等级获取用户扩展信息
     * 14:47 2019-08-19
     * @param: userLevel
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     **/
    CompletableFuture<JsonResult> findExtsByLevel(Integer userLevel);

}
