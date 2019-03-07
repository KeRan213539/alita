package top.klw8.alita.validator;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: ValidatorImpl
 * @Description: 用于标注验证器的实现
 * @author klw
 * @date 2018年12月13日 下午4:23:52
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface ValidatorImpl {

    /**
     * @Title: validator
     * @author klw
     * @Description: 标注该实现对应的验证器注解
     * @return
     */
    Class<? extends Annotation>[] validator();
    
}
