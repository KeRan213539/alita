package top.klw8.alita.authorization.common;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.dubbo.rpc.RpcContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.extern.slf4j.Slf4j;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.user.IAlitaUserService;

/**
 * @ClassName: AlitaUserDetailsService
 * @Description: org.springframework.security.core.userdetails.UserDetailsService 的实现,根据用户名查找用户, 提供给 spring-security 使用
 * @author klw
 * @date 2018年11月12日 下午5:20:38
 */
@Slf4j
public class AlitaUserDetailsService implements UserDetailsService {
    
    private IAlitaUserService userService;
    
    public AlitaUserDetailsService(IAlitaUserService userService) {
	this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	AlitaUserAccount queryBean = new AlitaUserAccount();
	queryBean.setUserPhoneNum(username);
	
	userService.findByEntity(queryBean, null, null, false);
	Future<List<AlitaUserAccount>> task = RpcContext.getContext().getFuture();
	List<AlitaUserAccount> userList = null;
	try {
	    userList = task.get();
	} catch (InterruptedException | ExecutionException e) {
	    e.printStackTrace();
	}
	if(userList != null && userList.size() > 0) {
	    return userList.get(0);
	}
	throw new UsernameNotFoundException(username);
    }

}
