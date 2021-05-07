package top.klw8.alita.starter.annotations;

import java.lang.annotation.*;

/**
 * 全局资源权限
 * 2020/5/14 10:22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Documented
public @interface PublicAuthoritysResourceRegister {
}
