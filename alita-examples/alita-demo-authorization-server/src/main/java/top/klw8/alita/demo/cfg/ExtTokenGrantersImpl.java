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
package top.klw8.alita.demo.cfg;

import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import top.klw8.alita.demo.oauth2.provider.SMSCodeLoginTokenGranter;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.starter.authorization.cfg.IExtTokenGranters;

import java.util.ArrayList;
import java.util.List;

/**
 * 扩展登录方式配制实现
 * 2019/8/20 11:17
 */
public class ExtTokenGrantersImpl implements IExtTokenGranters {
    @Override
    public List<TokenGranter> extTokenGranterList(AuthorizationServerTokenServices tokenServices,
                                                  ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory,
                                                  IAlitaUserProvider userProvider) {
        List<TokenGranter> result = new ArrayList<>(1);
        result.add(new SMSCodeLoginTokenGranter(tokenServices, clientDetailsService, requestFactory, userProvider));
        return result;
    }
}
