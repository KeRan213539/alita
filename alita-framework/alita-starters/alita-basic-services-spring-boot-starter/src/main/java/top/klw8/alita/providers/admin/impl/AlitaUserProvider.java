package top.klw8.alita.providers.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.service.authority.IAlitaUserService;
import top.klw8.alita.service.authority.ISystemAuthoritysCatlogService;
import top.klw8.alita.service.pojos.UserMenu;
import top.klw8.alita.service.pojos.UserMenuItem;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.starter.service.common.ServiceContext;

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

    @Override
    public CompletableFuture<AlitaUserAccount> findUserById(String userId) {
        return CompletableFuture.supplyAsync(() -> userService.getById(userId), ServiceContext.executor);
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
        Map<String, UserMenu> menuMap = new HashMap<>();
        // 根据用户ID查询用户角色
        List<SystemRole> userRoles = userService.getUserAllRoles(userId);
        // 根据用户角色查询角色对应的权限并更新到SystemRole实体中
        for (SystemRole role : userRoles) {
            List<SystemAuthoritys> authoritys = userService.getRoleAllAuthoritys(role.getId());
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

}
