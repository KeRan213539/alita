package top.klw8.alita.service.authority;

import com.baomidou.mybatisplus.extension.service.IService;
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
     * @param roleId
     * @return
     */
    int addRole2User(String userId, String roleId);
    
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
     * @author klw(213539@qq.com)
     * @Description: 查询用户拥有的全部角色ID
     * @Date 2020/7/17 16:38
     * @param: userId
     * @param: appTag
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemRole>
     */
    List<SystemRole> getUserAllRoles(String userId, String appTag);

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据传入的roleId查找拥有这个角色的用户
     * @Date 2019/10/19 16:41
     * @param: roleId
     * @return java.util.List<top.klw8.alita.entitys.user.AlitaUserAccount>
     */
    List<AlitaUserAccount> getUserByRoleId(String roleId);

}
