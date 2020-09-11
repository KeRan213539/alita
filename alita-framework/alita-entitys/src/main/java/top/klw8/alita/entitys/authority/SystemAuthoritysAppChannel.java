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
 * 应用渠道表.
 *
 * @author klw(213539 @ qq.com)
 * @ClassName: SystemAuthoritysAppChannel
 * @date 2020/9/9 16:51
 */
@TableName(value = "sys_authoritys_app_channel")
@Getter
@Setter
@ToString
public class SystemAuthoritysAppChannel implements Serializable, Cloneable {
    
    private static final long serialVersionUID = -3145989952679035685L;
    
    /**
     * @author klw(213539@qq.com)
     * @Description: 渠道标识(主键)
     */
    @TableId(type = IdType.INPUT)
    @TableField("channel_tag")
    private String channelTag;
    
    /**
     * @author klw(213539@qq.com)
     * @Description: 渠道所属应用的标识
     */
    @TableField("app_tag")
    private String appTag;
    
    /**
     * 渠道密码(明文)
     * @author klw(213539@qq.com)
     */
    @TableField("channel_pwd")
    private String channelPwd;
    
    /**
     * 渠道支持的登录方式,多种方式用逗号隔开
     * @author klw(213539@qq.com)
     */
    @TableField("channel_login_type")
    private String channelLoginType;
    
    /**
     * @author klw(213539@qq.com)
     * @Description: 备注
     */
    @TableField("remark")
    private String remark;

}
