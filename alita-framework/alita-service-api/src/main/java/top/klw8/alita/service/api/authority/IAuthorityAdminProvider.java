package top.klw8.alita.service.api.authority;

import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.service.result.JsonResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: IAuthorityAdminProvider
 * @Description: 权限管理相关dubbo服务提供者
 * @date 2019/8/14 9:08
 */
public interface IAuthorityAdminProvider {

    /**
     * @author klw(213539@qq.com)
     * @Description: 新增权限
     * @Date 2019/8/14 9:39
     * @param: au
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addAuthority(SystemAuthoritys au);

    /**
     * @author klw(213539@qq.com)
     * @Description: 新增权限目录
     * @Date 2019/8/14 11:08
     * @param: catlog
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addCatlog(SystemAuthoritysCatlog catlog);


    /**
     * @author klw(213539@qq.com)
     * @Description: 向角色中添加权限
     * @Date 2019/8/14 11:18
     * @param: roleId
     * @param: auId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addRoleAu(String roleId, String auId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 向用户中添加角色
     * @Date 2019/8/14 13:54
     * @param: userId
     * @param: roleId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addUserRole(String userId, String roleId);

    /**
     *
     * @Author zhanglei
     * @Description 刷新缓存中的权限
     * @Date 15:42 2019-08-14
     * @param userId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     **/
    CompletableFuture<JsonResult> refreshUserAuthoritys(String userId);


    //===================================================================
    /**
     * @author klw(213539@qq.com)
     * @Description: 添加角色
     * @Date 2019/8/14 11:15
     * @param: role
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addSysRole(SystemRole role);

    /**
     * @author klw(213539@qq.com)
     * @Description: 角色列表
     * @Date 2019/10/17 16:41
     * @param: roleName
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> roleList(String roleName);

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取全部权限,并根据传入的角色ID标识出该角色拥有的权限
     * @Date 2019/10/17 17:12
     * @param: roleId
     * @param: userId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> markRoleAuthoritys(String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 保存角色的权限(替换原有权限)
     * @Date 2019/10/18 17:38
     * @param: roleId
     * @param: auIds
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> saveRoleAuthoritys(String roleId, List<String> auIds);

    /**
     * @author klw(213539@qq.com)
     * @Description: 保存角色(无ID则添加,有则修改)
     * @Date 2019/10/18 17:39
     * @param: role
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> saveUser(SystemRole role);

}
