package top.klw8.alita.entitys.authority;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.base.BaseEntity;

/**
 * @ClassName: SystemAuthoritys
 * @Description: 整个系统的所有功能权限(WEB_API),包括前台后台
 * @author klw
 * @date 2018年11月28日 上午11:52:04
 */
@Document(collection = "sys_authoritys")
@Getter
@Setter
//@EqualsAndHashCode(callSuper = false, exclude = {"catlog"})
//@ToString(callSuper = false, exclude ={"catlog"})
public class SystemAuthoritys extends BaseEntity {
    
    private static final long serialVersionUID = 4226666111547632644L;

    /**
     * @author klw
     * @Fields catlog : 权限所属权限目录
     */
    @Indexed
    private SystemAuthoritysCatlog catlog;
    
    /**
     * @author klw
     * @Fields authorityName : 权限名称
     */
    private String authorityName;

    /**
     * @author klw
     * @Fields authorityUrl : 权限动作,根据类型来,如果是url那就放url,如果是菜单就放前端识别的视图标识(相对路径)
     */
    private String authorityAction;
    
    /**
     * @author klw
     * @Fields authorityType : 权限类型
     */
    private AuthorityTypeEnum authorityType;
    
    /**
     * @author klw
     * @Fields showIndex : 作为菜单的显示顺序,非菜单为0
     */
    private Integer showIndex;
    
    /**
     * @author klw
     * @Fields remark : 备注
     */
    private String remark;
    
}
