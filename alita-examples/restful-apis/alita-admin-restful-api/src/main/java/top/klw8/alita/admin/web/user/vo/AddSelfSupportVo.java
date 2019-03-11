package top.klw8.alita.admin.web.user.vo;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.user.StaffInfo;
import top.klw8.alita.entitys.user.enums.StaffStateEnum;
import top.klw8.alita.entitys.user.enums.StaffTypeEnum;
import top.klw8.alita.entitys.user.enums.UserGendersEnum;
import top.klw8.alita.service.common.GeoPoint;
import top.klw8.alita.starter.web.base.vo.IBaseCrudVo;
import top.klw8.alita.validator.annotations.FloatRange;
import top.klw8.alita.validator.annotations.GeoLatitude;
import top.klw8.alita.validator.annotations.GeoLongitude;
import top.klw8.alita.validator.annotations.IntegerRange;
import top.klw8.alita.validator.annotations.MobilePhoneNumber;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

/**
 * @ClassName: AddSelfSupportVo
 * @Description: 新增自营人员 VO
 * @author klw
 * @date 2019-01-25 16:25:44
 */
@Getter
@Setter
public class AddSelfSupportVo implements IBaseCrudVo<StaffInfo> {

    
    private static final long serialVersionUID = -6611801551767285533L;

    @ApiModelProperty(value = "照片路径")
    private String photoUrl;

    @ApiModelProperty(value = "区域ID", required=true)
    @Required(validatFailMessage = "区域ID不能为空")
    private String staffRegionId;

    @ApiModelProperty(value = "所属城市", required=true)
    @Required(validatFailMessage = "所属城市不能为空")
    @NotEmpty(validatFailMessage = "所属城市不能为空")
    private String cityCode;
    
    @ApiModelProperty(value = "人员姓名", required=true)
    @Required(validatFailMessage = "人员姓名不能为空")
    @NotEmpty(validatFailMessage = "人员姓名不能为空")
    private String realName;
    
    @ApiModelProperty(value = "联系电话", required=true)
    @Required(validatFailMessage = "联系电话不能为空")
    @NotEmpty(validatFailMessage = "联系电话不能为空")
    @MobilePhoneNumber
    private String phoneNumber;
    
    @ApiModelProperty(value = "职业: <br />"
	    	+ "管家: HOUSEKEEPER  <br />"
	    	+ "月嫂: PROFESSIONAL_CONFINEMENT_LADY", required=true)
    @Required(validatFailMessage = "职业不能为空")
    private StaffTypeEnum staffType;

    @ApiModelProperty(value = "性别: <br />"
	    	+ "男: MALE  <br />"
	    	+ "女: FEMALE  <br />"
	    	+ "保密: SECRECY")
    private UserGendersEnum userGender;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "地址")
    private String address;
    
    @ApiModelProperty(value = "地址对应的经度", required=true)
    @Required(validatFailMessage = "地址对应的经度不能为空")
    @GeoLongitude
    private Double addressLongitude;
    
    @ApiModelProperty(value = "地址对应的纬度", required=true)
    @Required(validatFailMessage = "地址对应的纬度不能为空")
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
    
    @ApiModelProperty(value = "服务类型的ID", required=true)
    @Required(validatFailMessage = "服务类型的ID不能为空")
    @NotEmpty(validatFailMessage = "服务类型的ID不能为空")
    private List<String> serviceTypeIds;
    
    @ApiModelProperty(value = "职业")
    private String occupation;
    
    @ApiModelProperty(value = "是否无声")
    private Boolean silent;
    
    @ApiModelProperty(value = "生日(yyyy-MM-dd)")
    private LocalDate birthday;
    
    @ApiModelProperty(value = "星级(1~5)")
    @IntegerRange(min = 1, max = 5, validatFailMessage="星级只能是1到5之间的整数")
    private Integer star;
    
    @ApiModelProperty(value = "评分(1~10)")
    @FloatRange(min = 0.1F, max = 10F, validatFailMessage="评分只能是1到10之间的数字")
    private Float score;
    
    @ApiModelProperty(value = "是否跨区")
    private Boolean spanningArea;
    
    @ApiModelProperty(value = "每月可休息几天")
    private Integer restDayOfMonth;
    
    @ApiModelProperty(value = "保底工资")
    private BigDecimal minimumWage;
    
    @ApiModelProperty(value = "技能工资")
    private BigDecimal skillWage;
    
    @ApiModelProperty(value = "体检报告编号")
    private String healthCheckupReportNum;
    
    @ApiModelProperty(value = "体检报告的文件路径")
    private String healthCheckupFileUrl;
    
    @ApiModelProperty(value = "其他附件的文件路径")
    private String otherAttachmentFileUrl;

    /*
     * <p>Title: buildEntity</p>
     * @author klw
     * <p>Description: </p>
     * @return
     * @see top.klw8.alita.starter.web.base.vo.IBaseCrudVo#buildEntity()
     */
    @Override
    public StaffInfo buildEntity() {
	StaffInfo staff = new StaffInfo();
	BeanUtils.copyProperties(this, staff);
	staff.setAddressLocation(new GeoPoint(addressLatitude, addressLongitude));
	staff.setStaffState(StaffStateEnum.INCUMBENCY);
	return staff;
    }
    

}
