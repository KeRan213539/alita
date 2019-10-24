package top.klw8.alita.service.authority;

import com.baomidou.mybatisplus.extension.service.IService;
import top.klw8.alita.entitys.authority.SystemAuthoritys;

/**
 * @ClassName: ISystemAuthoritysService
 * @Description: 系统权限Service
 * @author klw
 * @date 2018年11月28日 下午3:48:18
 */
public interface ISystemAuthoritysService extends IService<SystemAuthoritys> {

    /**
     * @Title: findByAuAction
     * @author klw
     * @Description: 根据authorityAction 查找
     * @param action
     * @return
     */
    SystemAuthoritys findByAuAction(String action);

    /**
     * @author klw(213539@qq.com)
     * @Description: 移除指定权限与所有角色的关联
     * @Date 2019/10/23 15:59
     * @param: auId
     * @return int
     */
    int removeAuthorityFromRole(String auId);
    
}
