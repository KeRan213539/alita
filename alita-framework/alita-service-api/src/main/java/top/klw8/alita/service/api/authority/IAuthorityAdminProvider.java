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
import top.klw8.alita.entitys.authority.AlitaAuthoritysMenu;
import top.klw8.alita.entitys.authority.AlitaAuthoritysCatlog;
import top.klw8.alita.entitys.authority.AlitaAuthoritysResource;
import top.klw8.alita.entitys.authority.AlitaRole;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.service.result.JsonResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 权限管理相关dubbo服务提供者
 * 2019/8/14 9:08
 */
public interface IAuthorityAdminProvider {

    /**
     * 新增权限
     * 2019/8/14 9:39
     * @param: au
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addAuthority(AlitaAuthoritysMenu au);

    /**
     * 新增权限目录
     * 2019/8/14 11:08
     * @param: catlog
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addCatlog(AlitaAuthoritysCatlog catlog);


    /**
     * 向角色中添加权限
     * 2019/8/14 11:18
     * @param: roleId
     * @param: auId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addRoleAu(String roleId, String auId);

    /**
     * 向用户中添加角色
     * 2019/8/14 13:54
     * @param: userId
     * @param: roleId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addUserRole(String userId, String roleId);

    /**
     *
     * 刷新缓存中的权限
     * 15:42 2019-08-14
     * @param userId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     **/
    CompletableFuture<JsonResult> refreshUserAuthoritys(String userId);

    /**
     * 添加角色
     * 2019/8/14 11:15
     * @param: role
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addSysRole(AlitaRole role);

    /**
     * 角色列表
     * 2020/7/16 16:36
     * @param: roleName
     * @param: appTag
     * @param: page
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> roleList(String roleName, String appTag, Page<AlitaRole> page);

    /**
     * 获取全部角色,不分页
     * 2020/7/16 16:36
     * @param: appTag
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> roleAll(String appTag);

    /**
     * 获取全部权限,并根据传入的角色ID标识出该角色拥有的权限
     * 2020/7/16 16:43
     * @param: roleId
     * @param: appTag
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> markRoleAuthoritys(String roleId, String appTag);

    /**
     * 保存角色的权限(替换原有权限)
     * 2019/10/18 17:38
     * @param: roleId
     * @param: auIds
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> saveRoleAuthoritys(String roleId, List<String> auIds, String appTag);

    /**
     * 保存角色(无ID则添加,有则修改)
     * 如果传入的角色中包含权限,会同时保存这些权限.
     * 如果 copyAuFromRoleId 有值,并且目标角色存在,会复制该角色的权限并保存到新角色中
     * 如果 copyAuFromRoleId有值并且要保存的角色中也有权限,要保存的角色中的权限会被 copyAuFromRoleId 覆盖
     * 2019/10/18 17:39
     * @param: role
     * @param: copyAuFromRoleId 角色ID,如果有值,并且目标角色存在,会复制该角色的权限并保存到新角色中
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> saveRole(AlitaRole role, String copyAuFromRoleId);

    /**
     * 删除角色(只能删除没有关联用户的角色,删除时会删除角色中配制的权限)
     * 2019/10/19 14:46
     * @param: roleId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> delRole(String roleId);

    /**
     * 角色信息
     * 2019/10/31 11:47
     * @param: roleId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> roleInfo(String roleId);

    /**
     * 权限目录列表,分页,可根据目录名称查询
     * 2020/7/16 16:54
     * @param: catlogName
     * @param: appTag
     * @param: page
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> catlogList(String catlogName, String appTag, Page<AlitaAuthoritysCatlog> page);

    /**
     * 获取全部权限目录,不分页
     * 2019/10/22 14:05
     * @param: 
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> catlogAll(String appTag);

    /**
     * 新增/修改权限目录的保存
     * 2019/10/22 11:21
     * @param: catlog
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> saveCatlog(AlitaAuthoritysCatlog catlog);

    /**
     * 删除权限目录(没有权限关联到它才能删除)
     * 2019/10/22 11:23
     * @param: catlogId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> delCatlog(String catlogId);

    /**
     * 权限目录详情
     * 2019/10/31 11:52
     * @param: catlogId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> catlogInfo(String catlogId);


    /**
     * 权限列表,分页,可根据权限名称查询
     * 2019/10/22 14:08
     * @param: auName
     * @param: page
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> authoritysList(String auName, AuthorityTypeEnum auType,
                                                 Page<AlitaAuthoritysMenu> page, String authorityAction,
                                                 String catlogName, String appTag);

    /**
     * 新增/修改权限的保存
     * 2019/10/22 11:21
     * @param: catlog
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> saveAuthority(AlitaAuthoritysMenu au, String httpMethod);

    /**
     * 删除权限(同时删除角色的关联)
     * 2019/10/22 11:23
     * @param: catlogId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> delAuthority(String auId);

    /**
     * 权限详情
     * 2019/10/31 14:46
     * @param: auId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> auInfo(String auId);

    /**
     * 根据权限路径获取该权限下的资源权限(包括全局资源权限)
     * 2020/8/4 15:31
     * @param: httpMethod
     * @param: auAction
     * @param: appTag
     * @param: userId  有值查指定用户权限中的,没值查全部
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> authoritysResourceByAuthorityAction(String httpMethod, String auAction, String appTag, String userId);

    /**
     * 获取全部权限,包含目录信息,并按目录分组
     * 2020/7/17 14:46
     * @param: appTag
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> allAuthoritysWithCatlog(String appTag);


    /**
     * 资源权限列表,分页,可根据资源名称查询
     * 2020/5/20 11:21
     * @param: resource
     * @param: page
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> authoritysResourceList(String resource, String appTag, Page<AlitaAuthoritysResource> page);

    /**
     * 新增/修改资源权限的保存
     * 2020/5/20 11:22
     * @param: catlog
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> saveAuthoritysResource(AlitaAuthoritysResource ds);

    /**
     * 删除资源权限(没有角色关联它才能删除)
     * 2020/5/20 11:23
     * @param: dsId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> delAuthoritysResource(String dsId);

    /**
     * 资源权限详情
     * 2020/5/20 11:23
     * @param: dsId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> authoritysResourceInfo(String dsId);

}
