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
import top.klw8.alita.service.result.CallBackMessage;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.utils.EntityUtil;
import top.klw8.alita.starter.service.common.ServiceContext;
import top.klw8.alita.utils.UUIDUtil;

import java.util.concurrent.CompletableFuture;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AuthorityAdminProviderImpl
 * @Description: 劝降相关dubbo服务提供者
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
    public CompletableFuture<JsonResult> addAuthority(SystemAuthoritys auvo) {
        return CompletableFuture.supplyAsync(() -> {
            String catlogId = auvo.getCatlogId();
            SystemAuthoritysCatlog catlog = catlogService.getById(catlogId);
            if (catlog == null) {
                return JsonResult.sendFailedResult("权限目录不存在【" + catlogId + "】", null);
            }
            SystemAuthoritys au = new SystemAuthoritys();
            BeanUtils.copyProperties(auvo, au);
            au.setId(UUIDUtil.getRandomUUIDString());
            au.setCatlog(catlog);
            boolean isSaved = auService.save(au);
            if (isSaved) {
                int savedCount = catlogService.addAuthority2Catlog(catlog.getId(), au);
                if (savedCount > 0) {
                    return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, "保存成功");
                }
                return JsonResult.sendFailedResult("保存失败", null);
            }
            return JsonResult.sendFailedResult("保存失败", null);
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addCatlog(SystemAuthoritysCatlog catlog) {
        return CompletableFuture.supplyAsync(() -> {
            if (catlogService.save(catlog)) {
                return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, "保存成功");
            } else {
                return JsonResult.sendFailedResult("保存失败", null);
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addSysRole(SystemRole role) {
        return CompletableFuture.supplyAsync(() -> {
            if (roleService.save(role)) {
                return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, "保存成功");
            } else {
                return JsonResult.sendFailedResult("保存失败", null);
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addRoleAu(String roleId, String auId) {
        return CompletableFuture.supplyAsync(() -> {
            SystemRole role = roleService.getById(roleId);

            if (EntityUtil.isEntityEmpty(role)) {
                return JsonResult.sendFailedResult("角色不存在", null);
            }
            SystemAuthoritys au = auService.getById(auId);
            if (EntityUtil.isEntityEmpty(au)) {
                return JsonResult.sendFailedResult("权限不存在", null);
            }
            int saveResult = roleService.addAuthority2Role(role.getId(), au);
            if (saveResult > 0) {
                return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, "添加成功");
            } else {
                return JsonResult.sendFailedResult("添加失败", null);
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addUserRole(String userId, String roleId) {
        return CompletableFuture.supplyAsync(() -> {
            AlitaUserAccount user = userService.getById(userId);
            if (EntityUtil.isEntityEmpty(user)) {
                return JsonResult.sendFailedResult("用户不存在", null);
            }
            SystemRole role = roleService.getById(roleId);
            if (EntityUtil.isEntityEmpty(role)) {
                return JsonResult.sendFailedResult("角色不存在", null);
            }
            int saveResult = userService.addRole2User(user.getId(), role);
            if (saveResult > 0) {
                return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, "添加成功");
            } else {
                return JsonResult.sendFailedResult("添加失败", null);
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> refreshAdminAuthoritys(String userId) {
        //查询管理员用户,把权限查出来
        // TODO 下面这里需要把角色和权限都关联查询出来
        return CompletableFuture.supplyAsync(() -> {
            AlitaUserAccount sysUser = userService.getById(userId);
            if (EntityUtil.isEntityEmpty(sysUser)) {
                return JsonResult.sendFailedResult("管理会员用户不存在", null);
            }
            userCacheHelper.putUserInfo2Cache(sysUser);
            return JsonResult.sendSuccessfulResult("刷新完成", null);
        }, ServiceContext.executor);
    }
}
