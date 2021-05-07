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
package top.klw8.alita.service.api.authority;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.klw8.alita.entitys.authority.AlitaAuthoritysApp;
import top.klw8.alita.entitys.authority.AlitaAuthoritysAppChannel;
import top.klw8.alita.service.result.JsonResult;

import java.util.concurrent.CompletableFuture;

/**
 * 应用标识 dubbo 服务提供者
 * 2020-07-16
 */

public interface IAuthorityAppProvider {

    /**
     *  新增应用
     * 2020/7/16
     * @param: authorityApp
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addAuthorityApp(AlitaAuthoritysApp authorityApp);

    /**
     *  修改应用 应用标识不可修改
     * 2020/7/16
     * @param: authorityApp
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> updateAuthorityApp(AlitaAuthoritysApp authorityApp);

    /**
     *  删除应用
     * 2020/7/16
     * @param: authorityApp
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> deleteAuthorityApp(String appTag);

    /**
     *  应用 详情
     * 2020/7/16
     * @param: authorityApp
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> authorityApp(String appTag);

    /**
     *  应用 分页列表
     * 2020/7/16
     * @param: authorityApp
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> authorityAppPage(String appTag, String appName, String remark, Page<AlitaAuthoritysApp> page);

    /**
     *  应用列表 不分页
     * 2020/7/16
     * @param: authorityApp
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> authorityAppList();

    /**
     * 新增应用渠道.
     * @param authorityAppChannel
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addAuthorityAppChannel(AlitaAuthoritysAppChannel authorityAppChannel);

    /**
     * 修改应用渠道 应用标识不可修改.
     * @param authorityAppChannel
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> updateAuthorityAppChannel(AlitaAuthoritysAppChannel authorityAppChannel);

    /**
     * 删除应用渠道.
     * @param channelTag
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> deleteAuthorityAppChannel(String channelTag);

    /**
     * 应用渠道详情.
     * @param channelTag
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> authorityAppChannelInfo(String channelTag);

    /**
     * 应用渠道 分页列表.
     * @param appTag 应用标识
     * @param channelTag 渠道标识
     * @param remark 备注
     * @param page 分页参数
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> authorityAppChannelPage(String appTag, String channelTag, String remark, Page<AlitaAuthoritysAppChannel> page);

    /**
     * 应用渠道列表 不分页.
     * @param
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> authorityAppChannelList(String appTag);
}
