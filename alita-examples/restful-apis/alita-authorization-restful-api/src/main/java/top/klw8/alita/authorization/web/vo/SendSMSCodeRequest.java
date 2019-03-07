package top.klw8.alita.authorization.web.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import top.klw8.alita.validator.annotations.MobilePhoneNumber;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

/**
 * @ClassName: SendSMSCodeRequest
 * @Description: 发送短信验证码接口请求类
 * @author klw
 * @date 2018年11月22日 下午4:33:41
 */
@Data
public class SendSMSCodeRequest {

    @ApiParam(value = "用户手机号", required = true)
    @Required(validatFailMessage="手机号为必传参数")
    @NotEmpty(validatFailMessage="手机号不能为空")
    @MobilePhoneNumber(validatFailMessage="手机号格式不正确")
    private String userMobileNo;
    
}
