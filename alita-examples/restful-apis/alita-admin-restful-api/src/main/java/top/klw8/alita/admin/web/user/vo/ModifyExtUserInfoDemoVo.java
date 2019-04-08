package top.klw8.alita.admin.web.user.vo;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.user.ExtUserInfoDemo;
import top.klw8.alita.starter.web.base.vo.IBaseCrudVo;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

/**
 * @ClassName: ModifyNormalUserInfoVo
 * @Description: 修改前台用户(客户)信息
 * @author klw
 * @date 2019年1月31日 上午11:19:51
 */
@Getter
@Setter
public class ModifyExtUserInfoDemoVo implements IBaseCrudVo<ExtUserInfoDemo> {

    private static final long serialVersionUID = -4302258047755427837L;

    @ApiModelProperty(value = "要修改的客户的ID", required=true)
    @Required(validatFailMessage = "要修改的客户的ID不能为空")
    @NotEmpty(validatFailMessage = "要修改的客户的ID不能为空")
    private Long id;
    
    @ApiModelProperty(value = "头像")
    private String faceImg;
    
    @ApiModelProperty(value = "昵称")
    private String nickName;
    
    @ApiModelProperty(value = "姓名")
    @Required(validatFailMessage = "姓名不能为空")
    @NotEmpty(validatFailMessage = "姓名不能为空")
    private String realName;
    
    @ApiModelProperty(value = "联系电话")
    @Required(validatFailMessage = "联系电话不能为空")
    @NotEmpty(validatFailMessage = "联系电话不能为空")
    private String phoneNumber;
    
    @ApiModelProperty(value = "生日")
    private LocalDate birthday;
    
    @Override
    public ExtUserInfoDemo buildEntity() {
	ExtUserInfoDemo user = new ExtUserInfoDemo();
	BeanUtils.copyProperties(this, user);
	return user;
    }
    
}
