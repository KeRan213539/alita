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

import top.klw8.alita.entitys.authority.SystemAuthoritysApp;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.entitys.authority.SystemDataSecured;
import top.klw8.alita.service.result.JsonResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: IDevHelperProvider
 * @Description: 开发辅助工具
 * @date 2019/8/15 8:42
 */
public interface IDevHelperProvider {

    /**
     * @author klw(213539@qq.com)
     * @Description: 批量添加权限目录和权限
     * @Date 2019/8/15 9:33
     * @param: catlogList
     * @return boolean
     */
    CompletableFuture<JsonResult> batchAddAuthoritysAndCatlogs(List<SystemAuthoritysCatlog> catlogList,
                                                               List<SystemDataSecured> publicDataSecuredList,
                                                               boolean isAdd2SuperAdmin, SystemAuthoritysApp app);

    /**
     * @author klw(213539@qq.com)
     * @Description: 添加所有权限到管理员角色和管理员账户,如果管理员角色或账户不存在则创建
     * @Date 2020/8/4 17:11
     * @param: app
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addAllAuthoritys2AdminRole(SystemAuthoritysApp app);

}
