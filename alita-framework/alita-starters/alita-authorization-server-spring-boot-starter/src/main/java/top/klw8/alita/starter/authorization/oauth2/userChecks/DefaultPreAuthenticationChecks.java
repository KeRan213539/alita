package top.klw8.alita.starter.authorization.oauth2.userChecks;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * @author klw
 * @ClassName: DefaultPreAuthenticationChecks
 * @Description: 检查用户状态
 * @date 2018年11月22日 上午11:05:54
 */
public class DefaultPreAuthenticationChecks implements UserDetailsChecker {

    @Override
    public void check(UserDetails user) {
        if (!user.isAccountNonLocked()) {

            throw new LockedException("用户帐户被锁定");
        }

        if (!user.isEnabled()) {

            throw new DisabledException("用户已禁用");
        }

        if (!user.isAccountNonExpired()) {

            throw new AccountExpiredException("用户帐户已过期");
        }
    }
}
