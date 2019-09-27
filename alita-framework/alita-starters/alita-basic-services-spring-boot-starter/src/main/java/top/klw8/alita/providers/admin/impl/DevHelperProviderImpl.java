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
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.authority.IDevHelperProvider;
import top.klw8.alita.service.authority.IAlitaUserService;
import top.klw8.alita.service.authority.ISystemAuthoritysCatlogService;
import top.klw8.alita.service.authority.ISystemAuthoritysService;
import top.klw8.alita.service.authority.ISystemRoleService;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.utils.EntityUtil;
import top.klw8.alita.starter.service.common.ServiceContext;
import top.klw8.alita.utils.UUIDUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
                    if (EntityUtil.isEntityEmpty(catlogFinded)) {
                        catlog.setId(UUIDUtil.getRandomUUIDString());
                        catlogService.save(catlog);
                    }
                    if (CollectionUtils.isNotEmpty(catlog.getAuthorityList())) {
                        for (SystemAuthoritys au : catlog.getAuthorityList()) {
                            SystemAuthoritys auFinded = auService.findByAuAction(au.getAuthorityAction());
                            if (EntityUtil.isEntityEmpty(auFinded)) {
                                au.setId(UUIDUtil.getRandomUUIDString());
                                auService.save(au);
                                catlogService.addAuthority2Catlog(catlog.getId(), au);
                                auFinded = au;
                            }
                            if (isAdd2SuperAdmin) {
                                // 添加到超级管理员角色和用户中
                                AlitaUserAccount superAdmin = userService.getById("d84c6b4ed9134d468e5a43d467036c46");
                                boolean isNeedAddRole2User = false;
                                if (EntityUtil.isEntityEmpty(superAdmin)) {
                                    isNeedAddRole2User = true;
                                    BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
                                    superAdmin = new AlitaUserAccount("admin", "15808888888", pwdEncoder.encode("123456"));
                                    superAdmin.setId("d84c6b4ed9134d468e5a43d467036c46");
                                    superAdmin.setCreateDate(LocalDateTime.now());
                                    userService.save(superAdmin);
                                }
                                SystemRole superAdminRole = roleService.getById("d84c6b4ed9134d468e5a43d467036c47");
                                if (EntityUtil.isEntityEmpty(superAdminRole)) {
                                    isNeedAddRole2User = true;
                                    superAdminRole = new SystemRole();
                                    superAdminRole.setId("d84c6b4ed9134d468e5a43d467036c47");
                                    superAdminRole.setRoleName("超级管理员");
                                    superAdminRole.setRemark("超级管理员");
                                    roleService.save(superAdminRole);
                                }
                                if (isNeedAddRole2User) {
                                    userService.addRole2User(superAdmin.getId(), superAdminRole);
                                }
                                roleService.addAuthority2Role(superAdminRole.getId(), auFinded);
                            }
                        }
                    }
                }
                return JsonResult.sendSuccessfulResult("注册完成", null);
            }
            return JsonResult.sendSuccessfulResult("注册失败,没有任何待注册数据", null);
        }, ServiceContext.executor);
    }

}
