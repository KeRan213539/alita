package top.klw8.alita.starter.cfg;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;

import java.util.HashMap;
import java.util.Map;

/**
 * 启动时设一些固定配制.
 *
 * 2020/9/14 17:43
 */
public class ConfigApplicationContextInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    
    @Override
    public void initialize(ConfigurableApplicationContext context) {
        ConfigurableEnvironment environment = context.getEnvironment();
        MutablePropertySources mutablePropertySources = environment.getPropertySources();
        Map<String, Object> propertySourceMap = new HashMap<>(1);
        propertySourceMap.put("springfox.documentation.swagger.v2.path", "/swagger-ui/v2/api-docs");
        mutablePropertySources.addAfter(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME,
                new MapPropertySource("resServerInitializerConfig", propertySourceMap));
    }
}
