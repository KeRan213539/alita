package top.klw8.alita.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: UseValidator
 * @Description: 使用注解验证器,在需要验证的Controller方法上加上该注解
 * @author klw
 * @date 2018年9月17日 下午4:56:17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface UseValidator {

}
