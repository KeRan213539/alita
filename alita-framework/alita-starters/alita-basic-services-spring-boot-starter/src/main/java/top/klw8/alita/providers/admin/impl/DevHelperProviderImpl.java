package top.klw8.alita.providers.admin.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.authority.IDevHelperProvider;
import top.klw8.alita.service.authority.IAlitaUserService;
import top.klw8.alita.service.authority.ISystemAuthoritysCatlogService;
import top.klw8.alita.service.authority.ISystemAuthoritysService;
import top.klw8.alita.service.authority.ISystemRoleService;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.utils.EntityUtil;
import top.klw8.alita.starter.service.common.ServiceContext;
import top.klw8.alita.starter.service.common.ServiceUtil;
import top.klw8.alita.utils.UUIDUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DevHelperProviderImpl
 * @Description: 开发辅助工具
 * @date 2019/8/15 8:44
 */
@Slf4j
@Profile("dev")
@Service(async = true, timeout=5000)
public class DevHelperProviderImpl implements IDevHelperProvider {

    private static final String ADMIN_USER_ID = "d84c6b4ed9134d468e5a43d467036c46";

    private static final String ADMIN_ROLE_ID = "d84c6b4ed9134d468e5a43d467036c47";

    @Autowired
    private ISystemAuthoritysService auService;

    @Autowired
    private ISystemAuthoritysCatlogService catlogService;

    @Autowired
    private ISystemRoleService roleService;

    @Autowired
    private IAlitaUserService userService;

    @Override
    public CompletableFuture<JsonResult> batchAddAuthoritysAndCatlogs(List<SystemAuthoritysCatlog> catlogList, boolean isAdd2SuperAdmin) {
        return CompletableFuture.supplyAsync(() -> {
            if (CollectionUtils.isNotEmpty(catlogList)) {
                for (SystemAuthoritysCatlog catlog : catlogList) {
                    if (StringUtils.isBlank(catlog.getCatlogName())) {
                        continue;
                    }
                    SystemAuthoritysCatlog catlogFinded = catlogService.findByCatlogName(catlog.getCatlogName());
                    String catlogId;
                    if (EntityUtil.isEntityEmpty(catlogFinded)) {
                        catlogId = UUIDUtil.getRandomUUIDString();
                        catlog.setId(catlogId);
                        catlogService.save(catlog);
                    } else {
                        catlogId = catlogFinded.getId();
                    }
                    if (CollectionUtils.isNotEmpty(catlog.getAuthorityList())) {
                        for (SystemAuthoritys au : catlog.getAuthorityList()) {
                            SystemAuthoritys auFinded = auService.findByAuAction(au.getAuthorityAction());
                            if (EntityUtil.isEntityEmpty(auFinded)) {
                                au.setId(UUIDUtil.getRandomUUIDString());
                                au.setCatlogId(catlogId);
                                auService.save(au);
                                auFinded = au;
                            }
                            if (isAdd2SuperAdmin) {
                                // 添加到超级管理员角色和用户中
                                checkAdminUserRoleAndAddIfNotExist();
                                roleService.addAuthority2Role(ADMIN_ROLE_ID, auFinded);
                            }
                        }
                    }
                }
                return JsonResult.sendSuccessfulResult("注册完成");
            }
            return JsonResult.sendSuccessfulResult("注册失败,没有任何待注册数据", null);
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addAllAuthoritys2AdminRole() {
        // 检查超级管理员角色和用户是否存在,不存在则添加
        checkAdminUserRoleAndAddIfNotExist();

        // 查询全部权限,并替换管理员角色中的权限
        List<SystemAuthoritys> authoritysList = auService.list();
        roleService.replaceAuthority2Role(ADMIN_ROLE_ID, authoritysList.stream().map(SystemAuthoritys::getId).collect(Collectors.toList()));
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult("OK"));
    }

    private void checkAdminUserRoleAndAddIfNotExist(){
        boolean isNeedAddRole2User = false;
        if (EntityUtil.isEntityEmpty(userService.getById(ADMIN_USER_ID))) {
            isNeedAddRole2User = true;
            userService.save(buildAdminUser());
        }
        if (EntityUtil.isEntityEmpty(roleService.getById(ADMIN_ROLE_ID))) {
            isNeedAddRole2User = true;
            roleService.save(buildAdminRole());
        }
        if (isNeedAddRole2User) {
            userService.addRole2User(ADMIN_USER_ID, ADMIN_ROLE_ID);
        }
    }

    private AlitaUserAccount buildAdminUser(){
        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
        AlitaUserAccount superAdmin = new AlitaUserAccount("admin", "15808888888", pwdEncoder.encode("123456"));
        superAdmin.setId(ADMIN_USER_ID);
        superAdmin.setCreateDate(LocalDateTime.now());
        return superAdmin;
    }

    private SystemRole buildAdminRole(){
        SystemRole superAdminRole = new SystemRole();
        superAdminRole.setId(ADMIN_ROLE_ID);
        superAdminRole.setRoleName("超级管理员");
        superAdminRole.setRemark("超级管理员");
        return superAdminRole;
    }

}
