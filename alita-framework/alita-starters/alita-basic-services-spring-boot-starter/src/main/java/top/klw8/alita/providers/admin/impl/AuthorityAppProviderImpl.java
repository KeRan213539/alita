package top.klw8.alita.providers.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
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
@Service(async = true)
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
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(
                appService.page(page, new QueryWrapper<SystemAuthoritysApp>().lambda().
                        eq(StringUtils.isNotBlank(appTag), SystemAuthoritysApp::getRemark, appTag).
                        like(StringUtils.isNotBlank(remark), SystemAuthoritysApp::getRemark, remark).
                        like(StringUtils.isNotBlank(appName), SystemAuthoritysApp::getAppName, appName))));
    }

    @Override
    public CompletableFuture<JsonResult> authorityAppList() {
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(
                appService.list()));
    }
}
