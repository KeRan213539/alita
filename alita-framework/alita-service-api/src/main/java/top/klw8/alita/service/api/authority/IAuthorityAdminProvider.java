package top.klw8.alita.service.api.authority;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.entitys.authority.SystemDataSecured;
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
    CompletableFuture<JsonResult> roleList(String roleName, Page<SystemRole> page);

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
     * 如果传入的角色中包含权限,会同时保存这些权限.
     * 如果 copyAuFromRoleId 有值,并且目标角色存在,会复制该角色的权限并保存到新角色中
     * 如果 copyAuFromRoleId有值并且要保存的角色中也有权限,要保存的角色中的权限会被 copyAuFromRoleId 覆盖
     * @Date 2019/10/18 17:39
     * @param: role
     * @param: copyAuFromRoleId 角色ID,如果有值,并且目标角色存在,会复制该角色的权限并保存到新角色中
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> saveRole(SystemRole role, String copyAuFromRoleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 删除角色(只能删除没有关联用户的角色,删除时会删除角色中配制的权限)
     * @Date 2019/10/19 14:46
     * @param: roleId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> delRole(String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 角色信息
     * @Date 2019/10/31 11:47
     * @param: roleId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> roleInfo(String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 权限目录列表,分页,可根据目录名称查询
     * @Date 2019/10/22 9:23
     * @param: catlogName
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> catlogList(String catlogName, Page<SystemAuthoritysCatlog> page);

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取全部权限目录,不分页
     * @Date 2019/10/22 14:05
     * @param: 
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> catlogAll();

    /**
     * @author klw(213539@qq.com)
     * @Description: 新增/修改权限目录的保存
     * @Date 2019/10/22 11:21
     * @param: catlog
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> saveCatlog(SystemAuthoritysCatlog catlog);

    /**
     * @author klw(213539@qq.com)
     * @Description: 删除权限目录(没有权限关联到它才能删除)
     * @Date 2019/10/22 11:23
     * @param: catlogId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> delCatlog(String catlogId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 权限目录详情
     * @Date 2019/10/31 11:52
     * @param: catlogId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> catlogInfo(String catlogId);


    /**
     * @author klw(213539@qq.com)
     * @Description: 权限列表,分页,可根据权限名称查询
     * @Date 2019/10/22 14:08
     * @param: auName
     * @param: page
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> authoritysMenuList(String auName, Page<SystemAuthoritys> page);

    /**
     * @author klw(213539@qq.com)
     * @Description: 新增/修改权限的保存
     * @Date 2019/10/22 11:21
     * @param: catlog
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> saveAuthority(SystemAuthoritys au);

    /**
     * @author klw(213539@qq.com)
     * @Description: 删除权限(同时删除角色的关联)
     * @Date 2019/10/22 11:23
     * @param: catlogId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> delAuthority(String auId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 权限详情
     * @Date 2019/10/31 14:46
     * @param: auId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> auInfo(String auId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据权限路径获取该权限下的数据权限(包括全局数据权限)
     * @Date 2020/5/18 15:22
     * @param: auPath
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> dataSecuredsByAuthorityAction(String httpMethod, String auAction);

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取全部权限,包含目录信息,并按目录分组
     * @Date 2020/5/19 15:32
     * @param:
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> allAuthoritysWithCatlog();


    /**
     * @author klw(213539@qq.com)
     * @Description: 数据权限列表,分页,可根据资源名称查询
     * @Date 2020/5/20 11:21
     * @param: resource
     * @param: page
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> dataSecuredList(String resource, Page<SystemDataSecured> page);

    /**
     * @author klw(213539@qq.com)
     * @Description: 新增/修改数据权限的保存
     * @Date 2020/5/20 11:22
     * @param: catlog
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> saveDataSecured(SystemDataSecured ds);

    /**
     * @author klw(213539@qq.com)
     * @Description: 删除数据权限(没有角色关联它才能删除)
     * @Date 2020/5/20 11:23
     * @param: dsId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> delDataSecured(String dsId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 数据权限详情
     * @Date 2020/5/20 11:23
     * @param: dsId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> dataSecuredInfo(String dsId);

}
