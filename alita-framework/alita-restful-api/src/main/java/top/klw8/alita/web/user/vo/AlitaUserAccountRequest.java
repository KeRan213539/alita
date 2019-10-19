package top.klw8.alita.web.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

import java.time.LocalDateTime;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AlitaUserAccountRequest
 * @Description: 账户Bean
 * @date 2019/10/15 15:18
 */
@Getter
@Setter
public class AlitaUserAccountRequest {

    @ApiModelProperty("用户名/帐号")
    @Required
    @NotEmpty
    private String userName;

    @ApiModelProperty("用户手机号")
    @Required
    @NotEmpty
    private String userPhoneNum;

    @ApiModelProperty("密码")
    @Required
    @NotEmpty
    private String userPwd;

    @ApiModelProperty("重复密码")
    @Required
    @NotEmpty
    private String userPwd2;

    @ApiModelProperty("创建时间Begin")
    private LocalDateTime createDateBegin;

    @ApiModelProperty("创建时间End")
    private LocalDateTime createDateEnd;

    @ApiModelProperty("账户是否未过期(true 是未过期)")
    private Boolean accountNonExpired;

    @ApiModelProperty("账户是否未锁定 (true 是未锁定)")
    private Boolean accountNonLocked;

    @ApiModelProperty("用户密码是否未过期(true 是未过期), 密码过期了会登录失败(需要强制用户修改密码)")
    private Boolean credentialsNonExpired;

    @ApiModelProperty("账户是否启用(true 是启用)")
    private Boolean enabled;

}
