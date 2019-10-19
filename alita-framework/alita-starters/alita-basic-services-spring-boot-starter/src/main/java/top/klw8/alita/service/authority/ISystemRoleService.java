package top.klw8.alita.service.authority;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemRole;

/**
 * @ClassName: ISystemRoleService
 * @Description: 系统角色Service
 * @author klw
 * @date 2018年11月28日 下午3:48:49
 */
public interface ISystemRoleService extends IService<SystemRole> {
    
    /**
     * @Title: addAuthority2Role
     * @author klw
     * @Description: 添加权限到角色中
     * @param roleId
     * @param au
     * @return
     */
    int addAuthority2Role(String roleId, SystemAuthoritys au);
    
    /**
     * @Title: removeAuthorityFromRole
     * @author klw
     * @Description: 从角色中删除指定权限
     * @param roleId
     * @param au
     * @return
     */
    int removeAuthorityFromRole(String roleId, SystemAuthoritys au);
    
    /**
     * @Title: updateAuthority2Role
     * @author klw
     * @Description: 使用传入的权限List替换角色中的权限
     * @param roleId
     * @param auIds
     * @return
     */
    int replaceAuthority2Role(String roleId, List<String> auIds);
    
}
