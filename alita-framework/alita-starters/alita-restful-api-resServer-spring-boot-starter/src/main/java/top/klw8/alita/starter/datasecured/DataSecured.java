package top.klw8.alita.starter.datasecured;

import java.lang.annotation.*;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DataSecured
 * @Description: 数据权限注解, 数据权限与url权限相对独立,只要使用了该注解就会验证,不管在 alita.oauth2.resServer.authPath 中是否配制了url
 * @date 2020/4/24 14:28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface DataSecured {

    /**
     * @author klw(213539@qq.com)
     * @Description: 资源名称
     */
    String[] resource() default {};

    /**
     * @author klw(213539@qq.com)
     * @Description: 资源解析器,如果配制了该属性则忽略资源名称属性.
     */
    Class<? extends IResourceParser> parser() default IResourceParser.class;

    /**
     * @author klw(213539@qq.com)
     * @Description: 是否是文件上传,默认false
     */
    boolean fileUpload() default false;

}