package top.klw8.alita.starter.datasecured;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import reactor.core.publisher.Mono;
import top.klw8.alita.service.result.JsonResult;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
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

    public static DataSecured getMethod(HttpMethod httpMethod, String path) {
        String key = httpMethod.name() + "-->" + path;
        return methods.get(key);
    }

    public static void initControllerMethod(RequestMappingHandlerMapping reqMapping, Environment env) {

        Map<RequestMappingInfo, HandlerMethod> methodMap = reqMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> methodEntry : methodMap.entrySet()) {
            HandlerMethod v = methodEntry.getValue();
            Method method = v.getMethod();
            if(!method.isAnnotationPresent(DataSecured.class)){
                continue;
            }
            String actionPathPrefix;
            String actionPath = "";
            String httpMethod = "";
            Class<?> controllerClass = v.getBeanType();
            if (controllerClass.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping mapping = controllerClass.getAnnotation(RequestMapping.class);
                actionPathPrefix = env.resolvePlaceholders(mapping.value()[0]);
            } else {
                actionPathPrefix = "";
            }
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping mapping = method.getAnnotation(RequestMapping.class);
                actionPath = actionPathPrefix + mapping.value()[0];
            } else if (method.isAnnotationPresent(PostMapping.class)) {
                PostMapping mapping = method.getAnnotation(PostMapping.class);
                actionPath = actionPathPrefix + mapping.value()[0];
                httpMethod = HttpMethod.POST.name();
            } else if (method.isAnnotationPresent(GetMapping.class)) {
                GetMapping mapping = method.getAnnotation(GetMapping.class);
                actionPath = actionPathPrefix + mapping.value()[0];
                httpMethod = HttpMethod.GET.name();
            } else if (method.isAnnotationPresent(PutMapping.class)) {
                PutMapping mapping = method.getAnnotation(PutMapping.class);
                actionPath = actionPathPrefix + mapping.value()[0];
                httpMethod = HttpMethod.PUT.name();
            } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                DeleteMapping mapping = method.getAnnotation(DeleteMapping.class);
                actionPath = actionPathPrefix + mapping.value()[0];
                httpMethod = HttpMethod.DELETE.name();
            } else if (method.isAnnotationPresent(PatchMapping.class)){
                PatchMapping mapping = method.getAnnotation(PatchMapping.class);
                actionPath = actionPathPrefix + mapping.value()[0];
                httpMethod = HttpMethod.PATCH.name();
            }
            actionPath = env.resolvePlaceholders(actionPath);
            String key = httpMethod + "-->" + actionPath;
            methods.put(key, method.getAnnotation(DataSecured.class));
        }

    }


}
