package top.klw8.alita.demo.cfg;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import top.klw8.alita.demo.oauth2.provider.SMSCodeLoginTokenGranter;
import top.klw8.alita.starter.authorization.cfg.IExtTokenGranters;

import java.util.ArrayList;
import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ExtTokenGrantersImpl
 * @Description: 扩展登录方式配制实现
 * @date 2019/8/20 11:17
 */
public class ExtTokenGrantersImpl implements IExtTokenGranters {
    @Override
    public List<TokenGranter> extTokenGranterList(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, UserDetailsService userService) {
        List<TokenGranter> result = new ArrayList<>(1);
        result.add(new SMSCodeLoginTokenGranter(tokenServices, clientDetailsService, requestFactory, userService));
        return result;
    }
}
