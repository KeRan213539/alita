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
 * @ClassName: NotEmpty
 * @Description: 验证String和集合不能为空,为空则不通过(String判断有字符,集合判断不为空等) <br />
 * 目前支持: String,数组,集合,Map <br />
 * 【注意】此注解不验证是否为null,如果为null为通过,需要不为null(必传参数)请使用@Required
 * @author klw
 * @date 2018年9月17日 13:08:44
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@Inherited  // 子类可以继承父类的注解
@ThisIsValidator
public @interface NotEmpty {

    @AliasFor("validatFailMessage")
    String value() default "";
    
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
    String validatFailMessage();
    
}
