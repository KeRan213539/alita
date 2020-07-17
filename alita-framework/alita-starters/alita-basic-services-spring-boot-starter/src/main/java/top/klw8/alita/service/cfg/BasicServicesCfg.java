package top.klw8.alita.service.cfg;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import top.klw8.alita.helper.UserCacheHelper;
import top.klw8.alita.providers.admin.impl.AlitaUserProvider;
import top.klw8.alita.providers.admin.impl.AuthorityAdminProviderImpl;
import top.klw8.alita.providers.admin.impl.DevHelperProviderImpl;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;
import top.klw8.alita.service.api.authority.IDevHelperProvider;
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
@Import({UserCacheHelper.class
        , AlitaUserServiceImpl.class, SystemAuthoritysCatlogServiceImpl.class
        , SystemAuthoritysServiceImpl.class, SystemRoleServiceImpl.class
        , DevHelperProviderImpl.class, AuthorityAdminProviderImpl.class, AlitaUserProvider.class})
public class BasicServicesCfg {

    @Autowired
    private ApplicationConfig application;

    @Autowired
    private RegistryConfig registry;

    @Autowired
    private ProtocolConfig protocol;

    @Autowired
    private Environment env;

    @Bean
    public Object regDubboProviders(@Autowired(required = false) IDevHelperProvider devHelperProvider,
                                    @Autowired IAuthorityAdminProvider authorityAdminProvider,
                                    @Autowired IAlitaUserProvider alitaUserProvider) {
        String[] activeprofiles = env.getActiveProfiles();
        for (String activeprofile : activeprofiles) {
            if (activeprofile.equals("dev")) {
                exportDubboService(IDevHelperProvider.class, devHelperProvider);
            }
        }
        exportDubboService(IAlitaUserProvider.class, alitaUserProvider);
        exportDubboService(IAuthorityAdminProvider.class, authorityAdminProvider);


        return new Object();
    }

//    @Bean
//    public IAlitaUserService alitaUserService(){
//        return new AlitaUserServiceImpl();
//    }
//
//    @Bean
//    public ISystemAuthoritysCatlogService systemAuthoritysCatlogService() throws InstantiationException, IllegalAccessException {
//        String[] activeprofiles = env.getActiveProfiles();
//        for (String activeprofile : activeprofiles) {
//            if (activeprofile.equals("dev")) {
//                exportDubboService(IDevHelperProvider.class, DevHelperProviderImpl.class);
//            }
//        }
//        exportDubboService(IAuthorityAdminProvider.class, AuthorityAdminProviderImpl.class);
//        exportDubboService(IAlitaUserProvider.class, AlitaUserProvider.class);
//
//        return new SystemAuthoritysCatlogServiceImpl();
//    }
//
//    @Bean
//    public ISystemAuthoritysService systemAuthoritysService(){
//        return new SystemAuthoritysServiceImpl();
//    }
//
//    @Bean
//    public ISystemRoleService systemRoleService(){
//        return new SystemRoleServiceImpl();
//    }

    private <I, T> void exportDubboService(Class<I> serviceClass, T serviceImplInstance) {
        ServiceConfig<T> service = new ServiceConfig<>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
        service.setApplication(application);
        service.setRegistry(registry); // 多个注册中心可以用setRegistries()
        service.setProtocol(protocol); // 多个协议可以用setProtocols()
        service.setInterface(serviceClass);
        service.setRef(serviceImplInstance);
        service.setAsync(true);
//        service.setVersion("1.0.0");
        service.export();// 暴露及注册服务
    }

}
