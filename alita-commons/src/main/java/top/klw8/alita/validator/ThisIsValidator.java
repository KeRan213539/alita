package top.klw8.alita.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;

/**
 * @ClassName: ThisIsValidator
 * @Description: 验证器的注解,用于标注某个注解为【验证器注解】<br />
 * 【注意】此注解仅供【验证器注解】使用
 * @author klw
 * @date 2018年9月17日 13:07:14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
@Documented
public @interface ThisIsValidator {

}
