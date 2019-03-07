package top.klw8.alita.validator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

import top.klw8.alita.validator.ThisIsValidator;


/**
 * @ClassName: MobilePhoneNumber
 * @Description: 手机号格式验证器
 * @author klw
 * @date 2018-11-22 16:54:26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@Inherited  // 子类可以继承父类的注解
@ThisIsValidator
public @interface MobilePhoneNumber {

    @AliasFor("validatFailMessage")
    String value() default "手机号格式不正确";
    
    /**
     * @Title: responseStatusCode
     * @Description: 验证失败(不通过)的code
     * @return
     */
    String responseStatusCode() default "500";
    
    /**
     * @Title: validatFailMessage
     * @Description: 验证失败(不通过)的文字消息,可为空,默认使用ResponseStatusCodeEnum对应的消息
     * @return
     */
    @AliasFor("value")
    String validatFailMessage() default "手机号格式不正确";
    
    
}
