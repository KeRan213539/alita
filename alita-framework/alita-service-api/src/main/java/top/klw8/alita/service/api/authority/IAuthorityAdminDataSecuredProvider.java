package top.klw8.alita.service.api.authority;

import top.klw8.alita.entitys.authority.*;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: IAuthorityAdminDataSecuredProvider
 * @Description: 权限管理数据权限相关
 * @date 2020/7/27 15:32
 */
public interface IAuthorityAdminDataSecuredProvider {

    List<SystemAuthoritysApp> allApp();

    SystemRole roleById(String id);

    SystemAuthoritysCatlog catlogById(String id);

    SystemAuthoritys auById(String id);

    SystemDataSecured dsById(String id);

}
