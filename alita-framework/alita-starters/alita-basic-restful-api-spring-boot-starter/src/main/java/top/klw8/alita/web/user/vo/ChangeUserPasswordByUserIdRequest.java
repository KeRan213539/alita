package top.klw8.alita.web.user.vo;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Password;
import top.klw8.alita.validator.annotations.Required;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ChangeUserPasswordByUserIdRequest
 * @Description: 根据ID修改用户密码接口请求
 * @date 2019/10/30 10:56
 */
@Getter
@Setter
public class ChangeUserPasswordByUserIdRequest {

    @ApiParam(value = "userId", required = true)
    @Required
    @NotEmpty
    private String userId;

    @ApiParam(value = "旧密码", required = true)
    @Required
    @NotEmpty
    private String oldPwd;

    @ApiParam(value = "新密码", required = true)
    @Required("新密码不能为空")
    @NotEmpty("新密码不能为空")
    @Password
    private String newPwd;

    @ApiParam(value = "重复新密码", required = true)
    @Required("重复新密码不能为空")
    @NotEmpty("重复新密码不能为空")
    @Password
    private String newPwd2;

}