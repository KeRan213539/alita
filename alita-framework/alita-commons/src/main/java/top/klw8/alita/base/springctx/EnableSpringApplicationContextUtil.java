package top.klw8.alita.base.springctx;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import org.springframework.context.annotation.Import;

/**
 * @ClassName: EnableSpringApplicationContextUtil
 * @Description: 开启 EnableSpringApplicationContextUtil
 * @author klw
 * @date 2018年9月17日 下午1:57:41
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = SpringApplicationContextUtilSelector.class)
public @interface EnableSpringApplicationContextUtil {

}
