package top.klw8.alita.entitys.demo.mybatis;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.entitys.base.BaseEntity;

import java.util.Date;

/**
 * @author freedom
 * @version 1.0
 * @ClassName ExtUserInfo
 * @Description 用户扩展信息表实体
 * @date 2019-08-19 10:43
 */
@TableName("ext_user_info")
@Getter
@Setter
@ToString
public class ExtUserInfo extends BaseEntity {

    private static final long serialVersionUID = 6884327869246437788L;

    /**
     * @Author zhanglei
     * @Description 所属用户ID
     * @Date 15:45 2019-08-19
     **/
    @TableField("user_id")
    private String userId;

    /**
     * @Author zhanglei
     * @Description 用户邮箱地址
     * @Date 14:20 2019-08-19
     **/
    @TableField("user_email")
    private String userEmail;

    /**
     * @Author zhanglei
     * @Description 用户昵称
     * @Date 14:21 2019-08-19
     **/
    @TableField("nick_name")
    private String nickName;

    /**
     * @Author zhanglei
     * @Description 用户年龄
     * @Date 14:22 2019-08-19
     **/
    @TableField("user_age")
    private Integer userAge;

    /**
     * @Author zhanglei
     * @Description 用户生日
     * @Date 14:23 2019-08-19
     **/
    @TableField("user_birth")
    private Date userBirth;

    /**
     * @Author zhanglei
     * @Description 用户自我介绍
     * @Date 14:25 2019-08-19
     **/
    @TableField("user_desc")
    private String userDesc;

    /**
     * @Author zhanglei
     * @Description 用户等级
     * @Date 14:43 2019-08-19
     **/
    @TableField("user_level")
    private Integer userLevel;

}