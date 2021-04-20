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
package top.klw8.alita.web.authority.ds;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;
import top.klw8.alita.entitys.authority.AlitaAuthoritysApp;
import top.klw8.alita.entitys.authority.enums.ResourceType;
import top.klw8.alita.service.api.authority.IAuthorityAdminAuthoritysResourceProvider;
import top.klw8.alita.starter.annotations.PublicAuthoritysResourceRegister;
import top.klw8.alita.starter.auscan.IAuthoritysResourceSource;

import java.util.ArrayList;
import java.util.List;

/**
 * appTag的资源权限资源数据源
 * 2020/7/27 15:19
 */
@Component
@PublicAuthoritysResourceRegister
public class AppTagAuthoritysResourceSource implements IAuthoritysResourceSource {

    @DubboReference
    private IAuthorityAdminAuthoritysResourceProvider adminAuthoritysResourceProvider;

    @Override
    public List<AppAuthoritysResourceSourceItem> getAuthoritysResourceSourceList() {
        List<AlitaAuthoritysApp> allApp = adminAuthoritysResourceProvider.allApp();
        if(null != allApp) {
            List<AppAuthoritysResourceSourceItem> result = new ArrayList<>(allApp.size());
            for (AlitaAuthoritysApp app : allApp) {
                result.add(new AppAuthoritysResourceSourceItem(app, ResourceType.DATA));
            }
            return result;
        }
        return null;
    }

}
