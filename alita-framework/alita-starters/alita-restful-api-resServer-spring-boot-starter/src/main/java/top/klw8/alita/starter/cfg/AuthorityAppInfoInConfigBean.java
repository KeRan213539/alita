package top.klw8.alita.starter.cfg;

import lombok.Getter;
import top.klw8.alita.entitys.authority.SystemAuthoritysApp;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AuthorityAppInfoInConfigBean
 * @Description: 配制中的app信息
 * @date 2020/7/21 16:31
 */
@Getter
public class AuthorityAppInfoInConfigBean {

    private String appTag;

    private String appName;

    private String remark;

    private SystemAuthoritysApp appEntity;

    public AuthorityAppInfoInConfigBean(String appTag, String appName, String remark){
        this.appTag = appTag;
        this.appName = appName;
        this.remark = remark;
        this.appEntity = new SystemAuthoritysApp();
        this.appEntity.setAppTag(appTag);
        this.appEntity.setAppName(appName);
        this.appEntity.setRemark(remark);
    }

}