package top.klw8.alita.service.api.user;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.IMybatisBaseService;

/**
 * @ClassName: IAlitaUserService
 * @Description: 用户服务
 * @author klw
 * @date 2018年11月15日 下午4:15:57
 */
public interface IAlitaUserService extends IMybatisBaseService<AlitaUserAccount> {

    /**
     * @Title: addRole2User
     * @author klw
     * @Description: 添加角色到用户中
     * @param userId
     * @param role
     * @return
     */
    CompletableFuture<Integer> addRole2User(String userId, SystemRole role);
    
    /**
     * @Title: removeRoleFromUser
     * @author klw
     * @Description: 从用户中删除指定角色
     * @param userId
     * @param role
     * @return
     */
    CompletableFuture<Integer> removeRoleFromUser(String userId, SystemRole role);
    
    /**
     * @Title: replaceRole2User
     * @author klw
     * @Description: 使用传入的角色List替换用户中的角色
     * @param userId
     * @param roleList
     * @return
     */
    CompletableFuture<Integer> replaceRole2User(String userId, List<SystemRole> roleList);
    
}
