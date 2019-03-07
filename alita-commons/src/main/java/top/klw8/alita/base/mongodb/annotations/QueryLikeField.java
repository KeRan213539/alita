package top.klw8.alita.base.mongodb.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: QueryLikeField
 * @Description: 用于标注需要模糊查询的字段
 * @author klw
 * @date 2018年10月2日 下午4:55:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface QueryLikeField {

}
