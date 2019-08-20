package top.klw8.alita.demo.web.demo.vo;

import java.time.LocalDate;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.demo.mongo.ExtUserInfoDemo;
import top.klw8.alita.starter.web.base.vo.IBaseCrudVo;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

/**
 * @author klw
 * @ClassName: ModifyNormalUserInfoVo
 * @Description: 修改前台用户(客户)信息
 * @date 2019年1月31日 上午11:19:51
 */
@Getter
@Setter
public class ModifyExtUserInfoDemoVo implements IBaseCrudVo<ExtUserInfoDemo> {

    private static final long serialVersionUID = -4302258047755427837L;

    @ApiParam(value = "要修改的客户的ID", required = true)
    @Required(validatFailMessage = "要修改的客户的ID不能为空")
    @NotEmpty(validatFailMessage = "要修改的客户的ID不能为空")
    private Long id;

    @ApiParam(value = "头像")
    private String faceImg;

    @ApiParam(value = "昵称")
    private String nickName;

    @ApiParam(value = "姓名")
    @Required(validatFailMessage = "姓名不能为空")
    @NotEmpty(validatFailMessage = "姓名不能为空")
    private String realName;

    @ApiParam(value = "联系电话")
    @Required(validatFailMessage = "联系电话不能为空")
    @NotEmpty(validatFailMessage = "联系电话不能为空")
    private String phoneNumber;

    @ApiParam(value = "生日")
    private LocalDate birthday;

    @Override
    public ExtUserInfoDemo buildEntity() {
        ExtUserInfoDemo user = new ExtUserInfoDemo();
        BeanUtils.copyProperties(this, user);
        return user;
    }

}
