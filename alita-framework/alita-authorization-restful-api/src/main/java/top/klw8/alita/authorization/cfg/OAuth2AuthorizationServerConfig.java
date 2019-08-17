package top.klw8.alita.authorization.cfg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
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
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import lombok.extern.slf4j.Slf4j;
import top.klw8.alita.authorization.cfg.beans.ClientItemBean;
import top.klw8.alita.authorization.cfg.beans.OAuth2ClientBean;
import top.klw8.alita.authorization.oauth2.provider.SMSCodeLoginTokenGranter;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;


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

	@Reference(async=true)
	private IAuthorityAdminProvider authorityAdminProvider;

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

    private AuthorizationCodeServices authorizationCodeServices() {
	return new InMemoryAuthorizationCodeServices();
    }

    private OAuth2RequestFactory requestFactory() {
	if(clientDetailsService == null) {
	    buildClientDetailsService();
	}
	return new DefaultOAuth2RequestFactory(clientDetailsService);
    }

    private List<TokenGranter> getDefaultTokenGranters() {
	if(clientDetailsService == null) {
	    buildClientDetailsService();
	}
	AuthorizationServerTokenServices tokenServices = tokenServices();
	AuthorizationCodeServices authorizationCodeServices = authorizationCodeServices();
	OAuth2RequestFactory requestFactory = requestFactory();

	List<TokenGranter> tokenGranters = new ArrayList<TokenGranter>();
	tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices,
		authorizationCodeServices, clientDetailsService, requestFactory));
	tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetailsService, requestFactory));
	ImplicitTokenGranter implicit = new ImplicitTokenGranter(tokenServices, clientDetailsService,
		requestFactory);
	tokenGranters.add(implicit);
	tokenGranters.add(
		new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, requestFactory));
	if (authenticationManager != null) {
	    tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager,
		    tokenServices, clientDetailsService, requestFactory));
	}
	
	tokenGranters.add(new SMSCodeLoginTokenGranter(tokenServices, clientDetailsService, requestFactory, userDetailsService));
	return tokenGranters;
    }

    private TokenGranter tokenGranter() {
	TokenGranter tokenGranter = new TokenGranter() {
	    private CompositeTokenGranter delegate;

	    @Override
	    public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
		if (delegate == null) {
		    delegate = new CompositeTokenGranter(getDefaultTokenGranters());
		}
		return delegate.grant(grantType, tokenRequest);
	    }
	};
	return tokenGranter;
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
//		.accessTokenConverter(accessTokenConverter())
        	.tokenGranter(tokenGranter())
//		.tokenEnhancer(tokenEnhancerChain)  // 设了 tokenGranter 后该配制失效,需要在 tokenServices() 中设置
		.authenticationManager(authenticationManager)
		.userDetailsService(userDetailsService)  //refresh_token 需要,否则会 UserDetailsService is required
		.allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }
    
    @Bean
    public TokenEnhancer tokenEnhancer() {
	TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
	tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new CustomTokenEnhancer(authorityAdminProvider), accessTokenConverter()));
        return tokenEnhancerChain;
    }
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        //允许表单认证
        oauthServer.allowFormAuthenticationForClients();
//        .sslOnly()   //TODO 强制使用 https方式.后面开启
//        .checkTokenAccess("permitAll()");  // 允许 check_token, 因为用了JWT,客户端可以验证签名,生产中可以不用
    }
    

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("authorizationKey.jks"), "aLitAPAsSwOrd".toCharArray());
	converter.setKeyPair(keyStoreKeyFactory.getKeyPair("alita"));
	return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenEnhancer(tokenEnhancer());
        String[] activeprofiles = env.getActiveProfiles();
        for(String activeprofile : activeprofiles) {
            if("dev".equals(activeprofile)) {
        	// dev 模式下token和刷新token永不过期
        	defaultTokenServices.setAccessTokenValiditySeconds(-1);
                defaultTokenServices.setRefreshTokenValiditySeconds(-1);
                break;
            }
        }
        return defaultTokenServices;
    }
    
    
}
