package top.klw8.alita.starter.authorization.oauth2.userChecks;

import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * @ClassName: DefaultPostAuthenticationChecks
 * @Description: 检查用户密码是否过期
 * @author klw
 * @date 2018年11月22日 上午11:09:58
 */
public class DefaultPostAuthenticationChecks implements UserDetailsChecker {

    @Override
    public void check(UserDetails user) {
	if (!user.isCredentialsNonExpired()) {
	    throw new CredentialsExpiredException("用户密码已过期");
	}
    }

}
