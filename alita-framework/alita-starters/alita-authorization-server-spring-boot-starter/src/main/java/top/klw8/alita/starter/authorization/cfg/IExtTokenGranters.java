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
package top.klw8.alita.starter.authorization.cfg;

import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ExtTokenGranterBean
 * @Description: 扩展的登录方式配制
 * @date 2019/8/20 10:59
 */
public interface IExtTokenGranters {

    List<TokenGranter> extTokenGranterList(AuthorizationServerTokenServices tokenServices,
                                           ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, IAlitaUserProvider userProvider);

}
