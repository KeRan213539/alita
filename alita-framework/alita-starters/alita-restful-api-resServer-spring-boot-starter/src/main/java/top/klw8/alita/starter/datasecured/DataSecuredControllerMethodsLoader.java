package top.klw8.alita.starter.datasecured;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ControllerMethodsLoader
 * @Description: 需要数据权限的Congroller方法加载器
 * @date 2020/4/24 16:01
 */
@Slf4j
@Component
public class DataSecuredControllerMethodsLoader implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private Environment env;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("================= initControllerMethod begin... ================");
        DataSecuredControllerMethodsCache.initControllerMethod(applicationContext.getBean(RequestMappingHandlerMapping.class), env);
        log.info("================= initControllerMethod end ================");
    }
}
