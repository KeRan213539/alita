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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import lombok.extern.slf4j.Slf4j;
import top.klw8.alita.entitys.authority.AlitaAuthoritysAppChannel;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;
import top.klw8.alita.service.api.authority.IAuthorityAppChannelProvider;
import top.klw8.alita.starter.authorization.cfg.beans.TokenConfigBean;
import top.klw8.alita.starter.authorization.cfg.tokenStore.RedisJwtTokenStore;
import top.klw8.alita.starter.authorization.endpoints.LogoutEndpoint;
import top.klw8.alita.utils.TokenUtil;
import top.klw8.alita.utils.redis.TokenRedisUtil;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * spring-security OAuth2 配制,使用 jwt
 * 2018年11月1日 下午3:52:48
 */
@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties({TokenConfigBean.class})
@Import(LogoutEndpoint.class)
@Slf4j
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Environment env;

    @Autowired(required = false)
    private IExtTokenGranters extTokenGranterBean;

    @Autowired
    private TokenStore tokenStore;
    
    @Autowired
    private ApplicationContext context;

    @DubboReference(async=true)
    private IAuthorityAdminProvider authorityAdminProvider;

    @DubboReference(async=true)
    private IAlitaUserProvider userProvider;
    
    @DubboReference
    private IAuthorityAppChannelProvider channelProvider;

    @Resource
    private TokenConfigBean tokenCfg;

    private ClientDetailsService clientDetailsService = null;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        if(clientDetailsService == null) {
            buildClientDetailsService();
        }
        clients.withClientDetails(clientDetailsService);
    }

    private void buildClientDetailsService() {
        Map<String, ClientDetails> clientDetails = new HashMap<>();
        List<AlitaAuthoritysAppChannel> allChannel = channelProvider.allChannel();
        if(CollectionUtils.isNotEmpty(allChannel)){
            BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
            for(AlitaAuthoritysAppChannel channel : allChannel) {
                BaseClientDetails client = new BaseClientDetails();
                client.setClientId(channel.getAppTag() + TokenUtil.APP_CHANNEL_COMBINED_CLIENT_SPLIT + channel.getChannelTag());
                client.setClientSecret(pwdEncoder.encode(channel.getChannelPwd()));
                client.setScope(Lists.newArrayList("select"));
                client.setAuthorizedGrantTypes(Lists.newArrayList(channel.getChannelLoginType().split(",")));
                clientDetails.put(client.getClientId(), client);
            }
        } else {
            log.error("没有配制认证中心客户端,这将导致没有任何客户端可以请求认证中心接口");
        }
        
        InMemoryClientDetailsService inMemClientDetailsService = new InMemoryClientDetailsService();
        inMemClientDetailsService.setClientDetailsStore(clientDetails);
        clientDetailsService = inMemClientDetailsService;
    }

    private OAuth2RequestFactory requestFactory(ClientDetailsService clientDetailsService) {
        return new DefaultOAuth2RequestFactory(clientDetailsService);
    }



    private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<>(Arrays.asList(endpoints.getTokenGranter()));
        if(extTokenGranterBean != null){
            List<TokenGranter> extTokenGranterList = extTokenGranterBean.extTokenGranterList(
                    endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), userProvider);
            if(CollectionUtils.isNotEmpty(extTokenGranterList)) {
                granters.addAll(extTokenGranterList);
            }
        }
        return new CompositeTokenGranter(granters);

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        if(clientDetailsService == null) {
            buildClientDetailsService();
        }
        endpoints.tokenStore(tokenStore)
//		.accessTokenConverter(accessTokenConverter())
                .tokenGranter(tokenGranter(endpoints))
                .tokenServices(tokenServices())
                .requestFactory(requestFactory(clientDetailsService))
                .authorizationCodeServices(new InMemoryAuthorizationCodeServices())
//		.tokenEnhancer(tokenEnhancerChain)  // 设了 tokenGranter 后该配制失效,需要在 tokenServices() 中设置
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)  //refresh_token 需要,否则会 UserDetailsService is required
                .allowedTokenEndpointRequestMethods(HttpMethod.POST)
                .setClientDetailsService(clientDetailsService);
    }

    public TokenEnhancer tokenEnhancer() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new CustomTokenEnhancer(authorityAdminProvider, userProvider), accessTokenConverter()));
        return tokenEnhancerChain;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        //允许表单认证
        oauthServer.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()");  // 允许 check_token
        //        .sslOnly()   //TODO 强制使用 https方式.后面开启
        
        Map<String, TokenEndpointFilter> validatorImplsMap = context.getBeansOfType(TokenEndpointFilter.class);
        if (MapUtils.isNotEmpty(validatorImplsMap)) {
            for (Map.Entry<String, TokenEndpointFilter> entry : validatorImplsMap.entrySet()) {
                oauthServer.addTokenEndpointAuthenticationFilter(new Filter() {
                    @Override
                    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                            FilterChain filterChain) throws IOException, ServletException {
                        entry.getValue().doFilter(servletRequest, servletResponse, filterChain);
                    }
                });
            }
        }

    }

    @Bean
    public TokenStore tokenStore() {
        if(tokenCfg.isStoreInRedis()) {
            return new RedisJwtTokenStore(accessTokenConverter());
        } else {
            return new JwtTokenStore(accessTokenConverter());
        }
    }

    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("authorizationKey.jks"), "aLitAPAsSwOrd".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("alita"));
        return converter;
    }

    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenEnhancer(tokenEnhancer());
        String[] activeprofiles = env.getActiveProfiles();
        boolean isDev = false;
        for(String activeprofile : activeprofiles) {
            if("dev".equals(activeprofile)) {
                isDev = true;
                // dev 模式下token和刷新token永不过期, 默认为 access_token 12小时, refresh_token 30天
                defaultTokenServices.setAccessTokenValiditySeconds(-1);
                defaultTokenServices.setRefreshTokenValiditySeconds(-1);
                break;
            }
        }
        if(!isDev){
            defaultTokenServices.setAccessTokenValiditySeconds(tokenCfg.getTimeoutSeconds().getAccess());
            defaultTokenServices.setRefreshTokenValiditySeconds(tokenCfg.getTimeoutSeconds().getRefresh());
            TokenRedisUtil.setTokenTimeout(tokenCfg.getTimeoutSeconds().getAccess(), tokenCfg.getTimeoutSeconds().getRefresh());
        }
        return defaultTokenServices;
    }


}
