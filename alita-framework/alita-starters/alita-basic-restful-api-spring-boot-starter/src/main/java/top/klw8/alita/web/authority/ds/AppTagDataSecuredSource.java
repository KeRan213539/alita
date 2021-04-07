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
import top.klw8.alita.entitys.authority.SystemAuthoritysApp;
import top.klw8.alita.service.api.authority.IAuthorityAdminDataSecuredProvider;
import top.klw8.alita.starter.annotations.PublicDataSecuredRegister;
import top.klw8.alita.starter.auscan.IDataSecuredSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AppTagDataSecuredSource
 * @Description: appTag的数据权限资源数据源
 * @date 2020/7/27 15:19
 */
@Component
@PublicDataSecuredRegister
public class AppTagDataSecuredSource implements IDataSecuredSource {

    @DubboReference
    private IAuthorityAdminDataSecuredProvider adminDataSecuredProvider;

    @Override
    public List<AppDataSecuredSourceItem> getDataSecuredSourceList() {
        List<SystemAuthoritysApp> allApp = adminDataSecuredProvider.allApp();
        if(null != allApp) {
            List<AppDataSecuredSourceItem> result = new ArrayList<>(allApp.size());
            for (SystemAuthoritysApp app : allApp) {
                result.add(new AppDataSecuredSourceItem(app));
            }
            return result;
        }
        return null;
    }

}
