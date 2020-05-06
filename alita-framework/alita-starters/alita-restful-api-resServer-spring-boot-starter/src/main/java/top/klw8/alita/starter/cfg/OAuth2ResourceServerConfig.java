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
import org.apache.dubbo.config.annotation.Reference;
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
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;
import top.klw8.alita.starter.common.UserCacheHelper;
import top.klw8.alita.starter.datasecured.DataSecuredControllerMethodsLoader;
import top.klw8.alita.starter.web.interceptor.AuthorityInterceptor;
import top.klw8.alita.starter.validator.AlitaResponseGenerator;
import top.klw8.alita.validator.EnableValidator;

/**
 * @ClassName: OAuth2ResourceServerConfig
 * @Description: spring-security OAuth2 配制,使用 jwt
 * @author klw
 * @date 2018年11月1日 下午3:52:48
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(ResServerAuthPathCfgBean.class)
@EnableWebFluxSecurity
@EnableValidator(responseMsgGenerator = AlitaResponseGenerator.class)
@Import({AuthorityInterceptor.class, DataSecuredControllerMethodsLoader.class})
public class OAuth2ResourceServerConfig {
    
    @javax.annotation.Resource
    private ResServerAuthPathCfgBean cfgBean;

	@Reference(async = true)
	private IAuthorityAdminProvider adminProvider;

	@Bean
	public UserCacheHelper userCacheHelper(){
		return new UserCacheHelper(adminProvider);
	}

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) throws Exception {
	if(cfgBean == null || CollectionUtils.isEmpty(cfgBean.getAuthPath())) {
	    log.error("---------------------------------------------------------------------");
	    log.error("oauth2 资源服务需要认证的路径未配制,至少配制一个!");
	    log.error("---------------------------------------------------------------------");
	    System.exit(1); 
	}
	SecurityAuthenticationEntryPoint xx = new SecurityAuthenticationEntryPoint();
	http.exceptionHandling()
	.accessDeniedHandler(xx)
	.authenticationEntryPoint(xx)
	.and()
	.csrf().disable();
	//下面配制必须认证过后才可以访问的url
	for(String path : cfgBean.getAuthPath()) {
	    http.authorizeExchange().pathMatchers(path).authenticated();
	}
	http.authorizeExchange().anyExchange().permitAll();
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
