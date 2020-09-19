package top.klw8.alita.providers.admin.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import top.klw8.alita.entitys.authority.*;
import top.klw8.alita.service.api.authority.IAuthorityAdminDataSecuredProvider;
import top.klw8.alita.service.authority.*;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AuthorityAdminDataSecuredProviderImpl
 * @Description: 权限管理数据权限相关 实现
 * @date 2020/7/27 15:33
 */
@Slf4j
@Service
public class AuthorityAdminDataSecuredProviderImpl implements IAuthorityAdminDataSecuredProvider {

    @Autowired
    private IAuthorityAppService appService;

    @Autowired
    private ISystemRoleService roleService;

    @Autowired
    private ISystemAuthoritysCatlogService catlogService;

    @Autowired
    private ISystemAuthoritysService authoritysService;

    @Autowired
    private ISystemDataSecuredService dataSecuredService;

    @Override
    public List<SystemAuthoritysApp> allApp() {
        return appService.list();
    }

    @Override
    public SystemRole roleById(String id) {
        return roleService.getById(id);
    }

    @Override
    public SystemAuthoritysCatlog catlogById(String id) {
        return catlogService.getById(id);
    }

    @Override
    public SystemAuthoritys auById(String id) {
        return authoritysService.getById(id);
    }

    @Override
    public SystemDataSecured dsById(String id) {
        return dataSecuredService.getById(id);
    }


}