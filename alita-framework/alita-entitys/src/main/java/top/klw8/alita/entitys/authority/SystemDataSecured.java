package top.klw8.alita.entitys.authority;

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

    @TableField("authoritys_id")
    private String authoritysId;

    @TableField("resource")
    private String resource;

    @TableField("remark")
    private String remark;

    @TableField(value = "authority_url", exist = false)
    private String authorityUrl;

}
