package top.klw8.alita.service.api.authority;

import top.klw8.alita.entitys.authority.SystemAuthoritysApp;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.entitys.authority.SystemDataSecured;
import top.klw8.alita.service.result.JsonResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: IDevHelperProvider
 * @Description: 开发辅助工具
 * @date 2019/8/15 8:42
 */
public interface IDevHelperProvider {

    /**
     * @author klw(213539@qq.com)
     * @Description: 批量添加权限目录和权限
     * @Date 2019/8/15 9:33
     * @param: catlogList
     * @return boolean
     */
    CompletableFuture<JsonResult> batchAddAuthoritysAndCatlogs(List<SystemAuthoritysCatlog> catlogList,
                                                               List<SystemDataSecured> publicDataSecuredList,
                                                               boolean isAdd2SuperAdmin, SystemAuthoritysApp app);

    /**
     * @author klw(213539@qq.com)
     * @Description: 添加所有权限到管理员角色和管理员账户,如果管理员角色或账户不存在则创建
     * @Date 2019/10/22 17:23
     * @param:
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addAllAuthoritys2AdminRole();

}
