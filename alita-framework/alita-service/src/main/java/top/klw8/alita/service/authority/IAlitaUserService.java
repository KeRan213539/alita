package top.klw8.alita.service.authority;

import com.baomidou.mybatisplus.extension.service.IService;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.user.AlitaUserAccount;

import java.util.List;

/**
 * @ClassName: IAlitaUserService
 * @Description: 用户服务
 * @author klw
 * @date 2018年11月15日 下午4:15:57
 */
public interface IAlitaUserService extends IService<AlitaUserAccount> {

    /**
     * @Title: addRole2User
     * @author klw
     * @Description: 添加角色到用户中
     * @param userId
     * @param role
     * @return
     */
    int addRole2User(String userId, SystemRole role);
    
    /**
     * @Title: removeRoleFromUser
     * @author klw
     * @Description: 从用户中删除指定角色
     * @param userId
     * @param role
     * @return
     */
    int removeRoleFromUser(String userId, SystemRole role);
    
    /**
     * @Title: replaceRole2User
     * @author klw
     * @Description: 使用传入的角色List替换用户中的角色
     * @param userId
     * @param roleList
     * @return
     */
    int replaceRole2User(String userId, List<SystemRole> roleList);

    /**
     *
     * @Author zhanglei
     * @Description 查询用户拥有的全部角色ID
     * @Date 15:28 2019-08-15
     * @param: userId
     * @return java.util.List<java.lang.String>
     **/
    List<SystemRole> getUserAllRoles(String userId);

    /**
     *
     * @Author zhanglei
     * @Description 查询角色拥有的权限信息
     * @Date 16:08 2019-08-15
     * @param: roleId
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemAuthoritys>
     **/
    List<SystemAuthoritys> getRoleAllAuthoritys(String roleId);

}
