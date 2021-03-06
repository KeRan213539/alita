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
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.result.JsonResult;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 用户相关dubbo提供者
 * 2019/8/14 14:15
 */
public interface IAlitaUserProvider {

    /**
     * 根据用户ID查找用户
     * 2019/8/14 14:19
     * @param: userId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.entitys.user.AlitaUserAccount>
     */
    CompletableFuture<AlitaUserAccount> findUserById(String userId);

    /**
     * 根据用户名(帐号)查找用户
     * 2019/8/14 16:31
     * @param: userName
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.entitys.user.AlitaUserAccount>
     */
    CompletableFuture<AlitaUserAccount> findUserByName(String userName);

    /**
     * 根据用户手机号查找用户
     * 2019/8/14 16:31
     * @param: userPhoneNum
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.entitys.user.AlitaUserAccount>
     */
    CompletableFuture<AlitaUserAccount> findUserByPhoneNum(String userPhoneNum);

    /**
     * 根据用户id获取用户的权限菜单
     * 2020/7/17 17:06
     * @param: userId
     * @param: appTag
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> findUserAuthorityMenus(String userId, String appTag);

    /**
     * 根据条件查询用户列表
     * 2019/10/16 9:20
     * @param: user
     * @param: createDateBegin
     * @param: createDateEnd
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> userList(AlitaUserAccount user, LocalDate createDateBegin, LocalDate createDateEnd, String appTag, Page<AlitaUserAccount> page);

    /**
     * 保存用户拥有的角色(替换原有角色)
     * 2020/7/27 14:40
     * @param: userId
     * @param: roleIds
     * @param: appTag
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> saveUserRoles(String userId, List<String> roleIds, String appTag);

    /**
     * 新增用户
     * 2019/10/16 10:00
     * @param: user
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addSaveUser(AlitaUserAccount user);

    /**
     * 根据用户ID修改指定用户的密码
     * 2019/10/16 16:24
     * @param: userId
     * @param: oldPwd
     * @param: newPwd
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> changeUserPasswordByUserId(String userId, String oldPwd, String newPwd);

    /**
     * 根据用户ID查找该用户拥有的角色和对应权限
     * 2019/10/24 16:08
     * @param: userId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> getUserAllRoles(String userId, String appTag);

    /**
     * 用户详情
     * 2019/10/31 11:21
     * @param: userId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> userInfo(String userId);

    /**
     * 改变用户状态(启用/禁用用户)
     * 2019/10/31 15:23
     * @param: userId
     * @param: enabled 是否启用: true 为启用, false为禁用
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> changeUserStatus(String userId, boolean enabled);

}
