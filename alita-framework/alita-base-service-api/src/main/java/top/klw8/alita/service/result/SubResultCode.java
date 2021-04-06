package top.klw8.alita.service.result;

import java.lang.annotation.*;

/**
 * @author klw
 * @ClassName: SubResultCode
 * @Description: 业务ResultCode需要使用此注解
 * @date 2019/6/6 17:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface SubResultCode {
}
