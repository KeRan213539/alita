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
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import top.klw8.alita.entitys.authority.*;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.helper.UserCacheHelper;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;
import top.klw8.alita.service.authority.*;
import top.klw8.alita.service.pojos.SystemAuthorityPojo;
import top.klw8.alita.service.pojos.SystemAuthorityCatlogPojo;
import top.klw8.alita.service.pojos.SystemAuthoritysResourcePojo;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.result.code.AuthorityResultCodeEnum;
import top.klw8.alita.service.utils.EntityUtil;
import top.klw8.alita.starter.service.common.ServiceContext;
import top.klw8.alita.starter.service.common.ServiceUtil;
import top.klw8.alita.utils.AuthorityUtil;
import top.klw8.alita.utils.BeanUtil;
import top.klw8.alita.utils.UUIDUtil;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 权限相关dubbo服务提供者
 * 2019/8/14 9:33
 */
@Slf4j
@DubboService(async = true)
public class AuthorityAdminProviderImpl implements IAuthorityAdminProvider {

    /**
     * 全局资源权限用的catlog id
     */
    private static final String ID_PUBLIC_AUTHORITYS_RESOURCE_CATLOG = "PUBLIC_AUTHORITYS_RESOURCE_CATLOG";

    /**
     * 全局资源权限用的权限ID
     */
    private static final String ID_PUBLIC_RESOURCE_AUTHORITY = "PUBLIC_RESOURCE_AUTHORITY";

    @Autowired
    private ISystemAuthoritysService auService;

    @Autowired
    private ISystemAuthoritysResourceService dsService;

    @Autowired
    private ISystemAuthoritysCatlogService catlogService;

    @Autowired
    private ISystemRoleService roleService;

    @Autowired
    private IAlitaUserService userService;

    @Autowired
    private IAuthorityAppService appService;

    @Autowired
    private UserCacheHelper userCacheHelper;

    @Override
    public CompletableFuture<JsonResult> addAuthority(AlitaAuthoritysMenu au) {
        return CompletableFuture.supplyAsync(() -> {
            AlitaAuthoritysCatlog catlog = catlogService.getById(au.getCatlogId());
            if (catlog == null) {
                return JsonResult.failed(AuthorityResultCodeEnum.CATLOG_NOT_EXIST, "权限目录不存在【" + au.getCatlogId() + "】");
            }
            if(StringUtils.isBlank(au.getId())) {
                au.setId(UUIDUtil.getRandomUUIDString());
            }
            boolean isSaved = auService.save(au);
            if (isSaved) {
                return JsonResult.successfu();
            }
            return JsonResult.failed("保存失败");
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addCatlog(AlitaAuthoritysCatlog catlog) {
        return CompletableFuture.supplyAsync(() -> {
            if (catlogService.save(catlog)) {
                return JsonResult.successfu();
            } else {
                return JsonResult.failed("保存失败");
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addSysRole(AlitaRole role) {
        return CompletableFuture.supplyAsync(() -> {
            if (roleService.save(role)) {
                return JsonResult.successfu();
            } else {
                return JsonResult.failed("保存失败");
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addRoleAu(String roleId, String auId) {
        return CompletableFuture.supplyAsync(() -> {
            AlitaRole role = roleService.getById(roleId);

            if (EntityUtil.isEntityNoId(role)) {
                return JsonResult.failed(AuthorityResultCodeEnum.ROLE_NOT_EXIST);
            }
            AlitaAuthoritysMenu au = auService.getById(auId);
            if (EntityUtil.isEntityNoId(au)) {
                return JsonResult.failed(AuthorityResultCodeEnum.AUTHORITY_NOT_EXIST);
            }
            int saveResult = roleService.addAuthority2Role(role.getId(), au);
            if (saveResult > 0) {
                return JsonResult.successfu();
            } else {
                return JsonResult.failed("添加失败");
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addUserRole(String userId, String roleId) {
        return CompletableFuture.supplyAsync(() -> {
            AlitaUserAccount user = userService.getById(userId);
            if (EntityUtil.isEntityNoId(user)) {
                return JsonResult.failed(AuthorityResultCodeEnum.USER_NOT_EXIST);
            }
            AlitaRole role = roleService.getById(roleId);
            if (EntityUtil.isEntityNoId(role)) {
                return JsonResult.failed(AuthorityResultCodeEnum.ROLE_NOT_EXIST);
            }
            int saveResult = userService.addRole2User(user.getId(), role.getId());
            if (saveResult > 0) {
                return JsonResult.successfu();
            } else {
                return JsonResult.failed("添加失败");
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> refreshUserAuthoritys(String userId) {
        //查询用户,把权限查出来
        return CompletableFuture.supplyAsync(() -> {
            // 根据用户ID查询到用户信息
            AlitaUserAccount sysUser = userService.getById(userId);
            // 判断是否查询到用户
            if (EntityUtil.isEntityNoId(sysUser)) {
                return JsonResult.failed(AuthorityResultCodeEnum.USER_NOT_EXIST);
            } else {
                // 根据用户ID查询用户角色
                List<AlitaRole> userRoles = userService.getUserAllRoles(userId, null);

                for (AlitaRole role : userRoles) {
                    // 根据用户角色查询角色对应的权限并更新到SystemRole实体中
                    List<AlitaAuthoritysMenu> authoritys = roleService.getRoleAllAuthoritys(role.getId());
                    role.setAuthorityList(authoritys);

                    // 根据用户角色查询角色对应的资源权限并更新到SystemRole实体中
                    List<AlitaAuthoritysResource> authoritysResource = roleService.getRoleAllAuthoritysResource(role.getId());
                    role.setAuthoritysResourceList(authoritysResource);
                }
                // 更新用户角色权限到用户实体中
                if (null != userRoles && !userRoles.isEmpty()) {
                    sysUser.setUserRoles(userRoles);
                }
                userCacheHelper.putUserInfo2Cache(sysUser);
                return JsonResult.successfu();
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> roleList(String roleName, String appTag, Page<AlitaRole> page) {
        QueryWrapper<AlitaRole> query = new QueryWrapper();
        if(StringUtils.isNotBlank(roleName)){
            query.like("role_name", roleName);
        }
        if(StringUtils.isNotBlank(appTag)){
            query.eq("app_tag", appTag);
        }
        List<OrderItem> orders = new ArrayList<>(1);
        orders.add(OrderItem.asc("role_name"));
        page.setOrders(orders);
        return ServiceUtil.buildFuture(JsonResult.successfu(
                roleService.page(page, query)));
    }

    @Override
    public CompletableFuture<JsonResult> roleAll(String appTag) {
        QueryWrapper<AlitaRole> query = new QueryWrapper();
        if(StringUtils.isBlank(appTag)){
            query.orderByAsc("app_tag", "role_name");
        } else {
            query.eq("app_tag", appTag);
            query.orderByAsc("role_name");
        }
        return ServiceUtil.buildFuture(JsonResult.successfu(roleService.list(query)));
    }

    @Override
    public CompletableFuture<JsonResult> markRoleAuthoritys(String roleId, String appTag) {
        List<AlitaAuthoritysMenu> roleAuList = new LinkedList<>();
        List<AlitaAuthoritysResource> roleDsList = new LinkedList<>();
        if (StringUtils.isNotBlank(roleId)) {
            AlitaRole role = roleService.getById(roleId);
            Assert.notNull(role, "该角色不存在!");
            //该角色拥有的权限
            roleAuList.addAll(roleService.getRoleAllAuthoritys(role.getId()));
            //该角色拥有的资源权限
            roleDsList.addAll(roleService.getRoleAllAuthoritysResource(role.getId()));
        }

        // 构造查询条件
        QueryWrapper<AlitaAuthoritysMenu> auQuery = new QueryWrapper();
        QueryWrapper<AlitaAuthoritysResource> dsQuery = new QueryWrapper();
        auQuery.orderByAsc("show_index");
        dsQuery.orderByAsc("authoritys_id");
        if(StringUtils.isNotBlank(appTag)){
            auQuery.eq("app_tag", appTag);
            dsQuery.eq("app_tag", appTag);
        }

        // 查询权限
        List<AlitaAuthoritysMenu> allAuList = auService.list(auQuery);
        // 查询资源权限
        List<AlitaAuthoritysResource> allDsList = dsService.list(dsQuery);

        // 根据权限分组的非全局资源权限Map<权限ID: List<SystemAuthoritysResourcePojo>
        Map<String, List<SystemAuthoritysResourcePojo>> dsListGroupByAu = new HashMap<>(16);
        // 全局资源权限List
        List<SystemAuthoritysResourcePojo> publicDsList = new ArrayList<>(16);
        // 资源权限Map<资源权限ID: SystemAuthoritysResourcePojo>
        Map<String, SystemAuthoritysResourcePojo> allDsMap = new HashMap<>(16);

        if(CollectionUtils.isNotEmpty(allDsList)){
            for(AlitaAuthoritysResource ds : allDsList){
                SystemAuthoritysResourcePojo dsPojo = new SystemAuthoritysResourcePojo();
                BeanUtils.copyProperties(ds, dsPojo);
                // 资源权限加个特殊标记
                dsPojo.setId(ISystemRoleService.DS_ID_PREFIX + dsPojo.getId());
                if(StringUtils.isBlank(dsPojo.getAuthoritysId())){
                    // 全局资源权限
                    publicDsList.add(dsPojo);
                } else {
                    // 非全局资源权限
                    List<SystemAuthoritysResourcePojo> auDsList = dsListGroupByAu.get(dsPojo.getAuthoritysId());
                    if(auDsList == null){
                        auDsList = new ArrayList<>(0);
                        dsListGroupByAu.put(dsPojo.getAuthoritysId(), auDsList);
                    }
                    auDsList.add(dsPojo);
                }
                allDsMap.put(dsPojo.getId(), dsPojo);
            }
        }

        // 处理角色拥有的资源权限
        if(CollectionUtils.isNotEmpty(roleDsList)) {
            Iterator<AlitaAuthoritysResource> roleDsIt = roleDsList.iterator();
            while (roleDsIt.hasNext()) {
                AlitaAuthoritysResource roleDs = roleDsIt.next();
                SystemAuthoritysResourcePojo dsPojo = allDsMap.get(roleDs.getId());
                if(null != dsPojo){
                    dsPojo.setCurrUserHas(true);
                }
            }
        }


        // 把全局资源权限构造一个catlog和一个Au放进去
        SystemAuthorityCatlogPojo publicDsCatlog = new SystemAuthorityCatlogPojo();
        publicDsCatlog.setId(ID_PUBLIC_AUTHORITYS_RESOURCE_CATLOG);
        publicDsCatlog.setCatlogName("全局资源权限");
        publicDsCatlog.setShowIndex(0);
        SystemAuthorityPojo publicDsAuPojo = new SystemAuthorityPojo();
        publicDsAuPojo.setId(ID_PUBLIC_RESOURCE_AUTHORITY);
        publicDsAuPojo.setAuthorityName("全局资源权限");
        publicDsAuPojo.setDsList(publicDsList);
        publicDsCatlog.setAuthorityList(Lists.newArrayList(publicDsAuPojo));

        Map<String, SystemAuthorityCatlogPojo> catlogMap = new HashMap<>(16);
        catlogMap.put(ID_PUBLIC_AUTHORITYS_RESOURCE_CATLOG, publicDsCatlog);


        // 临时存放非 menuItem
        Map<String, SystemAuthorityPojo> auNotMenuItemMap = new HashMap<>(50);
        // 临时存放 menuItem
        Map<String, SystemAuthorityPojo> auMenuItemMap = new HashMap<>(50);
        // 遍历权限和权限下的资源权限转为视图bean,并标记传入的角色是否拥有该权限
        for(AlitaAuthoritysMenu sysAu : allAuList){
            SystemAuthorityCatlogPojo catlogPojo = catlogMap.get(sysAu.getCatlogId());
            if (catlogPojo == null) {
                AlitaAuthoritysCatlog catlog = catlogService.getById(sysAu.getCatlogId());
                catlogPojo = new SystemAuthorityCatlogPojo();
                BeanUtils.copyProperties(catlog, catlogPojo);
                catlogPojo.setAuthorityList(new ArrayList<>(16));
                catlogMap.put(catlogPojo.getId(), catlogPojo);
            }
            SystemAuthorityPojo auPojo = new SystemAuthorityPojo();
            BeanUtils.copyProperties(sysAu, auPojo);
            // 查找当前角色是否有该权限
            if(CollectionUtils.isNotEmpty(roleAuList)) {
                Iterator<AlitaAuthoritysMenu> roleAuIt = roleAuList.iterator();
                while (roleAuIt.hasNext()) {
                    AlitaAuthoritysMenu roleAu = roleAuIt.next();
                    if (roleAu.getId().equals(auPojo.getId())) {
                        auPojo.setCurrUserHas(true);
                    }
                }
            }
            // 资源权限放入权限
            auPojo.setDsList(dsListGroupByAu.get(auPojo.getId()));
            if(!AuthorityTypeEnum.MENU.equals(auPojo.getAuthorityType()) && StringUtils.isNotBlank(auPojo.getMenuId())){
                auMenuItemMap.put(auPojo.getId(), auPojo);
            } else {
                catlogPojo.getAuthorityList().add(auPojo);
                auNotMenuItemMap.put(auPojo.getId(), auPojo);
            }
        }

        // 遍历 menuItem, 并把它放到对应的 menu下
        auMenuItemMap.forEach((key, value) -> {
            SystemAuthorityPojo auMenu = auNotMenuItemMap.get(value.getMenuId());
            if(null == auMenu.getMenuList()){
                auMenu.setMenuList(new ArrayList<>(16));
            }
            auMenu.getMenuList().add(value);
        });

        List<SystemAuthorityCatlogPojo> catlogPojoList = new ArrayList<>(catlogMap.values());
        Collections.sort(catlogPojoList);
        catlogPojoList.stream().forEach(systemAuthorityCatlogPojo -> Collections.sort(systemAuthorityCatlogPojo.getAuthorityList()));
        return ServiceUtil.buildFuture(JsonResult.successfu(catlogPojoList));
    }

    @Override
    public CompletableFuture<JsonResult> saveRoleAuthoritys(String roleId, List<String> auIds, String appTag) {
        AlitaRole role = roleService.getById(roleId);
        if(role == null){
            return ServiceUtil.buildFuture(JsonResult.badParameter("角色不存在"));
        }
        if(StringUtils.isNotBlank(appTag) && !appTag.equals(role.getAppTag())){
            return ServiceUtil.buildFuture(JsonResult.badParameter("传入的appTag与角色的不匹配"));
        }
        for(String auId : auIds){
            IAssociatedApp finded;
            if(auId.startsWith(ISystemRoleService.DS_ID_PREFIX)){
                finded = dsService.getById(auId.replace(ISystemRoleService.DS_ID_PREFIX, ""));
            } else {
                finded = auService.getById(auId);
            }
            if(null == finded){
                return ServiceUtil.buildFuture(JsonResult.badParameter("权限或者资源权限不存在"));
            }
            if(!role.getAppTag().equals(finded.getAppTag())){
                return ServiceUtil.buildFuture(JsonResult.badParameter("权限或者资源权限与角色所属app不一致!"));
            }
        }
        return ServiceUtil.buildFuture(JsonResult.successfu(roleService.replaceAuthority2Role(roleId, auIds)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<JsonResult> saveRole(AlitaRole role, String copyAuFromRoleId) {
        Assert.notNull(role, "要保存的角色不能为 null !!!");

        AlitaAuthoritysApp app = appService.getById(role.getAppTag());
        if(null == app){
            return ServiceUtil.buildFuture(JsonResult.failed("appTag 对应的应用不存在"));
        }

        if(StringUtils.isNotBlank(role.getId())){
            roleService.updateById(role);
        } else {
            if(CollectionUtils.isNotEmpty(role.getAuthorityList())){
                role.setId(UUIDUtil.getRandomUUIDString());
            }
            roleService.save(role);
        }
        boolean isCopy = false;
        if(StringUtils.isNotBlank(copyAuFromRoleId)){
            // 根据 copyAuFromRoleId 查找该角色的权限并设置到要保存的角色中
            role.setAuthorityList(roleService.getRoleAllAuthoritys(copyAuFromRoleId));
            role.setAuthoritysResourceList(roleService.getRoleAllAuthoritysResource(copyAuFromRoleId));
            isCopy = true;
        }
        if(CollectionUtils.isNotEmpty(role.getAuthorityList())){
            // 保存角色中的权限
            int dsSize = CollectionUtils.isEmpty(role.getAuthoritysResourceList()) ? 0 : role.getAuthoritysResourceList().size();
            List<String> auIdList = new ArrayList<>(role.getAuthorityList().size() + dsSize);
            for(AlitaAuthoritysMenu au : role.getAuthorityList()){
                if(!isCopy && auService.getById(au.getId()) == null){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ServiceUtil.buildFuture(JsonResult.badParameter("权限不存在"));
                }
                auIdList.add(au.getId());
            }
            if(dsSize > 0){
                for(AlitaAuthoritysResource ds : role.getAuthoritysResourceList()){
                    auIdList.add(ISystemRoleService.DS_ID_PREFIX + ds.getId());
                }
            }
            roleService.replaceAuthority2Role(role.getId(), auIdList);
        }
        return ServiceUtil.buildFuture(JsonResult.successfu());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<JsonResult> delRole(String roleId) {
        Assert.hasText(roleId, "角色ID不能为空!");
        List<AlitaUserAccount> userList = userService.getUserByRoleId(roleId);
        if(CollectionUtils.isNotEmpty(userList)){
            return ServiceUtil.buildFuture(JsonResult.badParameter("有用户拥有该角色,不允许删除"));
        }
        roleService.cleanAuthoritysFromRole(roleId);
        roleService.removeById(roleId);
        return ServiceUtil.buildFuture(JsonResult.successfu());
    }

    @Override
    public CompletableFuture<JsonResult> roleInfo(String roleId){
        Assert.hasText(roleId, "角色ID不能为空!");
        AlitaRole role = roleService.getById(roleId);


        List<AlitaAuthoritysMenu> roleAuList = roleService.selectSystemAuthoritysWithCatlogByRoleId(role.getId());
        Map<String, Object> tempResult = new HashMap<>(BeanUtil.beanToMap(role));
        tempResult.entrySet().removeIf(entry -> entry.getValue() == null);
        Map<String, Map<String, Object>> catlogListMap = new HashMap<>(16);
        Map<String, AlitaAuthoritysMenu> auListMap = new HashMap<>(16);
        for(AlitaAuthoritysMenu auInRole : roleAuList) {
            Map<String, Object> catlogMap = catlogListMap.get(auInRole.getCatlogId());
            if (catlogMap == null) {
                catlogMap = new HashMap<>(3);
                catlogListMap.put(auInRole.getCatlogId(), catlogMap);
                catlogMap.put("id", auInRole.getCatlogId());
                catlogMap.put("catlogName", auInRole.getCatlogName());
                catlogMap.put("auList", new ArrayList<AlitaAuthoritysMenu>(16));
            }
            ((List<AlitaAuthoritysMenu>) catlogMap.get("auList")).add(auInRole);
            auListMap.put(auInRole.getId(), auInRole);
        }
        tempResult.put("catlogList", catlogListMap.values().stream().collect(Collectors.toList()));

        List<AlitaAuthoritysResource> roleDsList = roleService.getRoleAllAuthoritysResource(role.getId());
        for(AlitaAuthoritysResource dsInRole : roleDsList) {
            AlitaAuthoritysMenu au = auListMap.get(dsInRole.getAuthoritysId());
            if(au != null){
                List<AlitaAuthoritysResource> dsInAuList = au.getAuthoritysResourceList();
                if(dsInAuList == null){
                    dsInAuList = new ArrayList<>(16);
                    au.setAuthoritysResourceList(dsInAuList);
                }
                dsInAuList.add(dsInRole);
            }
        }

        return ServiceUtil.buildFuture(JsonResult.successfu(tempResult));
    }

    @Override
    public CompletableFuture<JsonResult> catlogList(String catlogName, String appTag, Page<AlitaAuthoritysCatlog> page) {
        QueryWrapper<AlitaAuthoritysCatlog> query = new QueryWrapper();
        if(StringUtils.isNotBlank(catlogName)){
            query.like("catlog_name", catlogName);
        }
        if(StringUtils.isNotBlank(appTag)){
            query.eq("app_tag", appTag);
        }
        List<OrderItem> orders = new ArrayList<>(1);
        orders.add(OrderItem.asc("show_index"));
        page.setOrders(orders);
        return ServiceUtil.buildFuture(JsonResult.successfu(
                catlogService.page(page, query)));
    }

    @Override
    public CompletableFuture<JsonResult> catlogAll(String appTag) {
        QueryWrapper<AlitaAuthoritysCatlog> query = new QueryWrapper();
        if(StringUtils.isNotBlank(appTag)){
            query.eq("app_tag", appTag);
        }
        query.orderByAsc("show_index");
        return ServiceUtil.buildFuture(JsonResult.successfu(
                catlogService.list(query)));
    }

    @Override
    public CompletableFuture<JsonResult> saveCatlog(AlitaAuthoritysCatlog catlog) {
        Assert.notNull(catlog, "要保存的权限目录不能为 null !!!");
        AlitaAuthoritysApp app = appService.getById(catlog.getAppTag());
        if(null == app){
            return ServiceUtil.buildFuture(JsonResult.failed("appTag 对应的应用不存在"));
        }
        if(StringUtils.isNotBlank(catlog.getId())){
            catlogService.updateById(catlog);
        } else {
            catlogService.save(catlog);
        }
        return ServiceUtil.buildFuture(JsonResult.successfu());
    }

    @Override
    public CompletableFuture<JsonResult> delCatlog(String catlogId) {
        Assert.hasText(catlogId, "权限目录ID不能为空!");
        QueryWrapper<AlitaAuthoritysMenu> auQuery = new QueryWrapper();
        auQuery.eq("catlog_id", catlogId);
        int auCountByCatlogId = auService.count(auQuery);
        if(auCountByCatlogId > 0){
            return ServiceUtil.buildFuture(JsonResult.badParameter("该目录下有权限,不允许删除"));
        }
        catlogService.removeById(catlogId);
        return ServiceUtil.buildFuture(JsonResult.successfu());
    }

    @Override
    public CompletableFuture<JsonResult> catlogInfo(String catlogId){
        Assert.hasText(catlogId, "权限目录ID不能为空!");
        return ServiceUtil.buildFuture(JsonResult.successfu(catlogService.getById(catlogId)));
    }

    @Override
    public CompletableFuture<JsonResult> authoritysList(String auName, AuthorityTypeEnum auType,
                                                        Page<AlitaAuthoritysMenu> page, String authorityAction,
                                                        String catlogName, String appTag) {
        return ServiceUtil.buildFuture(JsonResult.successfu(
                auService.selectSystemAuthoritysList(page,auName, auType == null ? null : auType.name(),
                        authorityAction, catlogName, appTag)));
    }

    @Override
    public CompletableFuture<JsonResult> saveAuthority(AlitaAuthoritysMenu au, String httpMethod) {
        Assert.notNull(au, "要保存的权限不能为 null !!!");
        Assert.hasText(au.getCatlogId(), "所属权限目录ID不能为空!");

        AlitaAuthoritysCatlog catlog = catlogService.getById(au.getCatlogId());
        if(null == catlog){
            return ServiceUtil.buildFuture(JsonResult.failed("所属权限目录不存在 !!!"));
        }

        if(AuthorityTypeEnum.URL.equals(au.getAuthorityType())) {
            if(StringUtils.isBlank(httpMethod)) {
                return ServiceUtil.buildFuture(JsonResult.failed("httpMethod 不能为空"));
            }
            if(StringUtils.isNotBlank(au.getMenuId())) {
                AlitaAuthoritysMenu menuAu = auService.getById(au.getMenuId());
                if (EntityUtil.isEntityNoId(menuAu)) {
                    return ServiceUtil.buildFuture(JsonResult.failed("指定的权限所属MENU不存在"));
                }
                if (!AuthorityTypeEnum.MENU.equals(menuAu.getAuthorityType())) {
                    return ServiceUtil.buildFuture(JsonResult.failed("指定的权限所属MENU的类型不是MENU"));
                }
            }
            au.setAuthorityAction(AuthorityUtil.composeWithSeparator2(httpMethod, au.getAuthorityAction()));
        }

        if(AuthorityTypeEnum.MENU.equals(au.getAuthorityType())) {
            if(StringUtils.isNotBlank(au.getMenuId())) {
                return ServiceUtil.buildFuture(JsonResult.failed("MENU类型不能属于MENU"));
            }
        }

        au.setAppTag(catlog.getAppTag());

        if(StringUtils.isNotBlank(au.getId())){
            auService.updateById(au);
        } else {
            auService.save(au);
        }
        return ServiceUtil.buildFuture(JsonResult.successfu());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<JsonResult> delAuthority(String auId) {
        Assert.hasText(auId, "权限ID不能为空!");
        if(roleService.countByAuId(auId) > 0){
            return ServiceUtil.buildFuture(JsonResult.badParameter("该权限已被角色关联,禁止删除"));
        }
        if(dsService.countByAuId(auId) > 0){
            return ServiceUtil.buildFuture(JsonResult.badParameter("该权限下有资源权限,禁止删除"));
        }
        auService.removeById(auId);
        return ServiceUtil.buildFuture(JsonResult.successfu());
    }

    @Override
    public CompletableFuture<JsonResult> auInfo(String auId){
        Assert.hasText(auId, "权限ID不能为空!");
        return ServiceUtil.buildFuture(JsonResult.successfu(auService.getById(auId)));
    }

    @Override
    public CompletableFuture<JsonResult> authoritysResourceByAuthorityAction(String httpMethod, String auAction,
                                                                       String appTag, String userId) {
        Assert.hasText(auAction, "权限路径不能为空!");

        if(auAction.startsWith("http")){
            return ServiceUtil.buildFuture(JsonResult.badParameter("权限action格式不正确,请传入服务端返回的!"));
        }
        AlitaAuthoritysMenu au;
        if(StringUtils.isBlank(httpMethod)){
            au = auService.findByAuActionAndAppTag(auAction, appTag);
        } else {
            au = auService.findByAuActionAndAppTag(AuthorityUtil.composeWithSeparator2(httpMethod, auAction), appTag);
        }
        if(EntityUtil.isEntityNoId(au)){
            return ServiceUtil.buildFuture(JsonResult.badParameter("权限action格对应的权限不存在!"));
        }


        List<AlitaAuthoritysResource> resultList = new LinkedList<>();
        if(StringUtils.isNotBlank(userId)){
            List<AlitaRole> userRoles = userService.getUserAllRoles(userId, appTag);
            for(AlitaRole role : userRoles){
                List<AlitaAuthoritysResource> auDsList = dsService.findByRoleIdAndAuId(role.getId(), au.getId());
                resultList.addAll(auDsList);
                List<AlitaAuthoritysResource> publicDsList = dsService.findByRoleIdAndAuId(role.getId(), null);
                resultList.addAll(publicDsList);
            }
        } else {
            List<AlitaAuthoritysResource> auDsList = dsService.findByAuId(au.getId(), appTag);
            resultList.addAll(auDsList);
            List<AlitaAuthoritysResource> publicDsList = dsService.findByAuId(null, appTag);
            resultList.addAll(publicDsList);
        }
        return ServiceUtil.buildFuture(JsonResult.successfu(resultList));
    }

    public CompletableFuture<JsonResult> allAuthoritysWithCatlog(String appTag){
        List<AlitaAuthoritysMenu> allAuList = auService.selectAllSystemAuthoritysWithCatlog(appTag);
        Map<String, Map<String, Object>> tempResult = new HashMap<>(16);
        for(AlitaAuthoritysMenu au : allAuList){
            Map<String, Object> catlogMap = tempResult.get(au.getCatlogId());
            if(catlogMap == null){
                catlogMap = new HashMap<>(3);
                tempResult.put(au.getCatlogId(), catlogMap);
                catlogMap.put("id", au.getCatlogId());
                catlogMap.put("catlogName", au.getCatlogName());
                catlogMap.put("auList", new ArrayList<AlitaAuthoritysMenu>(16));
            }
            ((List<AlitaAuthoritysMenu>) catlogMap.get("auList")).add(au);
        }
        List<Map<String, Object>> result = new ArrayList<>(tempResult.size() + 1);

        // 把全局资源权限构造一个catlog和一个Au放进去
        Map<String, Object> publicCatlogMap = new HashMap<>(3);
        result.add(publicCatlogMap);
        publicCatlogMap.put("id", ID_PUBLIC_AUTHORITYS_RESOURCE_CATLOG);
        publicCatlogMap.put("catlogName", "全局资源权限");

        AlitaAuthoritysMenu publicDsAu = new AlitaAuthoritysMenu();
        publicDsAu.setId(ID_PUBLIC_RESOURCE_AUTHORITY);
        publicDsAu.setAuthorityName("全局资源权限");
        publicCatlogMap.put("auList", Lists.newArrayList(publicDsAu));

        tempResult.forEach((k, v) -> result.add(v));
        return ServiceUtil.buildFuture(JsonResult.successfu(result));
    }

    @Override
    public CompletableFuture<JsonResult> authoritysResourceList(String resource, String appTag, Page<AlitaAuthoritysResource> page) {
        QueryWrapper<AlitaAuthoritysResource> query = new QueryWrapper();
        if(StringUtils.isNotBlank(resource)){
            query.like("resource", resource);
        }
        if(StringUtils.isNotBlank(appTag)){
            query.eq("app_tag", appTag);
        }
        List<OrderItem> orders = new ArrayList<>(1);
        orders.add(OrderItem.desc("resource"));
        page.setOrders(orders);
        return ServiceUtil.buildFuture(JsonResult.successfu(
                dsService.page(page, query)));
    }

    @Override
    public CompletableFuture<JsonResult> saveAuthoritysResource(AlitaAuthoritysResource ds){
        Assert.notNull(ds, "要保存的资源权限不能为空!!!");

        // 如果所属权限ID是全局的ID,设为null
        if(null != ds.getAuthoritysId() && ID_PUBLIC_RESOURCE_AUTHORITY.equals(ds.getAuthoritysId())){
            ds.setAuthoritysId(null);
            // 全局资源权限,检查app是否存在
            Assert.notNull(ds.getAppTag(), "全局资源权限需要 appTag !");
            AlitaAuthoritysApp app = appService.getById(ds.getAppTag());
            if(null == app){
                return ServiceUtil.buildFuture(JsonResult.failed("appTag 对应的应用不存在"));
            }
        }
        // 如果所属权限ID不为空,检查权限是否存在
        if(StringUtils.isNotBlank(ds.getAuthoritysId())){
            AlitaAuthoritysMenu au = auService.getById(ds.getAuthoritysId());
            if(null == au){
                return ServiceUtil.buildFuture(JsonResult.failed(AuthorityResultCodeEnum.AUTHORITY_NOT_EXIST, "权限不存在【" + ds.getAuthoritysId() + "】"));
            }
            // 非全局资源权限, appTag 从权限中取
            ds.setAppTag(au.getAppTag());
        }

        if(StringUtils.isNotBlank(ds.getId())){
            dsService.updateById(ds);
        } else {
            // 检查该资源权限是否存在(所属权限ID+权限标识)
            if(EntityUtil.isEntityHasId(dsService.findByResourceAndAuId(ds.getResource(), ds.getAuthoritysId()))){
                return ServiceUtil.buildFuture(JsonResult.failed(AuthorityResultCodeEnum.SYSTEM_AUTHORITYS_RESOURCE_HAS_EXIST));
            }
            dsService.save(ds);
        }
        return ServiceUtil.buildFuture(JsonResult.successfu());
    }

    @Override
    public CompletableFuture<JsonResult> delAuthoritysResource(String dsId) {
        Assert.hasText(dsId, "资源权限ID不能为空!");
        if(roleService.countByDsId(dsId) > 0){
            return ServiceUtil.buildFuture(JsonResult.badParameter("该资源权限已与角色关联,不允许删除"));
        }
        dsService.removeById(dsId);
        return ServiceUtil.buildFuture(JsonResult.successfu());
    }

    @Override
    public CompletableFuture<JsonResult> authoritysResourceInfo(String dsId){
        Assert.hasText(dsId, "资源权限ID不能为空!");
        return ServiceUtil.buildFuture(JsonResult.successfu(dsService.getById(dsId)));
    }

}
