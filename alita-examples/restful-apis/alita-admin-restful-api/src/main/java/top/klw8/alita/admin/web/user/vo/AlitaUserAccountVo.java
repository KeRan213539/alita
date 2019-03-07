package top.klw8.alita.admin.web.user.vo;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.starter.web.base.vo.IBaseCrudVo;
import top.klw8.alita.validator.annotations.MobilePhoneNumber;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Password;
import top.klw8.alita.validator.annotations.Required;

/**
 * @ClassName: AlitaUserAccountVo
 * @Description: 用户信息表,包括前台用户,后台用户
 * @author klw
 * @date 2019-01-25 16:26:07
 */
@Getter
@Setter
public class AlitaUserAccountVo implements IBaseCrudVo<AlitaUserAccount> {
    
    private static final long serialVersionUID = 7921978011249125659L;

    @ApiModelProperty(value = "用户手机号", required=true)
    @Required(validatFailMessage = "用户手机号不能为空")
    @NotEmpty(validatFailMessage = "用户手机号不能为空")
    @MobilePhoneNumber
    private String userPhoneNum;
    
    @ApiModelProperty(value = "用户密码", required=true)
    @Required(validatFailMessage = "用户密码不能为空")
    @NotEmpty(validatFailMessage = "用户密码不能为空")
    @Password
    private String userPwd;

    @Override
    public AlitaUserAccount buildEntity() {
	AlitaUserAccount account = new AlitaUserAccount();
	account.setAccountNonExpired(true);
	account.setAccountNonLocked(true);
	account.setCredentialsNonExpired(true);
	account.setEnabled(true);
	account.setCreateDate(LocalDateTime.now());
	account.setUserPhoneNum(userPhoneNum);
	account.setUserPwd(userPwd);
	return account;
    }

}
