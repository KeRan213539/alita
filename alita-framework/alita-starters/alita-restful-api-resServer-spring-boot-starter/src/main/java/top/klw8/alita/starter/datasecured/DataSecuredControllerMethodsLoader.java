package top.klw8.alita.starter.datasecured;

/*
 * Copyright 2018-2021, ranke (213539@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
