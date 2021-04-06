package top.klw8.alita.entitys.authority;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.base.BaseEntity;

import java.util.List;

/**
 * @ClassName: SystemAuthoritysCatlog
 * @Description: 权限的目录
 * @author klw
 * @date 2018年11月28日 上午11:53:50
 */
@TableName("sys_authoritys_catlog")
@Getter
@Setter
//@EqualsAndHashCode(callSuper = false, exclude = {"authorityList"})
//@ToString(callSuper = false, exclude ={"authorityList"})
public class SystemAuthoritysCatlog extends BaseEntity implements IAssociatedApp {

    private static final long serialVersionUID = -8415317762418810314L;

    /**
     * @author klw
     * @Fields authorityName : 所属应用的应用标识
     */
    @TableField("app_tag")
    private String appTag;

    /**
     * @author klw
     * @Fields catlogName : 目录名称
     */
    @TableField("catlog_name")
    private String catlogName;
    
    /**
     * @author klw
     * @Fields showIndex : 显示顺序
     */
    @TableField("show_index")
    private Integer showIndex;
    
    /**
     * @author klw
     * @Fields remark : 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * @author klw
     * @Fields authorityList : 目录下的权限(冗余数据)
     */
    @TableField(exist=false)
    private List<SystemAuthoritys> authorityList;
    
}
