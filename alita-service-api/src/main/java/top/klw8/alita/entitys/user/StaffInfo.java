package top.klw8.alita.entitys.user;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.product.ServiceType;
import top.klw8.alita.entitys.user.enums.StaffStateEnum;
import top.klw8.alita.entitys.user.enums.StaffTypeEnum;
import top.klw8.alita.entitys.user.enums.UserGendersEnum;
import top.klw8.alita.service.base.entitys.BaseEntity;
import top.klw8.alita.service.base.entitys.IGeoSearchSupport;
import top.klw8.alita.service.base.entitys.ITextIndexedCustomSupport;
import top.klw8.alita.service.common.GeoPoint;
import top.klw8.alita.utils.AnalyzerUtil;

/**
 * @ClassName: StaffInfo
 * @Description: 员工信息
 * @author klw
 * @date 2019年1月11日 上午11:00:35
 */
@Document(collection = "alita_staff_info")
@Getter
@Setter
public class StaffInfo extends BaseEntity implements ITextIndexedCustomSupport, IGeoSearchSupport {

    private static final long serialVersionUID = 7020964227116398826L;
    
    /**
     * @author klw
     * @Fields accountInfo : 账户信息
     */
    @DBRef
    @Indexed(unique=true)
    private AlitaUserAccount accountInfo;
    
    /**
     * @author klw
     * @Fields photoUrl : 照片路径
     */
    private String photoUrl;

    /**
     * @author klw
     * @Fields staffOrg : 部门
     */
    private StaffOrg staffOrg;

    /**
     * @author klw
     * @Fields staffRegion : 区域
     */
    private StaffRegion staffRegion;

    /**
     * @author klw
     * @Fields cityCode : 所属城市
     */
    @Indexed
    private String cityCode;
    
    /**
     * @author klw
     * @Fields realName : 人员姓名
     */
    private String realName;
    
    /**
     * @author klw
     * @Fields phoneNumber : 联系电话
     */
    private String phoneNumber;

    /**
     * @author klw
     * @Fields userGender : 性别
     */
    private UserGendersEnum userGender;

    /**
     * @author klw
     * @Fields age : 年龄
     */
    private Integer age;

    /**
     * @author klw
     * @Fields address : 地址
     */
    private String address;
    
    /**
     * @author klw
     * @Fields addressLocation : 地址对应经纬度(纬度/经度)
     */
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoPoint addressLocation;

    /**
     * @author klw
     * @Fields idCardNo : 身份证
     */
    private String idCardNo;

    /**
     * @author klw
     * @Fields bankCardNo : 银行卡号
     */
    private String bankCardNo;

    /**
     * @author klw
     * @Fields entryTime : 入职时间
     */
    private LocalDateTime entryTime;

    /**
     * @author klw
     * @Fields correctionTime : 转正时间
     */
    private LocalDateTime correctionTime;

    /**
     * @author klw
     * @Fields DepartureTime : 离职时间
     */
    private LocalDateTime departureTime;

    /**
     * @author klw
     * @Fields education : 学历
     */
    private String education;

    /**
     * @author klw
     * @Fields major : 专业
     */
    private String major;

    /**
     * @author klw
     * @Fields graduationSchool : 毕业院校
     */
    private String graduationSchool;

    /**
     * @author klw
     * @Fields politicalOutlook : 政治面貌
     */
    private String politicalOutlook;

    /**
     * @author klw
     * @Fields maritalStatus : 婚姻状况
     */
    private String maritalStatus;

    /**
     * @author klw
     * @Fields reserve : 是否储备岗
     */
    private Boolean reserve;

    /**
     * @author klw
     * @Fields buySocialInsurance : 是否购买社保
     */
    private Boolean buySocialInsurance;

    /**
     * @author klw
     * @Fields socialInsuranceNo : 社保登记号
     */
    private String socialInsuranceNo;

    /**
     * @author klw
     * @Fields buyCommercialInsurance : 是否购买商业保险
     */
    private Boolean buyCommercialInsurance;

    /**
     * @author klw
     * @Fields emergencyContacName : 紧急联系人
     */
    private String emergencyContacName;

    /**
     * @author klw
     * @Fields emergencyContacPhoneNo : 紧急联系人电话
     */
    private String emergencyContacPhoneNo;

    /**
     * @author klw
     * @Fields recommender : 推荐人
     */
    private String recommender;

    /**
     * @author klw
     * @Fields constellation : 星座
     */
    private String constellation;

    /**
     * @author klw
     * @Fields height : 身高(cm)
     */
    private String height;

    /**
     * @author klw
     * @Fields weight : 体重(kg)
     */
    private String weight;

    /**
     * @author klw
     * @Fields health : 健康情况
     */
    private String health;

    /**
     * @author klw
     * @Fields interests : 兴趣爱好
     */
    private String interests;

    /**
     * @author klw
     * @Fields otherSpeciality : 其他特长
     */
    private String otherSpeciality;

    /**
     * @author klw
     * @Fields trainingExperienceAndCertificate : 培训经历及证书
     */
    private String trainingExperienceAndCertificate;

    /**
     * @author klw
     * @Fields remark : 备注
     */
    private String remark;
    
    /**
     * @author klw
     * @Fields staffState : 员工状态
     */
    private StaffStateEnum staffState;
    
    /**
     * @author klw
     * @Fields staffType : 员工类型
     */
    @Indexed
    private StaffTypeEnum staffType;
    
    /**
     * @author klw
     * @Fields textIndexedField : 全文搜索字段
     */
    @TextIndexed
    private String textIndexedField;
    
    //===============以下为自营人员才有的字段===============
    
    /**
     * @author klw
     * @Fields serviceTypeList : 服务类型
     */
    @Indexed
    private List<ServiceType> serviceTypeList;
    
    /**
     * @author klw
     * @Fields occupation : 职业
     */
    private String occupation;
    
    /**
     * @author klw
     * @Fields silent : 是否无声
     */
    private Boolean silent;
    
    /**
     * @author klw
     * @Fields birthday : 生日
     */
    private LocalDate birthday;
    
    //===============以下为自营人员的服务信息的字段===============
    
    /**
     * @author klw
     * @Fields star : 星级 1~5
     */
    private Integer star;
    
    /**
     * @author klw
     * @Fields score : 评分
     */
    private Float score;
    
    /**
     * @author klw
     * @Fields spanningArea : 是否跨区
     */
    private Boolean spanningArea;
    
    /**
     * @author klw
     * @Fields restDayOfMonth : 每月可休息几天
     */
    private Integer restDayOfMonth;
    
    /**
     * @author klw
     * @Fields minimumWage : 保底工资
     */
    private BigDecimal minimumWage;
     
    /**
     * @author klw
     * @Fields skillWage : 技能工资
     */
    private BigDecimal skillWage;
    
    //===============以下为附件信息的字段===============
    
    /**
     * @author klw
     * @Fields healthCheckupReportNum : 体检报告编号
     */
    private String healthCheckupReportNum;
    
    /**
     * @author klw
     * @Fields healthCheckupFilePath : 体检报告(文件路径)
     */
    private String healthCheckupFileUrl;
    
    /**
     * @author klw
     * @Fields otherAttachmentFileUrl : 其他附件(文件路径)
     */
    private String otherAttachmentFileUrl;
    
    /*
     * <p>Title: buildTextIndexedField</p>
     * @author klw
     * <p>Description: </p>
     * @see top.klw8.alita.service.base.entitys.ITextIndexedCustomSupport#buildTextIndexedField()
     */
    @Override
    public void buildTextIndexedField() {
	Assert.hasText(realName, "姓名不能为空");
	Assert.hasText(phoneNumber, "电话号码不能为空");
	this.textIndexedField = AnalyzerUtil.strAnalyzerReturnsWithSpaceNoSmart(realName) + " " + AnalyzerUtil.strAnalyzerInnerSpace(phoneNumber);
    }
    

}
