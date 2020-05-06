package top.klw8.alita.entitys.authority;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.base.BaseEntity;

/**
 * @ClassName: SystemRole
 * @Description: 系统角色
 * @author klw
 * @date 2018年11月28日 上午11:54:15
 */
@TableName("sys_role")
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
    @TableField("role_name")
    private String roleName;
    
    /**
     * @author klw
     * @Fields remark : 备注
     */
    @TableField("remark")
    private String remark;
    
    /**
     * @author klw
     * @Fields authorityList : 角色下的权限(冗余数据)
     */
    @TableField(exist=false)
    private List<SystemAuthoritys> authorityList;

    /**
     * @author klw(213539@qq.com)
     * @Description: 角色拥有的数据权限
     */
    @TableField(exist=false)
    private List<SystemDataSecured> dataSecuredList;
    
}
