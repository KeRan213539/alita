package top.klw8.alita.starter.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: AuthorityCatlogRegister
 * @Description: 将权限目录写入到数据库中,需要通过 /devHelper/registeAllAuthority 注册
 * 相关参数含义可参考 {@link top.klw8.alita.entitys.authority.SystemAuthoritysCatlog}
 * @author klw
 * @date 2018年12月11日 下午1:26:24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface AuthorityCatlogRegister {

    /**
     * @Title: name
     * @author klw
     * @Description: 菜单所属权限目录的名称,如果不存在自动创建
     * @return
     */
    String name();
    
    /**
     * @Title: showIndex
     * @author klw
     * @Description: 菜单的显示顺序
     * @return
     */
    int showIndex();
    
    /**
     * @Title: remark
     * @author klw
     * @Description: 菜单备注,可不设置
     * @return
     */
    String remark() default "";
    
}
