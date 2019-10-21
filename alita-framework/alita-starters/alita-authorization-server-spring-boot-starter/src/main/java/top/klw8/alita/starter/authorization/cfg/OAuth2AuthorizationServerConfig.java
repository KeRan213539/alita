package top.klw8.alita.starter.authorization.cfg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
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
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;
import top.klw8.alita.starter.authorization.cfg.beans.ClientItemBean;
import top.klw8.alita.starter.authorization.cfg.beans.OAuth2ClientBean;


/**
 * @ClassName: OAuth2AuthorizationServerConfig
 * @Description: spring-security OAuth2 配制,使用 jwt
 * @author klw
 * @date 2018年11月1日 下午3:52:48
 */
@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(OAuth2ClientBean.class)
@Slf4j
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Environment env;

    @Autowired
    private IExtTokenGranters extTokenGranterBean;

    @Reference(async=true)
    private IAuthorityAdminProvider authorityAdminProvider;

    @Reference(async=true)
    private IAlitaUserProvider userProvider;

    @javax.annotation.Resource
    private OAuth2ClientBean clientCfg;

    private ClientDetailsService clientDetailsService = null;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        if(clientDetailsService == null) {
            buildClientDetailsService();
        }
        clients.withClientDetails(clientDetailsService);
    }

    private void buildClientDetailsService() {
        Map<String, ClientDetails> clientDetails = new HashMap<String, ClientDetails>();
        if(clientCfg != null && CollectionUtils.isNotEmpty(clientCfg.getClientList())) {
            List<ClientItemBean> clientList = clientCfg.getClientList();
            for(ClientItemBean client : clientList) {
                BaseClientDetails result = new BaseClientDetails();
                result.setClientId(client.getClientId());
                result.setClientSecret(client.getClientSecret());
                result.setScope(client.getScope());
                result.setAuthorizedGrantTypes(client.getAuthorizedGrantTypes());
                if(client.getAuthorities() != null) {
                    result.setAuthorities(AuthorityUtils.createAuthorityList(client.getAuthorities().toArray(new String[]{})));
                }
                clientDetails.put(result.getClientId(), result);
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
        endpoints.tokenStore(tokenStore())
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

    }


    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("authorizationKey.jks"), "aLitAPAsSwOrd".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("alita"));
        return converter;
    }

    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenEnhancer(tokenEnhancer());
        String[] activeprofiles = env.getActiveProfiles();
        for(String activeprofile : activeprofiles) {
            if("dev".equals(activeprofile)) {
                // dev 模式下token和刷新token永不过期, 默认为 access_token 12小时, refresh_token 30天
                defaultTokenServices.setAccessTokenValiditySeconds(-1);
                defaultTokenServices.setRefreshTokenValiditySeconds(-1);
                break;
            }
        }
        return defaultTokenServices;
    }


}
