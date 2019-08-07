package top.klw8.alita.entitys.user;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.base.BaseEntity;
import top.klw8.alita.entitys.base.ITextIndexedCustomSupport;
import top.klw8.alita.utils.AnalyzerUtil;

/**
 * @ClassName: ExtUserInfoDemo
 * @Description: 扩展用户信息Demo
 * @author klw
 * @date 2019-03-12 10:32:42
 */
@Document(collection = "ext_user_info_demo")
@Getter
@Setter
public class ExtUserInfoDemo extends BaseEntity implements ITextIndexedCustomSupport {

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
     * @Fields birthday : 生日
     */
    private LocalDateTime birthday;
    
    
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
