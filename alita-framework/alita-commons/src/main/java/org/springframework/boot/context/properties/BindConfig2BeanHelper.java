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
package org.springframework.boot.context.properties;

import org.springframework.context.ApplicationContext;


/**
 * 从spring中读取配制(包括配制文件中和zookeeper中的或者其他通过spring读取的配制)并转换为对应的Bean
 * 2018年11月1日 上午11:01:37
 */
public class BindConfig2BeanHelper {

    private ConfigurationPropertiesBinder binder;

    public BindConfig2BeanHelper(ApplicationContext applicationContext) {
        binder = new ConfigurationPropertiesBinder(applicationContext);
    }

    public void bind(ConfigurationPropertiesBean propertiesBean) {
        binder.bind(propertiesBean);
    }

}
