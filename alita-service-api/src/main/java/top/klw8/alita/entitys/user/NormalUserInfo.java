package top.klw8.alita.entitys.user;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.user.enums.NormalUserClassifyEnum;
import top.klw8.alita.entitys.user.enums.NormalUserLevelEnum;
import top.klw8.alita.entitys.user.enums.NormalUserSourceEnum;
import top.klw8.alita.entitys.user.enums.NormalUserTypeEnum;
import top.klw8.alita.entitys.user.enums.UserGendersEnum;
import top.klw8.alita.service.base.entitys.BaseEntity;
import top.klw8.alita.service.base.entitys.ITextIndexedCustomSupport;
import top.klw8.alita.utils.AnalyzerUtil;

/**
 * @ClassName: NormalUserInfo
 * @Description: 前台用户(客户)信息
 * @author klw
 * @date 2019年1月11日 上午10:58:31
 */
@Document(collection = "normal_user_info")
@Getter
@Setter
public class NormalUserInfo extends BaseEntity implements ITextIndexedCustomSupport {

    private static final long serialVersionUID = 6774327869246437799L;
    
    /**
     * @author klw
     * @Fields accountInfo : 账户信息
     */
    @DBRef
    @Indexed(unique=true)
    private AlitaUserAccount accountInfo;
    
    /**
     * @author klw
     * @Fields faceImg : 头像
     */
    private String faceImg;
    
    /**
     * @author klw
     * @Fields nickName : 昵称
     */
    private String nickName;
    
    /**
     * @author klw
     * @Fields realName : 姓名
     */
    private String realName;
    
    /**
     * @author klw
     * @Fields phoneNumber : 联系电话
     */
    private String phoneNumber;
    
    /**
     * @author klw
     * @Fields userGender : 客户性别
     */
    private UserGendersEnum userGender;
    
    /**
     * @author klw
     * @Fields level : 客户等级
     */
    @Indexed
    private NormalUserLevelEnum level;
    
    /**
     * @author klw
     * @Fields birthday : 生日
     */
    private LocalDateTime birthday;
    
    /**
     * @author klw
     * @Fields classify : 客户分类
     */
    private NormalUserClassifyEnum classify;
    
    /**
     * @author klw
     * @Fields source : 客户来源
     */
    private NormalUserSourceEnum source;
    
    /**
     * @author klw
     * @Fields userType : 客户的类型
     */
    @Indexed
    private NormalUserTypeEnum userType;
	
    
    /**
     * @author klw
     * @Fields textIndexedField : 全文搜索字段
     */
    @TextIndexed
    private String textIndexedField;


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
	this.textIndexedField = AnalyzerUtil.strAnalyzerReturnsWithSpaceNoSmart(realName) + " "  + AnalyzerUtil.strAnalyzerInnerSpace(phoneNumber);
    }
    

}
