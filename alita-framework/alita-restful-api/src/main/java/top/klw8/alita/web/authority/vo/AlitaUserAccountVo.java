package top.klw8.alita.web.authority.vo;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.validator.annotations.MobilePhoneNumber;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Password;
import top.klw8.alita.validator.annotations.Required;

/**
 * @author klw
 * @ClassName: AlitaUserAccountVo
 * @Description: 用户信息
 * @date 2019-01-25 16:26:07
 */
@Getter
@Setter
public class AlitaUserAccountVo {

    private static final long serialVersionUID = 7921978011249125659L;

    @ApiParam(value = "用户手机号", required = true)
    @Required(validatFailMessage = "用户手机号不能为空")
    @NotEmpty(validatFailMessage = "用户手机号不能为空")
    @MobilePhoneNumber
    private String userPhoneNum;

    @ApiParam(value = "用户密码", required = true)
    @Required(validatFailMessage = "用户密码不能为空")
    @NotEmpty(validatFailMessage = "用户密码不能为空")
    @Password
    private String userPwd;

}
