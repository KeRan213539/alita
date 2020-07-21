package top.klw8.alita.starter.cfg;

import lombok.Getter;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AuthorityAppInfoFromConfig
 * @Description: 配制中的app信息
 * @date 2020/7/21 16:31
 */
@Getter
public class AuthorityAppInfoInConfig {

    private String appTag;

    private String appName;

    private String remark;

    public AuthorityAppInfoInConfig(String appTag, String appName, String remark){
        this.appTag = appTag;
        this.appName = appName;
        this.remark = remark;
    }

}
