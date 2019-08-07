package top.klw8.alita.authorization.cfg.beans;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: OAuth2ClientBean
 * @Description: OAuth2客户端配制bean
 * @author klw
 * @date 2018年12月13日 上午9:40:26
 */
@Getter
@Setter
@ConfigurationProperties(prefix="alita.oauth2.clients")
public class OAuth2ClientBean {

    /**
     * @author klw
     * @Fields list : 客户端list
     */
    private List<ClientItemBean> clientList;
    
}
