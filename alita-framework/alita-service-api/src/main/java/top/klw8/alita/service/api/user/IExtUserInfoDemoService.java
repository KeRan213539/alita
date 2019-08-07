package top.klw8.alita.service.api.user;


import org.bson.types.ObjectId;

import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.entitys.user.ExtUserInfoDemo;
import top.klw8.alita.service.base.api.IBaseService;

/**
 * @ClassName: IExtUserInfoDemoService
 * @Description: 用户扩展信息demo 的 Service
 * @author klw
 * @date 2019年1月30日 上午11:20:01
 */
public interface IExtUserInfoDemoService extends IBaseService<ExtUserInfoDemo> {

    /**
     * @Title: findByAccountId
     * @author klw
     * @Description: 根据帐号表ID查找扩展用户信息
     * @param accountId
     * @return
     */
    public ExtUserInfoDemo findByAccountId(ObjectId accountId);
    
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
