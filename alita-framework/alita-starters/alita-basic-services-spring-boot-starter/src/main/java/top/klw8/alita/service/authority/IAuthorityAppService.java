package top.klw8.alita.service.authority;

import com.baomidou.mybatisplus.extension.service.IService;
import top.klw8.alita.entitys.authority.SystemAuthoritysApp;
import top.klw8.alita.service.result.JsonResult;

/**
 * @author zhaozheng
 * @description:
 * @date: 2020-07-16
 */

public interface IAuthorityAppService extends IService<SystemAuthoritysApp> {


    JsonResult addAuthorityApp(SystemAuthoritysApp authorityApp);

    JsonResult deleteAuthorityApp(String appTag);

    JsonResult updateAuthorityApp(SystemAuthoritysApp authorityApp);

    JsonResult<SystemAuthoritysApp> findAuthorityApp(SystemAuthoritysApp authorityApp);
}
