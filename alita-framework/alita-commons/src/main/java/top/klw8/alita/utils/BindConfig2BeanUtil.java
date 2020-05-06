package top.klw8.alita.utils;

import java.lang.annotation.Annotation;

import org.springframework.boot.context.properties.BindConfig2BeanHelper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBean;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;

import lombok.extern.slf4j.Slf4j;

/**
 * @author klw
 * @ClassName: BindConfig2BeanUtil
 * @Description: 从spring中读取配制(包括配制文件中和zookeeper中的或者其他通过spring读取的配制)并转换为对应的Bean
 * @date 2018年11月1日 下午2:32:12
 */
@Slf4j
public enum BindConfig2BeanUtil {

    INSTANCE;

    private BindConfig2BeanHelper helper = null;

    public <T> void bind(ApplicationContext applicationContext, T bean, String beanName) {
        if (helper == null) {
            synchronized (BindConfig2BeanUtil.class) {
                if (helper == null) {
                    helper = new BindConfig2BeanHelper(applicationContext);
                }
            }
        }


//        ConfigurationProperties annotation = bean.getClass().getAnnotation(ConfigurationProperties.class);
//        if (annotation == null) {
//            log.warn("【" + bean.getClass().getName() + "】没有标注ConfigurationProperties注解,无法加载配制");
//            return;
//        }
//        ResolvableType type = ResolvableType.forClass(bean.getClass());
//        Bindable<?> target = Bindable.of(type).withExistingValue(bean).withAnnotations(new Annotation[]{annotation});
        helper.bind(ConfigurationPropertiesBean.get(applicationContext, bean, beanName));
    }

}
