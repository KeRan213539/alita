package top.klw8.alita.starter.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;


/**
 * @ClassName: AuthorityRegister
 * @Description: 将权限写入到数据库中,需要通过 /devHelper/registeAllAuthority 注册
 * 相关参数含义可参考 {@link top.klw8.alita.entitys.authority.SystemAuthoritys} 
 * {@link top.klw8.alita.entitys.authority.SystemAuthoritysCatlog}
 * @author klw
 * @date 2018年12月7日 下午4:58:53
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface AuthorityRegister {

    /**
     * @Title: catlogName
     * @author klw
     * @Description: 菜单所属权限目录的名称,如果不存在自动创建
     * @return
     */
    String catlogName() default "";

    /**
     * @Title: catlogShowIndex
     * @author klw
     * @Description: 菜单的显示顺序
     * @return
     */
    int catlogShowIndex() default -1;

    /**
     * @Title: catlogRemark
     * @author klw
     * @Description: 菜单备注,可不设置
     * @return
     */
    String catlogRemark() default "";
    
    /**
     * @Title: authorityName
     * @author klw
     * @Description: 权限名称
     * @return
     */
    String authorityName();
    
    /**
     * @Title: authorityType
     * @author klw
     * @Description: 权限类型
     * @return
     */
    AuthorityTypeEnum authorityType();
    
    /**
     * @Title: showIndex
     * @author klw
     * @Description: 作为菜单的显示顺序,非菜单为0
     * @return
     */
    int authorityShowIndex();
    
    /**
     * @Title: authorityRemark
     * @author klw
     * @Description: 权限备注
     * @return
     */
    String authorityRemark() default "";
    
}
