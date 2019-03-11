package top.klw8.alita.service.api.user;


import org.bson.types.ObjectId;

import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.entitys.user.StaffInfo;
import top.klw8.alita.service.base.api.IBaseService;

/**
 * @ClassName: IServiceTypeService
 * @Description: 员工信息 的 Service
 * @author klw
 * @date 2019年1月30日 上午11:20:42
 */
public interface IStaffInfoService extends IBaseService<StaffInfo> {

    /**
     * @Title: findByAccountId
     * @author klw
     * @Description: 根据帐号表ID查找员工
     * @param accountId
     * @return
     */
    StaffInfo findByAccountId(ObjectId accountId);
    
    /**
     * @Title: addSaveStaff
     * @author klw
     * @Description: 保存添加的员工
     * @param account
     * @param user
     * @return
     */
    StaffInfo addSaveStaff(AlitaUserAccount account, StaffInfo user);
    
}
