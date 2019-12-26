package top.klw8.alita.web.user.vo;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.validator.annotations.MobilePhoneNumber;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Password;
import top.klw8.alita.validator.annotations.Required;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AlitaUserAccountRequest
 * @Description: 账户Bean
 * @date 2019/10/15 15:18
 */
@Getter
@Setter
public class AlitaUserAccountRequest {

    @ApiParam(value = "用户名/帐号", required = true)
    @Required("用户名/帐号不能为空")
    @NotEmpty("用户名/帐号不能为空")
    private String userName;

    @ApiParam(value = "用户手机号", required = true)
    @Required("用户手机号不能为空")
    @NotEmpty("用户手机号不能为空")
    @MobilePhoneNumber
    private String userPhoneNum;

    @ApiParam(value = "密码", required = true)
    @Required("密码不能为空")
    @NotEmpty("密码不能为空")
    @Password
    private String userPwd;

    @ApiParam(value = "重复密码", required = true)
    @Required("重复密码不能为空")
    @NotEmpty("重复密码不能为空")
    @Password
    private String userPwd2;

}