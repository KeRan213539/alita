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
package top.klw8.alita.providers.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import top.klw8.alita.entitys.authority.AlitaAuthoritysApp;
import top.klw8.alita.entitys.authority.AlitaAuthoritysAppChannel;
import top.klw8.alita.service.api.authority.IAuthorityAppProvider;
import top.klw8.alita.service.authority.IAuthorityAppChannelService;
import top.klw8.alita.service.authority.IAuthorityAppService;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.starter.service.common.ServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * 
 * 2020-07-16
 */
@Slf4j
@DubboService(async = true)
public class AuthorityAppProviderImpl implements IAuthorityAppProvider {

    @Autowired
    private IAuthorityAppService appService;

    @Autowired
    private IAuthorityAppChannelService appChannelService;


    @Override
    public CompletableFuture<JsonResult> addAuthorityApp(AlitaAuthoritysApp authorityApp) {
        return ServiceUtil.buildFuture(appService.addAuthorityApp(authorityApp));
    }

    @Override
    public CompletableFuture<JsonResult> updateAuthorityApp(AlitaAuthoritysApp authorityApp) {
        return ServiceUtil.buildFuture(appService.updateAuthorityApp(authorityApp));
    }

    @Override
    public CompletableFuture<JsonResult> deleteAuthorityApp(String appTag) {
        return ServiceUtil.buildFuture(appService.deleteAuthorityApp(appTag));
    }

    @Override
    public CompletableFuture<JsonResult> authorityApp(String appTag) {
        AlitaAuthoritysApp authorityApp = new AlitaAuthoritysApp();
        authorityApp.setAppTag(appTag);
        return ServiceUtil.buildFuture(appService.findAuthorityApp(authorityApp));
    }

    @Override
    public CompletableFuture<JsonResult> authorityAppPage(String appTag, String appName, String remark, Page<AlitaAuthoritysApp> page) {
        List<OrderItem> orders = new ArrayList<>(1);
        orders.add(OrderItem.desc("app_tag"));
        page.setOrders(orders);
        return ServiceUtil.buildFuture(JsonResult.successfu(
                appService.page(page, new QueryWrapper<AlitaAuthoritysApp>().lambda().
                        eq(StringUtils.isNotBlank(appTag), AlitaAuthoritysApp::getRemark, appTag).
                        like(StringUtils.isNotBlank(remark), AlitaAuthoritysApp::getRemark, remark).
                        like(StringUtils.isNotBlank(appName), AlitaAuthoritysApp::getAppName, appName))));
    }

    @Override
    public CompletableFuture<JsonResult> authorityAppList() {
        return ServiceUtil.buildFuture(JsonResult.successfu(
                appService.list()));
    }

    @Override
    public CompletableFuture<JsonResult> addAuthorityAppChannel(AlitaAuthoritysAppChannel authorityAppChannel) {
        return ServiceUtil.buildFuture(() -> {
            AlitaAuthoritysApp app = appService.getById(authorityAppChannel.getAppTag());
            if (Objects.isNull(app)) {
                return JsonResult.failed(String.format("app_tag 【%S】 不存在", authorityAppChannel.getAppTag()));
            }
            AlitaAuthoritysAppChannel channel = appChannelService.getById(authorityAppChannel.getChannelTag());
            if (!Objects.isNull(channel)) {
                return JsonResult.failed(String.format("当前 channel_tag 【%S】 已存在", authorityAppChannel.getChannelTag()));
            }
            if (appChannelService.save(authorityAppChannel)) {
                return JsonResult.successfu();
            }
            return JsonResult.failed(String.format("channel_tag 【%S】 保存失败", authorityAppChannel.getChannelTag()));
        });
    }

    @Override
    public CompletableFuture<JsonResult> updateAuthorityAppChannel(AlitaAuthoritysAppChannel authorityAppChannel) {
        return ServiceUtil.buildFuture(() -> {
            AlitaAuthoritysApp app = appService.getById(authorityAppChannel.getAppTag());
            if (Objects.isNull(app)) {
                return JsonResult.failed(String.format("app_tag 【%S】 不存在", authorityAppChannel.getAppTag()));
            }
            AlitaAuthoritysAppChannel channel = appChannelService.getById(authorityAppChannel.getChannelTag());
            if (Objects.isNull(channel)) {
                return JsonResult.failed(String.format("要修改的 channel_tag 【%S】 不存在", authorityAppChannel.getChannelTag()));
            }
            if (appChannelService.updateById(authorityAppChannel)) {
                return JsonResult.successfu();
            }
            return JsonResult.failed(String.format("channel_tag 【%S】 修改失败", authorityAppChannel.getChannelTag()));
        });
    }

    @Override
    public CompletableFuture<JsonResult> deleteAuthorityAppChannel(String channelTag) {
        return ServiceUtil.buildFuture(() -> {
            appChannelService.removeById(channelTag);
            return JsonResult.successfu();
        });
    }

    @Override
    public CompletableFuture<JsonResult> authorityAppChannelInfo(String channelTag) {
        return ServiceUtil.buildFuture(() -> {
            if (StringUtils.isNotBlank(channelTag)) {
                return JsonResult.successfu(appChannelService.getOne(new QueryWrapper<AlitaAuthoritysAppChannel>().lambda().
                        eq(true, AlitaAuthoritysAppChannel::getChannelTag, channelTag)));
            }
            return JsonResult.failed("channelTag 不能为空");
        });
    }

    @Override
    public CompletableFuture<JsonResult> authorityAppChannelPage(String appTag, String channelTag, String remark, Page<AlitaAuthoritysAppChannel> page) {
        List<OrderItem> orders = new ArrayList<>(1);
        orders.add(OrderItem.desc("app_tag"));
        page.setOrders(orders);
        return ServiceUtil.buildFuture(JsonResult.successfu(
                appChannelService.page(page, new QueryWrapper<AlitaAuthoritysAppChannel>().lambda().
                        eq(StringUtils.isNotBlank(appTag), AlitaAuthoritysAppChannel::getAppTag, appTag).
                        eq(StringUtils.isNotBlank(channelTag), AlitaAuthoritysAppChannel::getChannelTag, channelTag).
                        like(StringUtils.isNotBlank(remark), AlitaAuthoritysAppChannel::getRemark, remark))));
    }

    @Override
    public CompletableFuture<JsonResult> authorityAppChannelList(String appTag) {
        return ServiceUtil.buildFuture(JsonResult.successfu(
                appChannelService.list(new QueryWrapper<AlitaAuthoritysAppChannel>().lambda().
                        eq(StringUtils.isNotBlank(appTag), AlitaAuthoritysAppChannel::getAppTag, appTag))));
    }
}
