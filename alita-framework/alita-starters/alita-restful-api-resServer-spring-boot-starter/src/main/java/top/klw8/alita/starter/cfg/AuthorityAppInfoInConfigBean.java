package top.klw8.alita.starter.cfg;

import lombok.Getter;
import top.klw8.alita.entitys.authority.AlitaAuthoritysApp;

/**
 * 配制中的app信息
 * 2020/7/21 16:31
 */
@Getter
public class AuthorityAppInfoInConfigBean {

    private String appTag;

    private String appName;

    private String remark;

    private AlitaAuthoritysApp appEntity;

    public AuthorityAppInfoInConfigBean(String appTag, String appName, String remark){
        this.appTag = appTag;
        this.appName = appName;
        this.remark = remark;
        this.appEntity = new AlitaAuthoritysApp();
        this.appEntity.setAppTag(appTag);
        this.appEntity.setAppName(appName);
        this.appEntity.setRemark(remark);
    }

}
