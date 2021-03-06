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
package top.klw8.alita.starter.cfg;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;
import top.klw8.alita.service.result.code.CommonResultCodeEnum;
import top.klw8.alita.starter.common.UserCacheHelper;
import top.klw8.alita.starter.aures.AuthoritysResourceControllerMethodsLoader;
import top.klw8.alita.starter.web.interceptor.AuthorityInterceptor;
import top.klw8.alita.starter.validator.AlitaResponseGenerator;
import top.klw8.alita.starter.web.interceptor.TokenCheckInterceptor;
import top.klw8.alita.validator.EnableValidator;

/**
 * spring-security OAuth2 配制,使用 jwt
 * 2018年11月1日 下午3:52:48
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({ResServerAuthPathCfgBean.class, TokenConfigBean.class})
@EnableWebFluxSecurity
@EnableValidator(responseMsgGenerator = AlitaResponseGenerator.class)
@Import({TokenCheckInterceptor.class, AuthorityInterceptor.class, AuthoritysResourceControllerMethodsLoader.class})
public class OAuth2ResourceServerConfig {

    @javax.annotation.Resource
    private ResServerAuthPathCfgBean cfgBean;
    
    @javax.annotation.Resource
    private TokenConfigBean tokenConfigBean;

    @DubboReference(async = true)
    private IAuthorityAdminProvider adminProvider;

    @Value("${alita.authority.app.tag:}")
    private String currectAppTag;

    @Value("${alita.authority.app.name:}")
    private String currectAppName;

    @Value("${alita.authority.app.remark:}")
    private String currectAppRemark;

    @Bean
    public AuthorityAppInfoInConfigBean authorityAppInfoInConfig(){
        Assert.hasText(currectAppTag, CommonResultCodeEnum.APP_TAG_NOT_EXIST.getCodeMsg());
        return new AuthorityAppInfoInConfigBean(currectAppTag, currectAppName, currectAppRemark);
    }

    @Bean
    public UserCacheHelper userCacheHelper() {
        return new UserCacheHelper(adminProvider);
    }

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) throws Exception {
        if (cfgBean == null || CollectionUtils.isEmpty(cfgBean.getAuthPath())) {
            log.warn("---------------------------------------------------------------------");
            log.warn("【警告】没有配制需要验证权限的url, 如果项目本身没有这个需求请忽略本警告!");
            log.warn("---------------------------------------------------------------------");
        }
        SecurityAuthenticationEntryPoint xx = new SecurityAuthenticationEntryPoint();
        http.exceptionHandling()
                .accessDeniedHandler(xx)
                .authenticationEntryPoint(xx)
                .and()
                .csrf().disable();
        // 所有页面都验证token,除了排除的
        // 下面配制不验证token的url
        if(CollectionUtils.isNotEmpty(tokenConfigBean.getCheckExcludePaths())){
            for(String path : tokenConfigBean.getCheckExcludePaths()){
                http.authorizeExchange().pathMatchers(path).permitAll();
            }
        }
        http.authorizeExchange().anyExchange().authenticated();
        http.oauth2ResourceServer().jwt().publicKey(jwtPublicKey());
        return http.build();
    }

    @SuppressWarnings("restriction")
    private RSAPublicKey jwtPublicKey() {
        Resource resource = new ClassPathResource("authorizationKeyPublic.txt");
        String publicKey;
        try {
            publicKey = IOUtils.toString(resource.getInputStream(), Charset.defaultCharset());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        byte[] keyBytes;
        keyBytes = Base64.getDecoder().decode(publicKey.replace("\r\n", "").replace("\n", ""));
//        try {
//			keyBytes = (new sun.misc.BASE64Decoder()).decodeBuffer(publicKey);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        RSAPublicKey publicKey2 = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey2 = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {

        }

        return publicKey2;
    }

}
