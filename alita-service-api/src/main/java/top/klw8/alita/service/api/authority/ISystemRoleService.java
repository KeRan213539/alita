package top.klw8.alita.service.api.authority;

import java.util.List;

import org.bson.types.ObjectId;

import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.service.base.api.IBaseService;

/**
 * @ClassName: ISystemRoleService
 * @Description: 系统角色Service
 * @author klw
 * @date 2018年11月28日 下午3:48:49
 */
public interface ISystemRoleService extends IBaseService<SystemRole> {
    
    /**
     * @Title: addAuthority2Role
     * @author klw
     * @Description: 添加权限到角色中
     * @param roleId
     * @param au
     * @return
     */
    Integer addAuthority2Role(ObjectId roleId, SystemAuthoritys au);
    
    /**
     * @Title: removeAuthorityFromRole
     * @author klw
     * @Description: 从角色中删除指定权限
     * @param roleId
     * @param au
     * @return
     */
    Integer removeAuthorityFromRole(ObjectId roleId, SystemAuthoritys au);
    
    /**
     * @Title: updateAuthority2Role
     * @author klw
     * @Description: 使用传入的权限List替换角色中的权限
     * @param roleId
     * @param auList
     * @return
     */
    Integer replaceAuthority2Role(ObjectId roleId, List<SystemAuthoritys> auList);
    
}
