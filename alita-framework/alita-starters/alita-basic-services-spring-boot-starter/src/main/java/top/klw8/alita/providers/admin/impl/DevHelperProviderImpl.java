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

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import top.klw8.alita.entitys.authority.*;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.authority.IDevHelperProvider;
import top.klw8.alita.service.authority.*;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.utils.EntityUtil;
import top.klw8.alita.starter.service.common.ServiceContext;
import top.klw8.alita.starter.service.common.ServiceUtil;
import top.klw8.alita.utils.UUIDUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 开发辅助工具
 * 2019/8/15 8:44
 */
@Slf4j
@Profile("dev")
@DubboService(async = true, timeout=10000)
public class DevHelperProviderImpl implements IDevHelperProvider {

    private static final String ADMIN_USER_ID = "d84c6b4ed9134d468e5a43d467036c46";

    private static final String ADMIN_ROLE_ID = "d84c6b4ed9134d468e5a43d467036c47";


    @Autowired
    private ISystemAuthoritysService auService;

    @Autowired
    private ISystemAuthoritysCatlogService catlogService;

    @Autowired
    private ISystemRoleService roleService;

    @Autowired
    private IAlitaUserService userService;

    @Autowired
    private ISystemAuthoritysResourceService dsService;

    @Autowired
    private IAuthorityAppService appService;

    @Override
    public CompletableFuture<JsonResult> batchAddAuthoritysAndCatlogs(List<AlitaAuthoritysCatlog> catlogList,
                                                                      List<AlitaAuthoritysResource> publicAuthoritysResourceList,
                                                                      boolean isAdd2SuperAdmin, AlitaAuthoritysApp app) {
        return CompletableFuture.supplyAsync(() -> {

            if(StringUtils.isBlank(app.getAppTag())){
                return JsonResult.failed("注册失败: appTag 未配制");
            }
            //检查app是否存在
            AlitaAuthoritysApp appFinded = appService.getById(app.getAppTag());
            if(null == appFinded){
                //app不存在,检查name是否有值,有就新增app
                if(StringUtils.isBlank(app.getAppName())){
                    return JsonResult.failed("注册失败: 配制的APP不存在,需要创建,但是缺少app名称");
                }
                AlitaAuthoritysApp app4Save = new AlitaAuthoritysApp();
                app4Save.setAppTag(app.getAppTag());
                app4Save.setAppName(app.getAppName());
                if(StringUtils.isNotBlank(app.getRemark())){
                    app4Save.setRemark(app.getRemark());
                }
                appService.save(app4Save);
            }

            boolean processFlag = false;
            if (CollectionUtils.isNotEmpty(catlogList)) {
                for (AlitaAuthoritysCatlog catlog : catlogList) {
                    if (StringUtils.isBlank(catlog.getCatlogName())) {
                        continue;
                    }
                    AlitaAuthoritysCatlog catlogFinded = catlogService.findByCatlogNameAndAppTag(catlog.getCatlogName(), app.getAppTag());
                    String catlogId;
                    if (EntityUtil.isEntityNoId(catlogFinded)) {
                        catlogId = UUIDUtil.getRandomUUIDString();
                        catlog.setId(catlogId);
                        catlog.setAppTag(app.getAppTag());
                        catlogService.save(catlog);
                    } else {
                        catlogId = catlogFinded.getId();
                    }
                    if (CollectionUtils.isNotEmpty(catlog.getAuthorityList())) {
                        for (AlitaAuthoritysMenu au : catlog.getAuthorityList()) {
                            AlitaAuthoritysMenu auFinded = auService.findByAuActionAndAppTag(au.getAuthorityAction(), app.getAppTag());
                            if (EntityUtil.isEntityNoId(auFinded)) {
                                au.setId(UUIDUtil.getRandomUUIDString());
                                au.setCatlogId(catlogId);
                                au.setAppTag(app.getAppTag());
                                auService.save(au);
                                auFinded = au;
                            }
                            if (isAdd2SuperAdmin) {
                                // 添加到超级管理员角色和用户中
                                checkAdminUserRoleAndAddIfNotExist(app);
                                roleService.addAuthority2Role(ADMIN_ROLE_ID, auFinded);
                            }

                            // 处理资源权限
                            List<AlitaAuthoritysResource> authoritysResourceList = au.getAuthoritysResourceList();
                            if(CollectionUtils.isNotEmpty(authoritysResourceList)){
                                for(AlitaAuthoritysResource ds : authoritysResourceList){
                                    AlitaAuthoritysResource dsFinded = dsService.findByResourceAndAuId(ds.getResource(), auFinded.getId());
                                    if (EntityUtil.isEntityNoId(dsFinded)) {
                                        ds.setId(UUIDUtil.getRandomUUIDString());
                                        ds.setAuthoritysId(auFinded.getId());
                                        ds.setAppTag(app.getAppTag());
                                        dsService.save(ds);
                                        dsFinded = ds;
                                    }
                                    if (isAdd2SuperAdmin) {
                                        // 添加到超级管理员角色和用户中
                                        checkAdminUserRoleAndAddIfNotExist(app);
                                        roleService.addAuthoritysResource2Role(ADMIN_ROLE_ID, dsFinded);
                                    }
                                }
                            }

                        }
                    }
                }
                processFlag = true;
            }
            if (CollectionUtils.isNotEmpty(publicAuthoritysResourceList)) {
                for(AlitaAuthoritysResource ds : publicAuthoritysResourceList){
                    AlitaAuthoritysResource dsFinded = dsService.findByResourceAndAuId(ds.getResource(), null);
                    if (EntityUtil.isEntityNoId(dsFinded)) {
                        ds.setId(UUIDUtil.getRandomUUIDString());
                        ds.setAppTag(app.getAppTag());
                        dsService.save(ds);
                        dsFinded = ds;
                    }
                    if (isAdd2SuperAdmin) {
                        // 添加到超级管理员角色和用户中
                        checkAdminUserRoleAndAddIfNotExist(app);
                        roleService.addAuthoritysResource2Role(ADMIN_ROLE_ID, dsFinded);
                    }
                }
                processFlag = true;
            }
            if(processFlag){
                return JsonResult.successfu("注册完成");
            }
            return JsonResult.successfu("扫描完成,没有任何待注册数据", null);
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addAllAuthoritys2AdminRole(AlitaAuthoritysApp app) {

        // 检查超级管理员角色和用户是否存在,不存在则添加
        checkAdminUserRoleAndAddIfNotExist(app);

        // 查询全部权限,并替换管理员角色中的权限
        List<AlitaAuthoritysMenu> authoritysList = auService.list();
        roleService.replaceAuthority2Role(ADMIN_ROLE_ID, authoritysList.stream().map(AlitaAuthoritysMenu::getId).collect(Collectors.toList()));
        return ServiceUtil.buildFuture(JsonResult.successfu("OK"));
    }

    private void checkAdminUserRoleAndAddIfNotExist(AlitaAuthoritysApp app){
        boolean isNeedAddRole2User = false;
        if (null == appService.getById(app.getAppTag())) {
            appService.save(buildAdminApp(app));
        }
        if (EntityUtil.isEntityNoId(userService.getById(ADMIN_USER_ID))) {
            isNeedAddRole2User = true;
            userService.save(buildAdminUser());
        }
        if (EntityUtil.isEntityNoId(roleService.getById(ADMIN_ROLE_ID))) {
            isNeedAddRole2User = true;
            roleService.save(buildAdminRole(app));
        }
        if (isNeedAddRole2User) {
            userService.addRole2User(ADMIN_USER_ID, ADMIN_ROLE_ID);
        }
    }

    private AlitaUserAccount buildAdminUser(){
        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
        AlitaUserAccount superAdmin = new AlitaUserAccount("admin", "15808888888", pwdEncoder.encode("123456"));
        superAdmin.setId(ADMIN_USER_ID);
        superAdmin.setCreateDate(LocalDateTime.now());
        return superAdmin;
    }

    private AlitaRole buildAdminRole(AlitaAuthoritysApp app){
        AlitaRole superAdminRole = new AlitaRole();
        superAdminRole.setId(ADMIN_ROLE_ID);
        superAdminRole.setRoleName("超级管理员");
        superAdminRole.setRemark("超级管理员");
        superAdminRole.setAppTag(app.getAppTag());
        return superAdminRole;
    }

    private AlitaAuthoritysApp buildAdminApp(AlitaAuthoritysApp app){
        AlitaAuthoritysApp superAdminApp = new AlitaAuthoritysApp();
        superAdminApp.setAppTag(app.getAppTag());
        superAdminApp.setAppName("超级管理应用");
        superAdminApp.setRemark("超级管理应用");
        return superAdminApp;
    }

}
