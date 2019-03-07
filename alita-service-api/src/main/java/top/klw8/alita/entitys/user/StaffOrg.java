package top.klw8.alita.entitys.user;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.service.base.entitys.BaseEntity;
import top.klw8.alita.service.base.entitys.ITextIndexedSupport;

/**
 * @ClassName: StaffOrg
 * @Description: 员工的部门信息
 * @author klw
 * @date 2019年1月18日 上午10:25:14
 */
@Document(collection = "staff_org")
@Getter
@Setter
public class StaffOrg extends BaseEntity implements ITextIndexedSupport {

    private static final long serialVersionUID = -5338614503349893115L;
    
    /**
     * @author klw
     * @Fields cityCode : 所属城市
     */
    private String cityCode;
    
    /**
     * @author klw
     * @Fields prantId : 上级ID
     */
    @Indexed
    private Long prantId;
    
    /**
     * @author klw
     * @Fields areaName : 部门名称
     */
    private String orgName;
    
    /**
     * @author klw
     * @Fields textIndexedField : 全文搜索字段
     */
    @TextIndexed
    private String textIndexedField;

    /*
     * <p>Title: toTextIndexedText</p>
     * @author klw
     * <p>Description: </p>
     * @return
     * @see top.klw8.alita.service.base.entitys.ITextIndexedSupport#toTextIndexedText()
     */
    @Override
    public String toTextIndexedText() {
	Assert.hasText(orgName, "部门名称不能为空");
	return orgName;
    }

    /*
     * <p>Title: analyzerResult2TextIndexedField</p>
     * @author klw
     * <p>Description: </p>
     * @param analyzerResult
     * @see top.klw8.alita.service.base.entitys.ITextIndexedSupport#analyzerResult2TextIndexedField(java.lang.String)
     */
    @Override
    public void analyzerResult2TextIndexedField(String analyzerResult) {
	this.textIndexedField = analyzerResult;
    }
    
}
