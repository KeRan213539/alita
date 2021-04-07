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
package top.klw8.alita.providers.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import top.klw8.alita.entitys.authority.SystemAuthoritysApp;
import top.klw8.alita.service.api.authority.IAuthorityAppProvider;
import top.klw8.alita.service.authority.IAuthorityAppService;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.starter.service.common.ServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author zhaozheng
 * @description:
 * @date: 2020-07-16
 */
@Slf4j
@DubboService(async = true)
public class AuthorityAppProviderImpl implements IAuthorityAppProvider {

    @Autowired
    private IAuthorityAppService appService;


    @Override
    public CompletableFuture<JsonResult> addAuthorityApp(SystemAuthoritysApp authorityApp) {
        return ServiceUtil.buildFuture(appService.addAuthorityApp(authorityApp));
    }

    @Override
    public CompletableFuture<JsonResult> updateAuthorityApp(SystemAuthoritysApp authorityApp) {
        return ServiceUtil.buildFuture(appService.updateAuthorityApp(authorityApp));
    }

    @Override
    public CompletableFuture<JsonResult> deleteAuthorityApp(String appTag) {
        return ServiceUtil.buildFuture(appService.deleteAuthorityApp(appTag));
    }

    @Override
    public CompletableFuture<JsonResult> authorityApp(String appTag) {
        SystemAuthoritysApp authorityApp = new SystemAuthoritysApp();
        authorityApp.setAppTag(appTag);
        return ServiceUtil.buildFuture(appService.findAuthorityApp(authorityApp));
    }

    @Override
    public CompletableFuture<JsonResult> authorityAppPage(String appTag, String appName, String remark, Page<SystemAuthoritysApp> page) {
        List<OrderItem> orders = new ArrayList<>(1);
        orders.add(OrderItem.desc("app_tag"));
        page.setOrders(orders);
        return ServiceUtil.buildFuture(JsonResult.successfu(
                appService.page(page, new QueryWrapper<SystemAuthoritysApp>().lambda().
                        eq(StringUtils.isNotBlank(appTag), SystemAuthoritysApp::getRemark, appTag).
                        like(StringUtils.isNotBlank(remark), SystemAuthoritysApp::getRemark, remark).
                        like(StringUtils.isNotBlank(appName), SystemAuthoritysApp::getAppName, appName))));
    }

    @Override
    public CompletableFuture<JsonResult> authorityAppList() {
        return ServiceUtil.buildFuture(JsonResult.successfu(
                appService.list()));
    }
}
