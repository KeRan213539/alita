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
package top.klw8.alita.service.api.authority;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.klw8.alita.entitys.authority.SystemAuthoritysApp;
import top.klw8.alita.service.result.JsonResult;

import java.util.concurrent.CompletableFuture;

/**
 * @author zhaozheng
 * @description: 应用标识 dubbo 服务提供者
 * @date: 2020-07-16
 */

public interface IAuthorityAppProvider {

    /**
     * @author zhaozheng
     * @Description:  新增应用
     * @Date 2020/7/16
     * @param: authorityApp
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addAuthorityApp(SystemAuthoritysApp authorityApp);

    /**
     * @author zhaozheng
     * @Description:  修改应用 应用标识不可修改
     * @Date 2020/7/16
     * @param: authorityApp
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> updateAuthorityApp(SystemAuthoritysApp authorityApp);

    /**
     * @author zhaozheng
     * @Description:  删除应用
     * @Date 2020/7/16
     * @param: authorityApp
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> deleteAuthorityApp(String appTag);

    /**
     * @author zhaozheng
     * @Description:  应用 详情
     * @Date 2020/7/16
     * @param: authorityApp
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> authorityApp(String appTag);

    /**
     * @author zhaozheng
     * @Description:  应用 分页列表
     * @Date 2020/7/16
     * @param: authorityApp
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> authorityAppPage(String appTag, String appName, String remark, Page<SystemAuthoritysApp> page);

    /**
     * @author zhaozheng
     * @Description:  应用列表 不分页
     * @Date 2020/7/16
     * @param: authorityApp
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> authorityAppList();
}
