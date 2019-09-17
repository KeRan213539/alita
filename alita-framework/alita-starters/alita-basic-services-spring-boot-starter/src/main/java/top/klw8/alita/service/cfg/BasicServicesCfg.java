package top.klw8.alita.service.cfg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.klw8.alita.helper.UserCacheHelper;
import top.klw8.alita.service.authority.service.impl.AlitaUserServiceImpl;
import top.klw8.alita.service.authority.service.impl.SystemAuthoritysCatlogServiceImpl;
import top.klw8.alita.service.authority.service.impl.SystemAuthoritysServiceImpl;
import top.klw8.alita.service.authority.service.impl.SystemRoleServiceImpl;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: BasicServicesCfg
 * @Description: basic-services配制
 * @date 2019/9/17 14:08
 */
@Configuration
@MapperScan("top.klw8.alita.service.authority.mapper")
@Import({UserCacheHelper.class})
public class BasicServicesCfg {

    @Bean
    public AlitaUserServiceImpl alitaUserServiceImpl(){
        return new AlitaUserServiceImpl();
    }

    @Bean
    public SystemAuthoritysCatlogServiceImpl systemAuthoritysCatlogServiceImpl(){
        return new SystemAuthoritysCatlogServiceImpl();
    }

    @Bean
    public SystemAuthoritysServiceImpl systemAuthoritysServiceImpl(){
        return new SystemAuthoritysServiceImpl();
    }

    @Bean
    public SystemRoleServiceImpl systemRoleServiceImpl(){
        return new SystemRoleServiceImpl();
    }

}
