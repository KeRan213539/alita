package top.klw8.alita.entitys.authority;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.base.BaseEntity;

import java.util.List;

/**
 * @ClassName: SystemAuthoritys
 * @Description: 整个系统的所有功能权限(WEB_API),包括前台后台
 * @author klw
 * @date 2018年11月28日 上午11:52:04
 */
@TableName("sys_authoritys")
@Getter
@Setter
@ToString
public class SystemAuthoritys extends BaseEntity {
    
    private static final long serialVersionUID = 4226666111547632644L;

    /**
     * @author klw
     * @Fields catlog : 权限所属权限目录
     */
    @TableField(exist=false)
    private SystemAuthoritysCatlog catlog;

    /**
     * @author klw
     * @Fields authorityName : 权限名称
     */
    @TableField("authority_name")
    private String authorityName;

    /*
     * @author klw(213539@qq.com)
     * @Description: 权限所属目录的ID
     */
    @TableField("catlog_id")
    private String catlogId;

    /**
     * @author xp
     * @Description: 权限组名称，冗余字段
     */
    @TableField(value="catlog_name",exist=false)
    private String catlogName;

    /**
     * @author klw
     * @Fields authorityUrl : 权限动作,根据类型来,如果是url那就放url,如果是菜单就放前端识别的视图标识(相对路径)
     */
    @TableField("authority_action")
    private String authorityAction;
    
    /**
     * @author klw
     * @Fields authorityType : 权限类型
     */
    @TableField("authority_type")
    private AuthorityTypeEnum authorityType;
    
    /**
     * @author klw
     * @Fields showIndex : 作为菜单的显示顺序,非菜单为0
     */
    @TableField("show_index")
    private Integer showIndex;
    
    /**
     * @author klw
     * @Fields remark : 备注
     */
    @TableField("remark")
    private String remark;
    
}
