package top.klw8.alita.entitys.authority;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.entitys.base.BaseEntity;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: SystemDataSecured
 * @Description: 数据权限表实体
 * @date 2020/4/30 11:47
 */
@TableName("sys_data_secured")
@Getter
@Setter
@ToString
public class SystemDataSecured extends BaseEntity {

    private static final long serialVersionUID = 4226666111547632645L;

    /**
     * @author klw
     * @Fields authorityName : 所属应用的应用标识
     */
    @TableField("app_tag")
    private String appAag;

    /**
     * @author klw(213539@qq.com)
     * @Description: 所属权限ID
     */
    @TableField(value = "authoritys_id", updateStrategy = FieldStrategy.IGNORED)
    private String authoritysId;

    /**
     * @author klw(213539@qq.com)
     * @Description: 资源标识
     */
    @TableField("resource")
    private String resource;

    /**
     * @author klw(213539@qq.com)
     * @Description: 备注/名称
     */
    @TableField("remark")
    private String remark;

    /**
     * @author klw(213539@qq.com)
     * @Description: 所属权限的权限url,冗余数据,非数据库字段
     */
    @TableField(value = "authority_url", exist = false)
    private String authorityUrl;

}
