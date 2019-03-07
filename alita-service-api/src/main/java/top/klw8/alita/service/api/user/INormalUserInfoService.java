package top.klw8.alita.service.api.user;


import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.entitys.user.NormalUserInfo;
import top.klw8.alita.service.base.api.IBaseService;

/**
 * @ClassName: IServiceTypeService
 * @Description: 前台用户(客户)信息 的 Service
 * @author klw
 * @date 2019年1月30日 上午11:20:01
 */
public interface INormalUserInfoService extends IBaseService<NormalUserInfo> {

    /**
     * @Title: findByAccountId
     * @author klw
     * @Description: 根据帐号表ID查找客户
     * @param accountId
     * @return
     */
    public NormalUserInfo findByAccountId(Long accountId);
    
    /**
     * @Title: addSaveNormalUser
     * @author klw
     * @Description: 新增客户
     * @param account
     * @param user
     * @return
     */
    public NormalUserInfo addSaveNormalUser(AlitaUserAccount account, NormalUserInfo user);
    
}
