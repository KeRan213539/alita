package top.klw8.alita.entitys.authority;

import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.base.BaseEntity;

/**
 * @ClassName: SystemRole
 * @Description: 系统角色
 * @author klw
 * @date 2018年11月28日 上午11:54:15
 */
@Document(collection = "sys_role")
@Getter
@Setter
//@EqualsAndHashCode(callSuper = false, exclude = {"authorityList"})
//@ToString(callSuper = false, exclude ={"authorityList"})
public class SystemRole extends BaseEntity {

    private static final long serialVersionUID = -2919173399468066019L;
    
    /**
     * @author klw
     * @Fields roleName : 角色名称
     */
    private String roleName;
    
    /**
     * @author klw
     * @Fields remark : 备注
     */
    private String remark;
    
    /**
     * @author klw
     * @Fields authorityList : 角色下的权限(冗余数据)
     */
    @Indexed
    private List<SystemAuthoritys> authorityList;
    
}
