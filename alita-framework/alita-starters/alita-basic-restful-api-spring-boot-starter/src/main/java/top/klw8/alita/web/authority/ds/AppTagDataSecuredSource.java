package top.klw8.alita.web.authority.ds;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import top.klw8.alita.entitys.authority.SystemAuthoritysApp;
import top.klw8.alita.service.api.authority.IAuthorityAdminDataSecuredProvider;
import top.klw8.alita.service.api.authority.IAuthorityAppProvider;
import top.klw8.alita.starter.annotations.PublicDataSecuredRegister;
import top.klw8.alita.starter.auscan.IDataSecuredSource;
import top.klw8.alita.starter.auscan.IDataSecuredSourceItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AppTagDataSecuredSource
 * @Description: appTag的数据权限资源数据源
 * @date 2020/7/27 15:19
 */
@Component
@PublicDataSecuredRegister
public class AppTagDataSecuredSource implements IDataSecuredSource {

    @Reference
    private IAuthorityAdminDataSecuredProvider adminDataSecuredProvider;

    @Override
    public List<AppDataSecuredSourceItem> getDataSecuredSourceList() {
        List<SystemAuthoritysApp> allApp = adminDataSecuredProvider.allApp();
        if(null != allApp) {
            List<AppDataSecuredSourceItem> result = new ArrayList<>(allApp.size());
            for (SystemAuthoritysApp app : allApp) {
                result.add(new AppDataSecuredSourceItem(app));
            }
            return result;
        }
        return null;
    }

}
