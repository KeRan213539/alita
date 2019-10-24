package top.klw8.alita.providers.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.service.authority.IAlitaUserService;
import top.klw8.alita.service.authority.ISystemAuthoritysCatlogService;
import top.klw8.alita.service.authority.ISystemRoleService;
import top.klw8.alita.service.pojos.UserMenu;
import top.klw8.alita.service.pojos.UserMenuItem;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.starter.service.common.ServiceContext;
import top.klw8.alita.starter.service.common.ServiceUtil;
import top.klw8.alita.utils.LocalDateTimeUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AlitaUserProvider
 * @Description: 用户相关dubbo提供者
 * @date 2019/8/14 14:16
 */
@Slf4j
@Service(async = true)
public class AlitaUserProvider implements IAlitaUserProvider {

    @Autowired
    private IAlitaUserService userService;

    @Autowired
    private ISystemAuthoritysCatlogService catlogService;

    @Autowired
    private ISystemRoleService roleService;

    private BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();

    @Override
    public CompletableFuture<AlitaUserAccount> findUserById(String userId) {
        AlitaUserAccount user = userService.getById(userId);
        return CompletableFuture.supplyAsync(() -> user, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<AlitaUserAccount> findUserByName(String userName) {
        QueryWrapper<AlitaUserAccount> query = new QueryWrapper();
        query.eq("user_name", userName);
        return CompletableFuture.supplyAsync(() -> userService.getOne(query), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<AlitaUserAccount> findUserByPhoneNum(String userPhoneNum) {
        QueryWrapper<AlitaUserAccount> query = new QueryWrapper();
        query.eq("user_phone_num", userPhoneNum);
        return CompletableFuture.supplyAsync(() -> userService.getOne(query), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> findUserAuthorityMenus(String userId) {
        Map<String, UserMenu> menuMap = new HashMap<>(16);
        // 根据用户ID查询用户角色
        List<SystemRole> userRoles = userService.getUserAllRoles(userId);
        // 根据用户角色查询角色对应的权限并更新到SystemRole实体中
        for (SystemRole role : userRoles) {
            List<SystemAuthoritys> authoritys = roleService.getRoleAllAuthoritys(role.getId());
            for(SystemAuthoritys au : authoritys){
                if(AuthorityTypeEnum.MENU.equals(au.getAuthorityType())) {
                    UserMenu menu = menuMap.get(au.getCatlogId());
                    if (menu == null) {
                        SystemAuthoritysCatlog catlog = catlogService.getById(au.getCatlogId());
                        menu = new UserMenu(catlog.getCatlogName(), catlog.getShowIndex());
                        menuMap.put(catlog.getId(), menu);
                    }
                    menu.getItemList().add(new UserMenuItem(au.getAuthorityName(),
                            au.getAuthorityAction(), au.getShowIndex()));
                }
            }
        }

        return CompletableFuture.supplyAsync(() -> JsonResult.sendSuccessfulResult(
                new ArrayList<>(menuMap.values())), ServiceContext.executor);

    }

    @Override
    public CompletableFuture<JsonResult> userList(AlitaUserAccount user, LocalDateTime createDateBegin,
                                                  LocalDateTime createDateEnd, Page<AlitaUserAccount> page) {
        QueryWrapper<AlitaUserAccount> query = new QueryWrapper();
        // 排除密码字段
        query.select(AlitaUserAccount.class, i -> !i.getColumn().equals("user_pwd"));
        if(user != null){
            if(StringUtils.isNotBlank(user.getUsername())){
                query.like("user_name", user.getUsername());
            }
            if(StringUtils.isNotBlank(user.getUserPhoneNum())){
                query.like("user_phone_num", user.getUserPhoneNum());
            }
            if(createDateBegin != null && createDateEnd != null){
                query.between("create_date", LocalDateTimeUtil.dayBegin(createDateBegin),
                        LocalDateTimeUtil.dayEnd(createDateEnd));
            } else {
                if(createDateBegin != null){
                    query.ge("create_date", LocalDateTimeUtil.dayBegin(createDateBegin));
                }
                if(createDateEnd != null){
                    query.le("create_date", LocalDateTimeUtil.dayBegin(createDateEnd));
                }
            }
            if(user.getAccountNonExpired1() != null){
                query.eq("account_non_expired", user.getAccountNonExpired1());
            }
            if(user.getAccountNonLocked1() != null){
                query.eq("account_non_locked", user.getAccountNonLocked1());
            }
            if(user.getCredentialsNonExpired1() != null){
                query.eq("credentials_non_expired", user.getCredentialsNonExpired1());
            }
            if(user.getEnabled1() != null){
                query.eq("enabled", user.getEnabled1());
            }
        }
        List<OrderItem> orders = new ArrayList<>(1);
        orders.add(OrderItem.desc("create_date"));
        page.setOrders(orders);
        return CompletableFuture.supplyAsync(() -> JsonResult.sendSuccessfulResult(
                userService.page(page, query)), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> saveUserRoles(String userId, List<String> roleIds) {
        if(userService.getById(userId) == null){
            return CompletableFuture.supplyAsync(() -> JsonResult.sendBadParameterResult("用户不存在")
            , ServiceContext.executor);
        }
        for(String roleId : roleIds){
            if(roleService.getById(roleId) == null){
                return CompletableFuture.supplyAsync(() -> JsonResult.sendBadParameterResult("角色不存在")
                        , ServiceContext.executor);
            }
        }
        return CompletableFuture.supplyAsync(() -> JsonResult.sendSuccessfulResult(
                userService.replaceRole2User(userId, roleIds)), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addSaveUser(AlitaUserAccount user) {
        if(StringUtils.isNotBlank(user.getId())){
            return ServiceUtil.buildFuture(JsonResult.sendBadParameterResult("保存用户只支持新增,不支持修改!"));
        }
        user.setCreateDate(LocalDateTime.now());
        user.setAccountNonExpired1(Boolean.TRUE);
        user.setAccountNonLocked1(Boolean.TRUE);
        user.setCredentialsNonExpired1(Boolean.TRUE);
        user.setEnabled1(Boolean.TRUE);
        user.setUserPwd(pwdEncoder.encode(user.getUserPwd()));
        userService.save(user);
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult());
    }

    @Override
    public CompletableFuture<JsonResult> changeUserPassword(String userId, String oldPwd, String newPwd) {
        Assert.hasText(userId, "userId 不能为空!");
        Assert.hasText(oldPwd, "oldPwd 不能为空!");
        Assert.hasText(newPwd, "newPwd 不能为空!");
        AlitaUserAccount user = userService.getById(userId);
        Assert.notNull(user, "该用户不存在!");
        if(!pwdEncoder.matches(oldPwd, user.getUserPwd())){
            return ServiceUtil.buildFuture(JsonResult.sendBadParameterResult("密码不正确!"));
        }
        AlitaUserAccount user4update = new AlitaUserAccount();
        user4update.setId(userId);
        user4update.setUserPwd(pwdEncoder.encode(newPwd));
        if(userService.updateById(user4update)){
            return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult("密码更新成功!"));
        }
        return ServiceUtil.buildFuture(JsonResult.sendFailedResult("密码更新失败!"));
    }

}
