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

import top.klw8.alita.entitys.authority.*;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: IAuthorityAdminDataSecuredProvider
 * @Description: 权限管理数据权限相关
 * @date 2020/7/27 15:32
 */
public interface IAuthorityAdminDataSecuredProvider {

    List<AlitaAuthoritysApp> allApp();

    AlitaRole roleById(String id);

    AlitaAuthoritysCatlog catlogById(String id);

    AlitaAuthoritysMenu auById(String id);

    AlitaAuthoritysResource dsById(String id);

}
