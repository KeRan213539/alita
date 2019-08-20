package top.klw8.alita.demo.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import top.klw8.alita.starter.authorization.cfg.IExtTokenGranters;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AuthorizationServerCfg
 * @Description: 认证服务配制
 * @date 2019/8/20 11:15
 */
@Configuration
public class AuthorizationServerCfg {

    @Bean
    @Primary
    public IExtTokenGranters extTokenGranters(){
        return new ExtTokenGrantersImpl();
    }

}
