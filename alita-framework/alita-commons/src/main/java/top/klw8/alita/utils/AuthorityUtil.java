package top.klw8.alita.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AuthorityUtil
 * @Description: 权限相关工具
 * @date 2020/5/11 14:02
 */
public class AuthorityUtil {

    /**
     * @author klw(213539@qq.com)
     * @Description: httpMethod 和 url 的分隔符
     */
    private final static String HTTPMETHOD_SEPARATOR_ACTION_URL = "-->";

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取完整的url mapping(类配制+方法配制)
     * @Date 2020/5/11 17:07
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
     * @author klw(213539@qq.com)
     * @Description: 去除 完整的url mapping(类配制+方法配制) 中的分隔符
     * @Date 2020/5/12 16:17
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
     * @author klw(213539@qq.com)
     * @Description: 字符串的httpMethod 和 url 加分隔符组合
     */
    public static String composeWithSeparator2(String strHttpMethod, String url){
        if(url.indexOf(HTTPMETHOD_SEPARATOR_ACTION_URL) != -1){
            return url;
        }
        return strHttpMethod + HTTPMETHOD_SEPARATOR_ACTION_URL + url;
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: httpMethod 和 url 加分隔符组合
     */
    public static String composeWithSeparator(HttpMethod httpMethod, String url){
        if(url.indexOf(HTTPMETHOD_SEPARATOR_ACTION_URL) != -1){
            return url;
        }
        return httpMethod.name() + HTTPMETHOD_SEPARATOR_ACTION_URL + url;
    }

}
