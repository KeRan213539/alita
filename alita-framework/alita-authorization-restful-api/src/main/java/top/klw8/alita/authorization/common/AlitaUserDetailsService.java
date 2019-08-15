package top.klw8.alita.authorization.common;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.extern.slf4j.Slf4j;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.service.utils.EntityUtil;

/**
 * @author klw
 * @ClassName: AlitaUserDetailsService
 * @Description: org.springframework.security.core.userdetails.UserDetailsService 的实现,根据用户名查找用户, 提供给 spring-security 使用
 * @date 2018年11月12日 下午5:20:38
 */
@Slf4j
public class AlitaUserDetailsService implements UserDetailsService {

    private IAlitaUserProvider userService;

    public AlitaUserDetailsService(IAlitaUserProvider userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CompletableFuture<AlitaUserAccount> userFuture =  userService.findUserByName(username);
		AlitaUserAccount user = null;
		try {
			user = userFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("", e);
		}
		if (EntityUtil.isEntityNotEmpty(user)) {
            return user;
        }
        throw new UsernameNotFoundException(username);
    }

}
