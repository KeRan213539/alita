package top.klw8.alita.providers.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.entitys.authority.SystemDataSecured;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.helper.UserCacheHelper;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;
import top.klw8.alita.service.authority.IAlitaUserService;
import top.klw8.alita.service.authority.ISystemAuthoritysCatlogService;
import top.klw8.alita.service.authority.ISystemAuthoritysService;
import top.klw8.alita.service.authority.ISystemRoleService;
import top.klw8.alita.service.pojos.SystemAuthorityPojo;
import top.klw8.alita.service.pojos.SystemAuthorityCatlogPojo;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.result.code.AuthorityResultCodeEnum;
import top.klw8.alita.service.utils.EntityUtil;
import top.klw8.alita.starter.service.common.ServiceContext;
import top.klw8.alita.starter.service.common.ServiceUtil;
import top.klw8.alita.utils.UUIDUtil;

import java.util.*;
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
    public CompletableFuture<JsonResult> addAuthority(SystemAuthoritys au) {
        return CompletableFuture.supplyAsync(() -> {
            SystemAuthoritysCatlog catlog = catlogService.getById(au.getCatlogId());
            if (catlog == null) {
                return JsonResult.sendFailedResult(AuthorityResultCodeEnum.CATLOG_NOT_EXIST, "权限目录不存在【" + au.getCatlogId() + "】");
            }
            if(StringUtils.isBlank(au.getId())) {
                au.setId(UUIDUtil.getRandomUUIDString());
            }
            boolean isSaved = auService.save(au);
            if (isSaved) {
                return JsonResult.sendSuccessfulResult();
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
            int saveResult = userService.addRole2User(user.getId(), role.getId());
            if (saveResult > 0) {
                return JsonResult.sendSuccessfulResult();
            } else {
                return JsonResult.sendFailedResult("添加失败");
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> refreshUserAuthoritys(String userId) {
        //查询管理员用户,把权限查出来
        return CompletableFuture.supplyAsync(() -> {
            // 根据用户ID查询到用户信息
            AlitaUserAccount sysUser = userService.getById(userId);
            // 判断是否查询到用户
            if (EntityUtil.isEntityEmpty(sysUser)) {
                return JsonResult.sendFailedResult(AuthorityResultCodeEnum.USER_NOT_EXIST);
            } else {
                // 根据用户ID查询用户角色
                List<SystemRole> userRoles = userService.getUserAllRoles(userId);

                for (SystemRole role : userRoles) {
                    // 根据用户角色查询角色对应的权限并更新到SystemRole实体中
                    List<SystemAuthoritys> authoritys = roleService.getRoleAllAuthoritys(role.getId());
                    role.setAuthorityList(authoritys);

                    // 根据用户角色查询角色对应的数据权限并更新到SystemRole实体中
                    List<SystemDataSecured> dataSecureds = roleService.getRoleAllDataSecureds(role.getId());
                    role.setDataSecuredList(dataSecureds);
                }
                // 更新用户角色权限到用户实体中
                if (null != userRoles && !userRoles.isEmpty()) {
                    sysUser.setUserRoles(userRoles);
                }
                userCacheHelper.putUserInfo2Cache(sysUser);
                return JsonResult.sendSuccessfulResult();
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> roleList(String roleName, Page<SystemRole> page) {
        QueryWrapper<SystemRole> query = new QueryWrapper();
        if(StringUtils.isNotBlank(roleName)){
            query.like("role_name", roleName);
        }
        List<OrderItem> orders = new ArrayList<>(1);
        orders.add(OrderItem.desc("role_name"));
        page.setOrders(orders);
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(
                roleService.page(page, query)));
    }

    @Override
    public CompletableFuture<JsonResult> markRoleAuthoritys(String roleId) {
        List<SystemAuthoritys> roleAuList = null;
        if(StringUtils.isNotBlank(roleId)) {
            SystemRole role = roleService.getById(roleId);
            Assert.notNull(role, "该角色不存在!");
            //该角色拥有的权限
            roleAuList = roleService.getRoleAllAuthoritys(role.getId());
        }

        // 所有权限
        List<SystemAuthoritys> allAuList = auService.list();
        Map<String, SystemAuthorityCatlogPojo> catlogMap = new HashMap<>(16);
        for(SystemAuthoritys sysAu : allAuList){
            SystemAuthorityCatlogPojo catlogPojo = catlogMap.get(sysAu.getCatlogId());
            if (catlogPojo == null) {
                SystemAuthoritysCatlog catlog = catlogService.getById(sysAu.getCatlogId());
                catlogPojo = new SystemAuthorityCatlogPojo();
                BeanUtils.copyProperties(catlog, catlogPojo);
                catlogPojo.setAuthorityList(new ArrayList<>());
                catlogMap.put(catlogPojo.getId(), catlogPojo);
            }
            SystemAuthorityPojo auPojo = new SystemAuthorityPojo();
            BeanUtils.copyProperties(sysAu, auPojo);
            // 查找当前角色是否有该权限
            if(CollectionUtils.isNotEmpty(roleAuList)) {
                Iterator<SystemAuthoritys> roleAuIt = roleAuList.iterator();
                while (roleAuIt.hasNext()) {
                    SystemAuthoritys roleAu = roleAuIt.next();
                    if (roleAu.getId().equals(auPojo.getId())) {
                        auPojo.setCurrUserHas(true);
                        roleAuIt.remove();
                    }
                }
            }
            catlogPojo.getAuthorityList().add(auPojo);
        }
        List<SystemAuthorityCatlogPojo> catlogPojoList = new ArrayList<>(catlogMap.values());
        Collections.sort(catlogPojoList);
        catlogPojoList.stream().forEach(systemAuthorityCatlogPojo -> Collections.sort(systemAuthorityCatlogPojo.getAuthorityList()));
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(catlogPojoList));
    }

    @Override
    public CompletableFuture<JsonResult> saveRoleAuthoritys(String roleId, List<String> auIds) {
        if(roleService.getById(roleId) == null){
            return ServiceUtil.buildFuture(JsonResult.sendBadParameterResult("角色不存在"));
        }
        for(String auId : auIds){
            if(auService.getById(auId) == null){
                return ServiceUtil.buildFuture(JsonResult.sendBadParameterResult("权限不存在"));
            }
        }
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(roleService.replaceAuthority2Role(roleId, auIds)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<JsonResult> saveRole(SystemRole role, String copyAuFromRoleId) {
        Assert.notNull(role, "要保存的角色不能为 null !!!");
        if(StringUtils.isNotBlank(role.getId())){
            roleService.updateById(role);
        } else {
            if(CollectionUtils.isNotEmpty(role.getAuthorityList())){
                role.setId(UUIDUtil.getRandomUUIDString());
            }
            roleService.save(role);
        }
        boolean isCopy = false;
        if(StringUtils.isNotBlank(copyAuFromRoleId)){
            // 根据 copyAuFromRoleId 查找该角色的权限并设置到要保存的角色中
            List<SystemAuthoritys> copyAuFromRoleAuList = roleService.getRoleAllAuthoritys(copyAuFromRoleId);
            if(CollectionUtils.isEmpty(copyAuFromRoleAuList)){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ServiceUtil.buildFuture(JsonResult.sendBadParameterResult("要复制的角色不存在或该角色中没有配制权限"));
            }
            role.setAuthorityList(copyAuFromRoleAuList);
            isCopy = true;
        }
        if(CollectionUtils.isNotEmpty(role.getAuthorityList())){
            // 保存角色中的权限
            List<String> auIdList = new ArrayList<>(role.getAuthorityList().size());
            for(SystemAuthoritys au : role.getAuthorityList()){
                if(!isCopy && auService.getById(au.getId()) == null){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ServiceUtil.buildFuture(JsonResult.sendBadParameterResult("权限不存在"));
                }
                auIdList.add(au.getId());
            }
            roleService.replaceAuthority2Role(role.getId(), auIdList);
        }
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<JsonResult> delRole(String roleId) {
        Assert.hasText(roleId, "角色ID不能为空!");
        List<AlitaUserAccount> userList = userService.getUserByRoleId(roleId);
        if(CollectionUtils.isNotEmpty(userList)){
            return ServiceUtil.buildFuture(JsonResult.sendBadParameterResult("有用户拥有该角色,不允许删除"));
        }
        roleService.cleanAuthoritysFromRole(roleId);
        roleService.removeById(roleId);
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult());
    }

    @Override
    public CompletableFuture<JsonResult> roleInfo(String roleId){
        Assert.hasText(roleId, "角色ID不能为空!");
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(roleService.getById(roleId)));
    }

    @Override
    public CompletableFuture<JsonResult> catlogList(String catlogName, Page<SystemAuthoritysCatlog> page) {
        QueryWrapper<SystemAuthoritysCatlog> query = new QueryWrapper();
        if(StringUtils.isNotBlank(catlogName)){
            query.like("catlog_name", catlogName);
        }
        List<OrderItem> orders = new ArrayList<>(1);
        orders.add(OrderItem.desc("catlog_name"));
        page.setOrders(orders);
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(
                catlogService.page(page, query)));
    }

    @Override
    public CompletableFuture<JsonResult> catlogAll() {
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(
                catlogService.list()));
    }

    @Override
    public CompletableFuture<JsonResult> saveCatlog(SystemAuthoritysCatlog catlog) {
        Assert.notNull(catlog, "要保存的权限目录不能为 null !!!");
        if(StringUtils.isNotBlank(catlog.getId())){
            catlogService.updateById(catlog);
        } else {
            catlogService.save(catlog);
        }
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult());
    }

    @Override
    public CompletableFuture<JsonResult> delCatlog(String catlogId) {
        Assert.hasText(catlogId, "权限目录ID不能为空!");
        QueryWrapper<SystemAuthoritys> auQuery = new QueryWrapper();
        auQuery.eq("catlog_id", catlogId);
        int auCountByCatlogId = auService.count(auQuery);
        if(auCountByCatlogId > 0){
            return ServiceUtil.buildFuture(JsonResult.sendBadParameterResult("该目录下有权限,不允许删除"));
        }
        catlogService.removeById(catlogId);
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult());
    }

    @Override
    public CompletableFuture<JsonResult> catlogInfo(String catlogId){
        Assert.hasText(catlogId, "权限目录ID不能为空!");
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(catlogService.getById(catlogId)));
    }

    @Override
    public CompletableFuture<JsonResult> authoritysMenuList(String auName, Page<SystemAuthoritys> page) {
//        QueryWrapper<SystemAuthoritys> query = new QueryWrapper();
//        query.eq("authority_type", AuthorityTypeEnum.MENU.name());
//        if(StringUtils.isNotBlank(auName)){
//            query.like("authority_name", auName);
//        }
//        List<OrderItem> orders = new ArrayList<>(1);
//        orders.add(OrderItem.asc("show_index"));
//        page.setOrders(orders);
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(
                auService.selectSystemAuthoritysMenuList(page,auName, AuthorityTypeEnum.MENU.name())));
    }

    @Override
    public CompletableFuture<JsonResult> saveAuthority(SystemAuthoritys au) {
        Assert.notNull(au, "要保存的权限不能为 null !!!");
        Assert.hasText(au.getCatlogId(), "所属权限目录ID不能为空!");
        Assert.notNull(catlogService.getById(au.getCatlogId()), "所属权限目录不存在 !!!");
        if(StringUtils.isNotBlank(au.getId())){
            auService.updateById(au);
        } else {
            auService.save(au);
        }
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<JsonResult> delAuthority(String auId) {
        Assert.hasText(auId, "权限ID不能为空!");
        auService.removeAuthorityFromRole(auId);
        auService.removeById(auId);
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult());
    }

    @Override
    public CompletableFuture<JsonResult> auInfo(String auId){
        Assert.hasText(auId, "权限ID不能为空!");
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(auService.getById(auId)));
    }

}
