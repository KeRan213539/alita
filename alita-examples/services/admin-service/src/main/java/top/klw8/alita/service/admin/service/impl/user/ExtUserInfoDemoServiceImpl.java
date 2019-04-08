package top.klw8.alita.service.admin.service.impl.user;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.apache.dubbo.config.annotation.Service;
import org.bson.types.ObjectId;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.entitys.user.ExtUserInfoDemo;
import top.klw8.alita.service.admin.dao.IAlitaUserDao;
import top.klw8.alita.service.admin.dao.user.IExtUserInfoDemoDao;
import top.klw8.alita.service.api.user.IExtUserInfoDemoService;
import top.klw8.alita.starter.service.BaseServiceImpl;

/**
 * @ClassName: ExtUserInfoDemoServiceImpl
 * @Description: 用户扩展信息demo 的 Service
 * @author klw
 * @date 2019-01-30 11:39:45
 */
@Slf4j
@Service(async=true)
public class ExtUserInfoDemoServiceImpl extends BaseServiceImpl<ExtUserInfoDemo> implements IExtUserInfoDemoService {
    
    private IExtUserInfoDemoDao dao;
    
    @Autowired
    private IAlitaUserDao accountDao;
    
    public ExtUserInfoDemoServiceImpl(@Autowired IExtUserInfoDemoDao dao) {
	super(dao);
	this.dao = dao;
    }
    
    @Override
    public ExtUserInfoDemo findByAccountId(ObjectId accountId) {
	AlitaUserAccount account = new AlitaUserAccount();
	ExtUserInfoDemo query = new ExtUserInfoDemo();
	account.setId(accountId);
	query.setAccountInfo(account);
	Mono<List<ExtUserInfoDemo>> monoList = dao.findByEntityWithRefQuery(query, null);
	return asyncSendData(monoList.flatMap(list -> {
	    if (list.size() > 0) {
		return Mono.just(list.get(0));
	    }
	    return Mono.empty();
	}));
    }
    
    @Override
    public ExtUserInfoDemo addUser(AlitaUserAccount account, ExtUserInfoDemo user) {
	user.setId(new ObjectId());
	account.setId(new ObjectId());
	account.setUserTypeId(user.getId());
	BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
	account.setUserPwd(pwdEncoder.encode(account.getUserPwd()));
	accountDao.save(account);
	user.setAccountInfo(account);
	return asyncSendData(dao.save(user));
    }

}
