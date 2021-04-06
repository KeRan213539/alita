package top.klw8.alita.starter.cfg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 配置环境处理器.
 *
 * @author klw(213539 @ qq.com)
 * @ClassName: ConfigEnvironmentProcessor
 * @date 2020/9/14 17:56
 */
public class ConfigEnvironmentProcessor implements EnvironmentPostProcessor, Ordered {
    
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        application.addInitializers(new ConfigApplicationContextInitializer());
    }
    
    @Override
    public int getOrder() {
        return 1;
    }
}
