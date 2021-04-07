/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.utils;

import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: BeanUtil
 * @Description: bean 工具
 * @date 2020/2/25 20:09
 * <p>
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 * ┏┓   ┏┓
 * ┏┛┻━━━┛┻┓
 * ┃   ☃   ┃
 * ┃ ┳┛ ┗┳ ┃
 * ┃   ┻   ┃
 * ┗━┓   ┏━┛
 * ┃   ┗━━━━━━━┓
 * ┃  神兽保佑   ┣┓
 * ┃  永无BUG   ┏┛
 * ┗━┓┓┏━━━┓┓┏━┛
 * ┃┫┫   ┃┫┫
 * ┗┻┛   ┗┻┛
 */
public class BeanUtil {

    /***
     * 将对象转换为map对象
     * @param bean 对象
     * @return
     */
    public static Map<String, Object> beanToMap(Object bean) {
        if (bean instanceof Map) {
            return (Map<String, Object>) bean;
        }
        return null == bean ? null : BeanMap.create(bean);
    }


    /**
     * 将map转为对象
     *
     * @param map   存放数据的map对象
     * @param clazz 待转换的class
     * @return 转换后的Bean对象
     * @throws Exception 异常
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        T bean = BeanUtil.newInstance(clazz);
        BeanMap.create(bean).putAll(map);
        return bean;
    }

    /**
     * 根据指定的class， 实例化一个对象，根据构造参数来实例化
     * <p>
     * 在 java9 及其之后的版本 Class.newInstance() 方法已被废弃
     * </p>
     *
     * @param clazz 需要实例化的对象
     * @param <T>   类型，由输入类型决定
     * @return 返回新的实例
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("实例化对象时出现错误,请尝试给 " + clazz.getName() + " 添加无参的构造方法", e);
        }
    }

}
