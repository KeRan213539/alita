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

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

/**
 * 权限相关工具
 * 2020/5/11 14:02
 */
public class AuthorityUtil {

    /**
     * httpMethod 和 url 的分隔符
     */
    private final static String HTTPMETHOD_SEPARATOR_ACTION_URL = "-->";

    /**
     * 获取完整的url mapping(类配制+方法配制)
     * 2020/5/11 17:07
     * @param: handlerMethod
     * @return java.lang.String
     */
    public static String getCompleteMappingUrl(HandlerMethod handlerMethod){
        Assert.notNull(handlerMethod, "handlerMethod 不能为null!");
        Method method = handlerMethod.getMethod();
        String authorityActionPrefix;
        String authorityAction;
        String httpMethod = "";
        Class<?> controllerClass = handlerMethod.getBeanType();
        if (controllerClass.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping mapping = controllerClass.getAnnotation(RequestMapping.class);
            authorityActionPrefix = AuthorityUtil.getStringArrayFirst(mapping.value());
        } else {
            authorityActionPrefix = "";
        }
        if (method.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping mapping = method.getAnnotation(RequestMapping.class);
            authorityAction = authorityActionPrefix + AuthorityUtil.getStringArrayFirst(mapping.value());
            if(ArrayUtils.isNotEmpty(mapping.method())){
                httpMethod = mapping.method()[0].name();
            }
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            PostMapping mapping = method.getAnnotation(PostMapping.class);
            authorityAction = authorityActionPrefix + AuthorityUtil.getStringArrayFirst(mapping.value());
            httpMethod = HttpMethod.POST.name();
        } else if (method.isAnnotationPresent(GetMapping.class)) {
            GetMapping mapping = method.getAnnotation(GetMapping.class);
            authorityAction = authorityActionPrefix + AuthorityUtil.getStringArrayFirst(mapping.value());
            httpMethod = HttpMethod.GET.name();
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            PutMapping mapping = method.getAnnotation(PutMapping.class);
            authorityAction = authorityActionPrefix + AuthorityUtil.getStringArrayFirst(mapping.value());
            httpMethod = HttpMethod.PUT.name();
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            DeleteMapping mapping = method.getAnnotation(DeleteMapping.class);
            authorityAction = authorityActionPrefix + AuthorityUtil.getStringArrayFirst(mapping.value());
            httpMethod = HttpMethod.DELETE.name();
        } else if (method.isAnnotationPresent(PatchMapping.class)){
            PatchMapping mapping = method.getAnnotation(PatchMapping.class);
            authorityAction = authorityActionPrefix + AuthorityUtil.getStringArrayFirst(mapping.value());
            httpMethod = HttpMethod.PATCH.name();
        }else {
            authorityAction = authorityActionPrefix;
        }
        return httpMethod + HTTPMETHOD_SEPARATOR_ACTION_URL + authorityAction;
    }

    public static String getStringArrayFirst(String[] strArr){
        if(ArrayUtils.isEmpty(strArr)){
            return "";
        } else {
            return strArr[0];
        }
    }

    /**
     * 去除 完整的url mapping(类配制+方法配制) 中的分隔符
     * 2020/5/12 16:17
     * @param: str
     * @return java.lang.String
     */
    public static String removeSeparator(String str){
        if(str.indexOf(HTTPMETHOD_SEPARATOR_ACTION_URL) > -1){
            return str.substring(str.indexOf(HTTPMETHOD_SEPARATOR_ACTION_URL) + HTTPMETHOD_SEPARATOR_ACTION_URL.length());
        }
        return str;
    }

    /**
     * 字符串的httpMethod 和 url 加分隔符组合
     */
    public static String composeWithSeparator2(String strHttpMethod, String url){
        if(url.indexOf(HTTPMETHOD_SEPARATOR_ACTION_URL) != -1){
            return url;
        }
        return strHttpMethod + HTTPMETHOD_SEPARATOR_ACTION_URL + url;
    }

    /**
     * httpMethod 和 url 加分隔符组合
     */
    public static String composeWithSeparator(HttpMethod httpMethod, String url){
        if(url.indexOf(HTTPMETHOD_SEPARATOR_ACTION_URL) != -1){
            return url;
        }
        return httpMethod.name() + HTTPMETHOD_SEPARATOR_ACTION_URL + url;
    }

}
