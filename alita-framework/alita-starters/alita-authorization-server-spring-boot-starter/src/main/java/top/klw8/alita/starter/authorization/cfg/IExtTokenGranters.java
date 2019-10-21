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
