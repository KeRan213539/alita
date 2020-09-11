package top.klw8.alita.starter.cfg;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * alita.oauth2.token 配制.
 *
 * @author klw(213539 @ qq.com)
 * @ClassName: TokenConfigBean
 * @date 2020/9/11 11:10
 */
@Data
@ConfigurationProperties(prefix="alita.oauth2.token")
public class TokenConfigBean {
    
    private boolean storeInRedis;
    
    private List<String> checkExcludePaths;
    
}
