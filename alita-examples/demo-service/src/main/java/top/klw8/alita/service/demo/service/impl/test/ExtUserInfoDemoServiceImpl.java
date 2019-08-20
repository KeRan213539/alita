package top.klw8.alita.service.demo.service.impl.test;


import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import reactor.core.publisher.Mono;
import top.klw8.alita.entitys.demo.mongo.ExtUserInfoDemo;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.demo.IExtUserInfoDemoDao;
import top.klw8.alita.service.api.mybatis.IExtUserInfoDemoService;
import top.klw8.alita.starter.service.BaseServiceImpl;

import java.util.List;

/**
 * @author klw
 * @ClassName: ExtUserInfoDemoServiceImpl
 * @Description: 用户扩展信息demo 的 Service
 * @date 2019-01-30 11:39:45
 */
@Slf4j
@Service(async = true)
public class ExtUserInfoDemoServiceImpl extends BaseServiceImpl<ExtUserInfoDemo> implements IExtUserInfoDemoService {

    private IExtUserInfoDemoDao dao;

//    @Autowired
//    private IAlitaUserService accountService;

    public ExtUserInfoDemoServiceImpl(@Autowired IExtUserInfoDemoDao dao) {
        super(dao);
        this.dao = dao;
    }

    @Override
    public ExtUserInfoDemo findByAccountId(String accountId) {
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
        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
        account.setUserPwd(pwdEncoder.encode(account.getUserPwd()));
//        accountService.save(account);
        user.setAccountInfo(account);
        return asyncSendData(dao.save(user));
    }

}
