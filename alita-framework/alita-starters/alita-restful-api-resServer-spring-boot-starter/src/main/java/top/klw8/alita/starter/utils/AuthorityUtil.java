package top.klw8.alita.starter.utils;

import org.apache.commons.lang3.ArrayUtils;
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
        Class<?> controllerClass = handlerMethod.getBeanType();
        if (controllerClass.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping mapping = controllerClass.getAnnotation(RequestMapping.class);
            authorityActionPrefix = mapping.value()[0];
        } else {
            authorityActionPrefix = "";
        }
        if (method.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping mapping = method.getAnnotation(RequestMapping.class);
            authorityAction = authorityActionPrefix + AuthorityUtil.getStringArrayFirst(mapping.value());
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            PostMapping mapping = method.getAnnotation(PostMapping.class);
            authorityAction = authorityActionPrefix + AuthorityUtil.getStringArrayFirst(mapping.value());
        } else if (method.isAnnotationPresent(GetMapping.class)) {
            GetMapping mapping = method.getAnnotation(GetMapping.class);
            authorityAction = authorityActionPrefix + AuthorityUtil.getStringArrayFirst(mapping.value());
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            PutMapping mapping = method.getAnnotation(PutMapping.class);
            authorityAction = authorityActionPrefix + AuthorityUtil.getStringArrayFirst(mapping.value());
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            DeleteMapping mapping = method.getAnnotation(DeleteMapping.class);
            authorityAction = authorityActionPrefix + AuthorityUtil.getStringArrayFirst(mapping.value());
        } else {
            authorityAction = authorityActionPrefix;
        }
        return authorityAction;
    }

    public static String getStringArrayFirst(String[] strArr){
        if(ArrayUtils.isEmpty(strArr)){
            return "";
        } else {
            return strArr[0];
        }
    }

}
