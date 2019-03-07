package top.klw8.alita.admin.web.user.vo;


import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.user.StaffInfo;
import top.klw8.alita.entitys.user.enums.UserGendersEnum;
import top.klw8.alita.service.common.GeoPoint;
import top.klw8.alita.starter.web.base.vo.IBaseCrudVo;
import top.klw8.alita.validator.annotations.GeoLatitude;
import top.klw8.alita.validator.annotations.GeoLongitude;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

/**
 * @ClassName: StaffInfo
 * @Description: 新增行政员工信息 VO
 * @author klw
 * @date 2019-01-25 16:25:44
 */
@Getter
@Setter
public class ModifyStaffInfoVo implements IBaseCrudVo<StaffInfo> {

    @ApiModelProperty(value = "要修改的员工的ID", required=true)
    @Required(validatFailMessage = "要修改的员工的ID不能为空")
    @NotEmpty(validatFailMessage = "要修改的员工的ID不能为空")
    private Long id;
    
    @ApiModelProperty(value = "照片路径")
    private String photoUrl;

    @ApiModelProperty(value = "部门ID", required=true)
    private Long staffOrgId;

    @ApiModelProperty(value = "区域ID", required=true)
    private Long staffRegionId;
    
    @ApiModelProperty(value = "岗位(角色)ID", required=true)
    private Long roleId;

    @ApiModelProperty(value = "所属城市", required=true)
    private String cityCode;
    
    @ApiModelProperty(value = "人员姓名", required=true)
    private String realName;
    
    @ApiModelProperty(value = "联系电话", required=true)
    private String phoneNumber;

    @ApiModelProperty(value = "性别: <br />"
	    	+ "男: MALE  <br />"
	    	+ "女: FEMALE  <br />"
	    	+ "保密: SECRECY")
    private UserGendersEnum userGender;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "地址")
    private String address;
    
    @ApiModelProperty(value = "地址对应的经度")
    @GeoLongitude
    private Double addressLongitude;
    
    @ApiModelProperty(value = "地址对应的纬度")
    @GeoLatitude
    private Double addressLatitude;

    @ApiModelProperty(value = "身份证")
    private String idCardNo;

    @ApiModelProperty(value = "银行卡号")
    private String bankCardNo;

    @ApiModelProperty(value = "入职时间")
    private LocalDateTime entryTime;

    @ApiModelProperty(value = "转正时间")
    private LocalDateTime correctionTime;

    @ApiModelProperty(value = "离职时间")
    private LocalDateTime DepartureTime;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "毕业院校")
    private String graduationSchool;

    @ApiModelProperty(value = "政治面貌")
    private String politicalOutlook;

    @ApiModelProperty(value = "婚姻状况: <br />"
	    	+ "MARRIED: 已婚  <br />"
	    	+ "UNMARRIED: 未婚  <br />"
	    	+ "DIVORCE: 离异")
    private String maritalStatus;

    @ApiModelProperty(value = "是否储备岗")
    private Boolean reserve;

    @ApiModelProperty(value = "是否购买社保")
    private Boolean buySocialInsurance;

    @ApiModelProperty(value = "社保登记号")
    private String socialInsuranceNo;

    @ApiModelProperty(value = "是否购买商业保险")
    private Boolean buyCommercialInsurance;

    @ApiModelProperty(value = "紧急联系人")
    private String emergencyContacName;

    @ApiModelProperty(value = "紧急联系人电话")
    private String emergencyContacPhoneNo;

    @ApiModelProperty(value = "推荐人")
    private String recommender;

    @ApiModelProperty(value = "星座")
    private String constellation;

    @ApiModelProperty(value = "身高(cm)")
    private String height;

    @ApiModelProperty(value = "体重(kg)")
    private String weight;

    @ApiModelProperty(value = "健康情况")
    private String health;

    @ApiModelProperty(value = "兴趣爱好")
    private String interests;

    @ApiModelProperty(value = "其他特长")
    private String otherSpeciality;

    @ApiModelProperty(value = "培训经历及证书")
    private String trainingExperienceAndCertificate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @Override
    public StaffInfo buildEntity() {
	StaffInfo staff = new StaffInfo();
	BeanUtils.copyProperties(this, staff);
	if(addressLatitude != null && addressLongitude != null) {
	    staff.setAddressLocation(new GeoPoint(addressLatitude, addressLongitude));
	}
	return staff;
    }
    

}
