package top.klw8.alita.base.springctx;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: SpringApplicationContextUtil
 * @Description: Spring的ApplicationContext工具类,用于启动时获取 ApplicationContext 并 hold 住 引用
 * @author klw
 * @date 2018年9月17日 下午1:18:57
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
		+ "ApplicationContext已 hole 住,可以通过SpringApplicationContextUtil.getAppContext()获取applicationContext对象\n\n"
		+ "---------------------------------------------------------------------";
	log.info(logInfo);
    }
    
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
    
    /**
     * @Title: getBean
     * @author klw
     * @Description: 通过class获取Bean <br />
     * 不能获取dubbo service,因为dubbo service是在第一次调用的时候才放入spring容器中的,这里获取的时候dubbo service很可能还没被初始化
     * @param clazz
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**
     * @Title: getBean
     * @author klw
     * @Description: 通过bean id,以及Clazz返回指定的Bean <br />
     * 不能获取dubbo service,因为dubbo service是在第一次调用的时候才放入spring容器中的,这里获取的时候dubbo service很可能还没被初始化
     * @param name
     * @param clazz
     * @return
     */
    public static <T> T getBean(String beanId,Class<T> clazz){
        return getApplicationContext().getBean(beanId, clazz);
    }
    
    /**
     * @Title: getBean
     * @author klw
     * @Description: 通过bean id返回指定的Bean <br />
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
