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
package top.klw8.alita.service.authority;

import com.baomidou.mybatisplus.extension.service.IService;
import top.klw8.alita.entitys.authority.SystemAuthoritysApp;
import top.klw8.alita.service.result.JsonResult;

/**
 * @author zhaozheng
 * @description:
 * @date: 2020-07-16
 */

public interface IAuthorityAppService extends IService<SystemAuthoritysApp> {


    JsonResult addAuthorityApp(SystemAuthoritysApp authorityApp);

    JsonResult deleteAuthorityApp(String appTag);

    JsonResult updateAuthorityApp(SystemAuthoritysApp authorityApp);

    JsonResult<SystemAuthoritysApp> findAuthorityApp(SystemAuthoritysApp authorityApp);
}
