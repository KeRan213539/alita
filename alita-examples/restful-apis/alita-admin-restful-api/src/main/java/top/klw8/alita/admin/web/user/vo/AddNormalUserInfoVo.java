package top.klw8.alita.admin.web.user.vo;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.user.NormalUserInfo;
import top.klw8.alita.entitys.user.enums.NormalUserClassifyEnum;
import top.klw8.alita.entitys.user.enums.NormalUserLevelEnum;
import top.klw8.alita.entitys.user.enums.NormalUserSourceEnum;
import top.klw8.alita.entitys.user.enums.NormalUserTypeEnum;
import top.klw8.alita.entitys.user.enums.UserGendersEnum;
import top.klw8.alita.starter.web.base.vo.IBaseCrudVo;
import top.klw8.alita.validator.annotations.MobilePhoneNumber;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

/**
 * @ClassName: NormalUserInfo
 * @Description: 新增前台用户(客户)信息 VO
 * @author klw
 * @date 2019-01-25 16:26:01
 */
@Getter
@Setter
public class AddNormalUserInfoVo implements IBaseCrudVo<NormalUserInfo> {

    @ApiModelProperty(value = "头像")
    private String faceImg;
    
    @ApiModelProperty(value = "昵称")
    private String nickName;
    
    @ApiModelProperty(value = "姓名", required=true)
    @Required(validatFailMessage = "姓名不能为空")
    @NotEmpty(validatFailMessage = "姓名不能为空")
    private String realName;
    
    @ApiModelProperty(value = "联系电话", required=true)
    @Required(validatFailMessage = "联系电话不能为空")
    @NotEmpty(validatFailMessage = "联系电话不能为空")
    @MobilePhoneNumber
    private String phoneNumber;
    
    @ApiModelProperty(value = "客户性别: <br />"
	    	+ "男: MALE  <br />"
	    	+ "女: FEMALE  <br />"
	    	+ "保密: SECRECY")
    private UserGendersEnum userGender;
    
    @ApiModelProperty(value = "客户等级: <br />"
	    	+ "VIP: VIP  <br />"
	    	+ "普通: NORMAL")
    private NormalUserLevelEnum level;
    
    @ApiModelProperty(value = "生日")
    private LocalDate birthday;
    
    @ApiModelProperty(value = "客户分类: <br />"
	    	+ "自然上门: VISIT  <br />"
	    	+ "营销: MARKETING  <br />"
	    	+ "活动: ACTIVITY")
    private NormalUserClassifyEnum classify;
    
    @ApiModelProperty(value = "客户来源: <br />"
	    	+ "TV: 电视  <br />"
	    	+ "ROAD_SIGNS: 路牌  <br />"
	    	+ "RADIO: 广播  <br />"
	    	+ "ACTIVITY: 活动  <br />"
	    	+ "OTHER: 其他")
    private NormalUserSourceEnum source;
    
    @ApiModelProperty(value = "客户的类型: <br />"
	    	+ "COMPLAINT: 投诉客户  <br />"
	    	+ "BIRTH: 生日客户  <br />"
	    	+ "RESERVE: 储备客户  <br />"
	    	+ "OBSTACLE: 障碍客户")
    private NormalUserTypeEnum userType;

    @Override
    public NormalUserInfo buildEntity() {
	NormalUserInfo user = new NormalUserInfo();
	BeanUtils.copyProperties(this, user);
	return user;
    }
    

}
