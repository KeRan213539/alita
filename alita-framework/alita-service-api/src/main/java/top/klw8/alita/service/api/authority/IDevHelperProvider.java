package top.klw8.alita.service.api.authority;

import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
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
    CompletableFuture<JsonResult> batchAddAuthoritysAndCatlogs(List<SystemAuthoritysCatlog> catlogList, boolean isAdd2SuperAdmin);

}
