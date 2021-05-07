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
package top.klw8.alita.utils;


import org.springframework.boot.context.properties.BindConfig2BeanHelper;
import org.springframework.boot.context.properties.ConfigurationPropertiesBean;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;

/**
 * 从spring中读取配制(包括配制文件中和zookeeper中的或者其他通过spring读取的配制)并转换为对应的Bean
 * 2018年11月1日 下午2:32:12
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
