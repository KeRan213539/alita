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
package top.klw8.alita.web.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.klw8.alita.web.authority.AuthorityAdminController;
import top.klw8.alita.web.authority.AuthorityAppChannelController;
import top.klw8.alita.web.authority.AuthorityAppController;
import top.klw8.alita.web.authority.ds.AppTagByIdParser;
import top.klw8.alita.web.authority.ds.AppTagAuthoritysResourceSource;
import top.klw8.alita.web.authority.ds.AppTagParser;
import top.klw8.alita.web.user.SysUserAdminController;
import top.klw8.alita.web.user.SysUserController;

/**
 * 基础web接口配制
 * 2019/11/4 14:14
 */
@Configuration
@Import({AuthorityAdminController.class, SysUserAdminController.class, SysUserController.class,
        AuthorityAppController.class, AuthorityAppChannelController.class,
        AppTagAuthoritysResourceSource.class, AppTagParser.class, AppTagByIdParser.class})
public class BasicWebApisConfig {
}
