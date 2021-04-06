package top.klw8.alita.web.authority.ds;

import top.klw8.alita.entitys.authority.SystemAuthoritysApp;
import top.klw8.alita.starter.auscan.IDataSecuredSourceItem;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AppDataSecuredSourceItem
 * @Description: 数据权限来源中的 APP 元素
 * @date 2020/5/12 16:50
 */
public class AppDataSecuredSourceItem implements IDataSecuredSourceItem {

    private String appTag;

    private String appName;

    public AppDataSecuredSourceItem(SystemAuthoritysApp app){
        this.appTag = app.getAppTag();
        this.appName = app.getAppName();
    }

    @Override
    public String getResource() {
        return this.appTag;
    }

    @Override
    public String getRemark() {
        return this.appName;
    }
}
