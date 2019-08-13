package org.springframework.boot.context.properties;

import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.context.ApplicationContext;


/**
 * @ClassName: BindConfig2BeanHelper
 * @Description: 从spring中读取配制(包括配制文件中和zookeeper中的或者其他通过spring读取的配制)并转换为对应的Bean
 * @author klw
 * @date 2018年11月1日 上午11:01:37
 */
public class BindConfig2BeanHelper {
    
    private ConfigurationPropertiesBinder binder = null;
    
    public BindConfig2BeanHelper(ApplicationContext applicationContext) {
	    binder = new ConfigurationPropertiesBinder(applicationContext, "configurationPropertiesValidator");
    }

    public void bind(Bindable<?> target) {
	binder.bind(target);
    }
    
}
