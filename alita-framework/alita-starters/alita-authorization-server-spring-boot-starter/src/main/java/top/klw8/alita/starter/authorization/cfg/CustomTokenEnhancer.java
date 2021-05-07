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
package top.klw8.alita.starter.authorization.cfg;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * token中的附加数据
 * 2018年11月2日 下午4:43:03
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    private IAuthorityAdminProvider authorityAdminProvider;

    private IAlitaUserProvider userService;

    public CustomTokenEnhancer(IAuthorityAdminProvider authorityAdminProvider, IAlitaUserProvider userService) {
        this.authorityAdminProvider = authorityAdminProvider;
        this.userService = userService;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        // 这里可以做一些其他事情,比如从库里查一些数据放到token里面,或者把一些东西放redis
        // token 里不能放敏感信息,因为token只是base64编码,可以反编码出来.虽然要加上HTTPS,但是也不是绝对安全
        // authentication.getName() 可以拿到登录的用户名
        // authentication 里面包含用户实体  authentication.getUserAuthentication().getPrincipal()
        

        //token 中也不要放太多内容了,特别是敏感信息. 还是放在缓存里吧
        Map<String, Object> additionalInfo = new HashMap<>();
        Object principal = authentication.getUserAuthentication().getPrincipal();
        AlitaUserAccount user = null;
        if (principal instanceof AlitaUserAccount) {
            user = (AlitaUserAccount) principal;
            additionalInfo.put("userId", user.getId());
        } else if (principal instanceof String) {
            try {
                user = userService.findUserByName((String)principal).get();
                additionalInfo.put("userId", user.getId());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        authorityAdminProvider.refreshUserAuthoritys(user.getId());

        return accessToken;
    }

}
