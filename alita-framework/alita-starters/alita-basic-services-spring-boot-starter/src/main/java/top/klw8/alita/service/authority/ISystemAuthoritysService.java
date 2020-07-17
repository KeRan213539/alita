package top.klw8.alita.service.authority;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.klw8.alita.entitys.authority.SystemAuthoritys;

import java.util.List;

/**
 * @author klw
 * @ClassName: ISystemAuthoritysService
 * @Description: 系统权限Service
 * @date 2018年11月28日 下午3:48:18
 */
public interface ISystemAuthoritysService extends IService<SystemAuthoritys> {

    /**
     * @param action
     * @return
     * @Title: findByAuAction
     * @author klw
     * @Description: 根据authorityAction 查找
     */
    SystemAuthoritys findByAuAction(String action);

    /**
     * @return int
     * @author klw(213539 @ qq.com)
     * @Description: 移除指定权限与所有角色的关联
     * @Date 2019/10/23 15:59
     * @param: auId
     */
    int removeAuthorityFromRole(String auId);

    /**
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemAuthoritys>
     * @author xp
     * @Description: 查询菜单权限列表
     * @Date 2019/12/26 15:55
     * @param: authorityName
     * @param: authorityType
     */
    IPage<SystemAuthoritys> selectSystemAuthoritysList(Page page, String authorityName,
                                                       String authorityType, String authorityAction,
                                                       String catlogName, String appTag);

    /**
     * @author klw(213539@qq.com)
     * @Description: 查询全部权限, 包含目录信息
     * @Date 2020/7/17 14:46
     * @param: appTag
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemAuthoritys>
     */
    List<SystemAuthoritys> selectAllSystemAuthoritysWithCatlog(String appTag);

}
