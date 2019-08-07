package top.klw8.alita.config.redis;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @ClassName: RedisConfigBean
 * @Description: redis配制bean
 * @author klw
 * @date 2018年10月31日 上午10:33:40
 */
@ConfigurationProperties(prefix="alita.redis")
@Data
public class RedisConfigBean {
    
    private boolean enabled = false;
    
    private String defaultHost;
    
    private Integer defaultPort;
    
    private String defaultPass;
    
    private List<String> extendHosts;
    
    private List<Integer> extendPorts;
    
    private List<String> extendPasss;
    
    /**
     * @author klw
     * @Fields extendBeanId : 其他的redis服务器的 spring bean id
     */
    private List<String> extendBeanIds;
    
}
