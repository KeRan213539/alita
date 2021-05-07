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

import top.klw8.alita.entitys.authority.AlitaAuthoritysApp;
import top.klw8.alita.entitys.authority.AlitaAuthoritysCatlog;
import top.klw8.alita.entitys.authority.AlitaAuthoritysResource;
import top.klw8.alita.service.result.JsonResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 开发辅助工具
 * 2019/8/15 8:42
 */
public interface IDevHelperProvider {

    /**
     * 批量添加权限目录和权限
     * 2019/8/15 9:33
     * @param: catlogList
     * @return boolean
     */
    CompletableFuture<JsonResult> batchAddAuthoritysAndCatlogs(List<AlitaAuthoritysCatlog> catlogList,
                                                               List<AlitaAuthoritysResource> publicAuthoritysResourceList,
                                                               boolean isAdd2SuperAdmin, AlitaAuthoritysApp app);

    /**
     * 添加所有权限到管理员角色和管理员账户,如果管理员角色或账户不存在则创建
     * 2020/8/4 17:11
     * @param: app
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addAllAuthoritys2AdminRole(AlitaAuthoritysApp app);

}
