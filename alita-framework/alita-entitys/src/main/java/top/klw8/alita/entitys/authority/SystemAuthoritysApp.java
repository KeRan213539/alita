package top.klw8.alita.entitys.authority;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: SystemAuthoritysApp
 * @Description: 权限应用信息表实体
 * @date 2020/7/16 15:23
 */
@TableName(value = "sys_authoritys_app")
@Getter
@Setter
@ToString
public class SystemAuthoritysApp implements Serializable, Cloneable{

    private static final long serialVersionUID = -3145989952679035685L;

    /**
     * @author klw(213539@qq.com)
     * @Description: 应用标识(主键)
     */
    @TableId(type = IdType.INPUT)
    @TableField("app_tag")
    private String appTag;

    /**
     * @author klw(213539@qq.com)
     * @Description: 应用名称
     */
    @TableField("app_name")
    private String appName;

    /**
     * @author klw(213539@qq.com)
     * @Description: 备注
     */
    @TableField("remark")
    private String remark;

}
