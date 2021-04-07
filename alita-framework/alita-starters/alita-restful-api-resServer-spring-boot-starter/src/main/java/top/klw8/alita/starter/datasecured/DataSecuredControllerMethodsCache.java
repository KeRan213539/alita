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
package top.klw8.alita.starter.datasecured;

import org.springframework.core.env.Environment;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import top.klw8.alita.utils.AuthorityUtil;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ControllerMethodsCache
 * @Description: 需要数据权限的Controller方法缓存
 * @date 2020/4/24 16:01
 */
public class DataSecuredControllerMethodsCache {

    private static ConcurrentMap<String, DataSecured> methods = new ConcurrentHashMap<>();

    public static DataSecured getMethod(String httpMethodAndpath) {
        return methods.get(httpMethodAndpath);
    }

    public static void initControllerMethod(RequestMappingHandlerMapping reqMapping, Environment env) {

        Map<RequestMappingInfo, HandlerMethod> methodMap = reqMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> methodEntry : methodMap.entrySet()) {
            HandlerMethod v = methodEntry.getValue();
            Method method = v.getMethod();
            if(!method.isAnnotationPresent(DataSecured.class)){
                continue;
            }
            String key = env.resolvePlaceholders(AuthorityUtil.getCompleteMappingUrl(v));
            methods.put(key, method.getAnnotation(DataSecured.class));
        }

    }


}
