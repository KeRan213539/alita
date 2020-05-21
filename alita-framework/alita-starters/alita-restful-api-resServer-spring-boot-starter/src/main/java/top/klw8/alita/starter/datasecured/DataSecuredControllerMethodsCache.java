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
