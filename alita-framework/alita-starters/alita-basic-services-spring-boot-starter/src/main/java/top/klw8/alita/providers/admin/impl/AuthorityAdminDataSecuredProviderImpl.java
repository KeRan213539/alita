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

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import top.klw8.alita.entitys.authority.*;
import top.klw8.alita.service.api.authority.IAuthorityAdminDataSecuredProvider;
import top.klw8.alita.service.authority.*;

import java.util.List;

/**
 * 权限管理数据权限相关 实现
 * 2020/7/27 15:33
 */
@Slf4j
@DubboService
public class AuthorityAdminDataSecuredProviderImpl implements IAuthorityAdminDataSecuredProvider {

    @Autowired
    private IAuthorityAppService appService;

    @Autowired
    private ISystemRoleService roleService;

    @Autowired
    private ISystemAuthoritysCatlogService catlogService;

    @Autowired
    private ISystemAuthoritysService authoritysService;

    @Autowired
    private ISystemDataSecuredService dataSecuredService;

    @Override
    public List<AlitaAuthoritysApp> allApp() {
        return appService.list();
    }

    @Override
    public AlitaRole roleById(String id) {
        return roleService.getById(id);
    }

    @Override
    public AlitaAuthoritysCatlog catlogById(String id) {
        return catlogService.getById(id);
    }

    @Override
    public AlitaAuthoritysMenu auById(String id) {
        return authoritysService.getById(id);
    }

    @Override
    public AlitaAuthoritysResource dsById(String id) {
        return dataSecuredService.getById(id);
    }


}
