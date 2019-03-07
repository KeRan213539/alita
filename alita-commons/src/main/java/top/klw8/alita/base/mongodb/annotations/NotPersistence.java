package top.klw8.alita.base.mongodb.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: NotPersistence
 * @Description: 不保存到数据库的字段
 * @author klw
 * @date 2018年10月11日 下午4:08:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface NotPersistence {

}
