package top.klw8.alita.starter.authorization.cfg.beans;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: TokenConfigBean
 * @Description: token配制bean
 * @date 2020/8/17 11:20
 */
@Getter
@Setter
@ConfigurationProperties(prefix="alita.oauth2.token")
public class TokenConfigBean {

    private boolean storeInRedis = false;

    private TokenTimeoutConfigBean timeoutSeconds = new TokenTimeoutConfigBean();


}
