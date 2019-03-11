package top.klw8.alita.service.admin.service.impl.user;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.apache.dubbo.config.annotation.Service;
import org.bson.types.ObjectId;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.entitys.user.StaffInfo;
import top.klw8.alita.entitys.user.enums.UserTypeEnum;
import top.klw8.alita.service.admin.dao.IAlitaUserDao;
import top.klw8.alita.service.admin.dao.user.IStaffInfoDao;
import top.klw8.alita.service.api.user.IStaffInfoService;
import top.klw8.alita.starter.service.BaseServiceImpl;

/**
 * @ClassName: StaffInfoServiceImpl
 * @Description: 前台用户(客户)信息 的 Service
 * @author klw
 * @date 2019-01-30 11:39:45
 */
@Slf4j
@Service(async=true)
public class StaffInfoServiceImpl extends BaseServiceImpl<StaffInfo> implements IStaffInfoService {
    
    private IStaffInfoDao dao;
    
    @Autowired
    private IAlitaUserDao accountDao;
    
    public StaffInfoServiceImpl(@Autowired IStaffInfoDao dao) {
	super(dao);
	this.dao = dao;
    }
    
    @Override
    public StaffInfo findByAccountId(ObjectId accountId) {
	AlitaUserAccount account = new AlitaUserAccount();
	StaffInfo query = new StaffInfo();
	account.setId(accountId);
	query.setAccountInfo(account);
	Mono<List<StaffInfo>> monoList = dao.findByEntityWithRefQuery(query, null);
	return asyncSendData(monoList.flatMap(list -> {
	    if (list.size() > 0) {
		return Mono.just(list.get(0));
	    }
	    return Mono.empty();
	}));
    }
    
    @Override
    public StaffInfo addSaveStaff(AlitaUserAccount account, StaffInfo user) {
	user.setId(new ObjectId());
	account.setId(new ObjectId());
	account.setUserType(UserTypeEnum.ADMIN_USER);
	account.setUserTypeId(user.getId());
	BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
	account.setUserPwd(pwdEncoder.encode(account.getUserPwd()));
	accountDao.save(account);
	user.setAccountInfo(account);
	return asyncSendData(dao.save(user));
    }


}
