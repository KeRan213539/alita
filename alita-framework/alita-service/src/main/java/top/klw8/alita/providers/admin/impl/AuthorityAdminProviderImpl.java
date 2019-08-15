package top.klw8.alita.providers.admin.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.helper.UserCacheHelper;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;
import top.klw8.alita.service.authority.IAlitaUserService;
import top.klw8.alita.service.authority.ISystemAuthoritysCatlogService;
import top.klw8.alita.service.authority.ISystemAuthoritysService;
import top.klw8.alita.service.authority.ISystemRoleService;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.result.code.AuthorityResultCodeEnum;
import top.klw8.alita.service.utils.EntityUtil;
import top.klw8.alita.starter.service.common.ServiceContext;
import top.klw8.alita.utils.UUIDUtil;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AuthorityAdminProviderImpl
 * @Description: 权限相关dubbo服务提供者
 * @date 2019/8/14 9:33
 */
@Slf4j
@Service(async = true)
public class AuthorityAdminProviderImpl implements IAuthorityAdminProvider {

    @Autowired
    private ISystemAuthoritysService auService;

    @Autowired
    private ISystemAuthoritysCatlogService catlogService;

    @Autowired
    private ISystemRoleService roleService;

    @Autowired
    private IAlitaUserService userService;

    @Autowired
    private UserCacheHelper userCacheHelper;

    @Override
    public CompletableFuture<JsonResult> addAuthority(String catlogId, SystemAuthoritys auvo) {
        return CompletableFuture.supplyAsync(() -> {
            SystemAuthoritysCatlog catlog = catlogService.getById(catlogId);
            if (catlog == null) {
                return JsonResult.sendFailedResult(AuthorityResultCodeEnum.CATLOG_NOT_EXIST, "权限目录不存在【" + catlogId + "】");
            }
            SystemAuthoritys au = new SystemAuthoritys();
            BeanUtils.copyProperties(auvo, au);
            au.setId(UUIDUtil.getRandomUUIDString());
            au.setCatlog(catlog);
            boolean isSaved = auService.save(au);
            if (isSaved) {
                int savedCount = catlogService.addAuthority2Catlog(catlog.getId(), au);
                if (savedCount > 0) {
                    return JsonResult.sendSuccessfulResult();
                }
                return JsonResult.sendFailedResult("保存失败");
            }
            return JsonResult.sendFailedResult("保存失败");
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addCatlog(SystemAuthoritysCatlog catlog) {
        return CompletableFuture.supplyAsync(() -> {
            if (catlogService.save(catlog)) {
                return JsonResult.sendSuccessfulResult();
            } else {
                return JsonResult.sendFailedResult("保存失败");
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addSysRole(SystemRole role) {
        return CompletableFuture.supplyAsync(() -> {
            if (roleService.save(role)) {
                return JsonResult.sendSuccessfulResult();
            } else {
                return JsonResult.sendFailedResult("保存失败");
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addRoleAu(String roleId, String auId) {
        return CompletableFuture.supplyAsync(() -> {
            SystemRole role = roleService.getById(roleId);

            if (EntityUtil.isEntityEmpty(role)) {
                return JsonResult.sendFailedResult(AuthorityResultCodeEnum.ROLE_NOT_EXIST);
            }
            SystemAuthoritys au = auService.getById(auId);
            if (EntityUtil.isEntityEmpty(au)) {
                return JsonResult.sendFailedResult(AuthorityResultCodeEnum.AUTHORITY_NOT_EXIST);
            }
            int saveResult = roleService.addAuthority2Role(role.getId(), au);
            if (saveResult > 0) {
                return JsonResult.sendSuccessfulResult();
            } else {
                return JsonResult.sendFailedResult("添加失败");
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addUserRole(String userId, String roleId) {
        return CompletableFuture.supplyAsync(() -> {
            AlitaUserAccount user = userService.getById(userId);
            if (EntityUtil.isEntityEmpty(user)) {
                return JsonResult.sendFailedResult(AuthorityResultCodeEnum.USER_NOT_EXIST);
            }
            SystemRole role = roleService.getById(roleId);
            if (EntityUtil.isEntityEmpty(role)) {
                return JsonResult.sendFailedResult(AuthorityResultCodeEnum.ROLE_NOT_EXIST);
            }
            int saveResult = userService.addRole2User(user.getId(), role);
            if (saveResult > 0) {
                return JsonResult.sendSuccessfulResult();
            } else {
                return JsonResult.sendFailedResult("添加失败");
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> refreshAdminAuthoritys(String userId) {
        //查询管理员用户,把权限查出来
        return CompletableFuture.supplyAsync(() -> {
            // 根据用户ID查询到用户信息
            AlitaUserAccount sysUser = userService.getById(userId);
            // 根据用户ID查询用户角色
            List<SystemRole> userRoles = userService.getUserAllRoles(userId);
            // 根据用户角色查询角色对应的权限并更新到SystemRole实体中
            for (SystemRole role : userRoles) {
                List<SystemAuthoritys> authoritys = userService.getRoleAllAuthoritys(role.getId());
                role.setAuthorityList(authoritys);
            }
            // 更新用户角色权限到用户实体中
            sysUser.setUserRoles(userRoles);
            // 根据查询到的
            if (EntityUtil.isEntityEmpty(sysUser)) {
                return JsonResult.sendFailedResult(AuthorityResultCodeEnum.USER_NOT_EXIST);
            }
            userCacheHelper.putUserInfo2Cache(sysUser);
            return JsonResult.sendSuccessfulResult();
        }, ServiceContext.executor);
    }
}
