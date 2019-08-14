package top.klw8.alita.service.api.authority;

import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.service.result.JsonResult;

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
     * @Description: 添加角色
     * @Date 2019/8/14 11:15
     * @param: role
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addSysRole(SystemRole role);

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

}
