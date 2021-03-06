package top.klw8.alita.base.springctx;

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
import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;

import lombok.extern.slf4j.Slf4j;

/**
 * Spring的ApplicationContext工具类,用于启动时获取 ApplicationContext 并 hold 住 引用
 * 2018年9月17日 下午1:18:57
 */
@Lazy(false)
@Slf4j
public class SpringApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext ctx;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	if(SpringApplicationContextUtil.ctx == null) {
	    SpringApplicationContextUtil.ctx = applicationContext;
	}

	String logInfo = "\n---------------------------------------------------------------------\n\n"
		+ "ApplicationContext已 hold 住,可以通过SpringApplicationContextUtil.getAppContext()获取applicationContext对象\n\n"
		+ "---------------------------------------------------------------------";
	log.info(logInfo);
    }
    
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
    
    /**
     * 通过class获取Bean <br />
     * 不能获取dubbo service,因为dubbo service是在第一次调用的时候才放入spring容器中的,这里获取的时候dubbo service很可能还没被初始化
     * @param clazz
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过bean id,以及Clazz返回指定的Bean <br />
     * 不能获取dubbo service,因为dubbo service是在第一次调用的时候才放入spring容器中的,这里获取的时候dubbo service很可能还没被初始化
     * @param name
     * @param clazz
     * @return
     */
    public static <T> T getBean(String beanId,Class<T> clazz){
        return getApplicationContext().getBean(beanId, clazz);
    }
    
    /**
     * 通过bean id返回指定的Bean <br />
     * 不能获取dubbo service,因为dubbo service是在第一次调用的时候才放入spring容器中的,这里获取的时候dubbo service很可能还没被初始化
     * @param name
     * @return
     */
    public static Object getBean(String beanId){
        return getApplicationContext().getBean(beanId);
    }
    
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType){
	return getApplicationContext().getBeansWithAnnotation(annotationType);
    }

}
