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
package top.klw8.alita.demo.oauth2.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.starter.authorization.oauth2.userChecks.DefaultPostAuthenticationChecks;
import top.klw8.alita.starter.authorization.oauth2.userChecks.DefaultPreAuthenticationChecks;
import top.klw8.alita.utils.redis.RedisTagEnum;
import top.klw8.alita.utils.redis.RedisUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static top.klw8.alita.demo.cfg.SysContext.SMS_CODE_CACHE_PREFIX;

/**
 * 短信验证码登录的 TokenGranter
 * 2018年11月20日 下午4:36:16
 */
@Slf4j
public class SMSCodeLoginTokenGranter extends AbstractTokenGranter {

    private UserDetailsChecker preAuthenticationChecks = new DefaultPreAuthenticationChecks();
    private UserDetailsChecker postAuthenticationChecks = new DefaultPostAuthenticationChecks();

    private static final String GRANT_TYPE = "sms_code";

    private IAlitaUserProvider userProvider;

    public SMSCodeLoginTokenGranter(AuthorizationServerTokenServices tokenServices,
                                    ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, IAlitaUserProvider userProvider) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.userProvider = userProvider;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client,
                                                           TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        String userLoginIdentifier = parameters.get("userLoginIdentifier");
        String smscode = parameters.get("smscode");
        if (StringUtils.isBlank(userLoginIdentifier) || StringUtils.isBlank(smscode)) {
            throw new InvalidGrantException("缺少必传参数");
        }

        // 从库里查用户
        AlitaUserAccount user = null;
        try {
            user = userProvider.findUserByName(userLoginIdentifier).get();
            if(user == null){
                user = userProvider.findUserByPhoneNum(userLoginIdentifier).get();
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("查找用户出错", e);
        }
        if(user == null){
            throw new InvalidGrantException("用户不存在");
        }
        String userMobileNo = user.getUserPhoneNum();
        if(StringUtils.isBlank(userMobileNo)){
            throw new InvalidGrantException("用户没有绑定手机号");
        }
        preAuthenticationChecks.check(user); // 验证用户状态

        // 验证验证码
        String smsCodeCached = (String) RedisUtil.get(SMS_CODE_CACHE_PREFIX + userMobileNo,
                RedisTagEnum.REDIS_TAG_DEFAULT);
        if (StringUtils.isBlank(smsCodeCached)) {
            throw new InvalidGrantException("用户没有发送验证码");
        }
        if (!smscode.equals(smsCodeCached)) {
            throw new InvalidGrantException("验证码不正确");
        } else {
            RedisUtil.del(SMS_CODE_CACHE_PREFIX + userMobileNo,
                    RedisTagEnum.REDIS_TAG_DEFAULT);
        }

        postAuthenticationChecks.check(user); // 验证用户密码是否过期

        Authentication userAuth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }

}
