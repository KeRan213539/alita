package top.klw8.alita.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;

/**
 * @ClassName: EnableValidator
 * @Description: 开启省验证器
 * @author klw
 * @date 2018年9月17日 13:07:19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(value = EnableValidatorSelector.class)
public @interface EnableValidator {

    Class<? extends IResponseMsgGenerator> responseMsgGenerator();
    
}
