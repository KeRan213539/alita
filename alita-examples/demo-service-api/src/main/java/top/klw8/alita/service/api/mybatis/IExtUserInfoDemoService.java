package top.klw8.alita.service.api.mybatis;


import top.klw8.alita.entitys.demo.mongo.ExtUserInfoDemo;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.base.mongo.api.IMongoBaseService;

/**
 * @ClassName: IExtUserInfoDemoService
 * @Description: 用户扩展信息demo 的 Service
 * @author klw
 * @date 2019年1月30日 上午11:20:01
 */
public interface IExtUserInfoDemoService extends IMongoBaseService<ExtUserInfoDemo> {

    /**
     * @Title: findByAccountId
     * @author klw
     * @Description: 根据帐号表ID查找扩展用户信息
     * @param accountId
     * @return
     */
    public ExtUserInfoDemo findByAccountId(String accountId);
    
    /**
     * @Title: addExtUserInfoDemo
     * @author klw
     * @Description: 新增用户(账户和扩展信息)
     * @param account
     * @param user
     * @return
     */
    public ExtUserInfoDemo addUser(AlitaUserAccount account, ExtUserInfoDemo user);
    
}
