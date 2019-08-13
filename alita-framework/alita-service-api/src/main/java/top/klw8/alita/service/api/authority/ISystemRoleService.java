package top.klw8.alita.service.api.authority;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.service.api.IMybatisBaseService;

/**
 * @ClassName: ISystemRoleService
 * @Description: 系统角色Service
 * @author klw
 * @date 2018年11月28日 下午3:48:49
 */
public interface ISystemRoleService extends IMybatisBaseService<SystemRole> {
    
    /**
     * @Title: addAuthority2Role
     * @author klw
     * @Description: 添加权限到角色中
     * @param roleId
     * @param au
     * @return
     */
    CompletableFuture<Integer> addAuthority2Role(String roleId, SystemAuthoritys au);
    
    /**
     * @Title: removeAuthorityFromRole
     * @author klw
     * @Description: 从角色中删除指定权限
     * @param roleId
     * @param au
     * @return
     */
    CompletableFuture<Integer> removeAuthorityFromRole(String roleId, SystemAuthoritys au);
    
    /**
     * @Title: updateAuthority2Role
     * @author klw
     * @Description: 使用传入的权限List替换角色中的权限
     * @param roleId
     * @param auList
     * @return
     */
    CompletableFuture<Integer> replaceAuthority2Role(String roleId, List<SystemAuthoritys> auList);
    
}
