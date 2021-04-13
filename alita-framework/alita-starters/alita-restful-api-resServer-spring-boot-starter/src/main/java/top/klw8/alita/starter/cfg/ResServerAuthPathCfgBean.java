package top.klw8.alita.starter.cfg;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * oauth2 资源服务需要认证的路径配制bean
 * 2018年12月6日 上午10:35:19
 */
@Data
@ConfigurationProperties(prefix="alita.oauth2.res-server")
public class ResServerAuthPathCfgBean {

    private List<String> authPath;
    
}
