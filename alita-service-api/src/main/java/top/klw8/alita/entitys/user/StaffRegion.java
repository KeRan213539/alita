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
 * @ClassName: StaffRegion
 * @Description: 员工(管家)的负责区域
 * @author klw
 * @date 2019年1月18日 上午10:26:18
 */
@Document(collection = "staff_region")
@Getter
@Setter
public class StaffRegion extends BaseEntity implements ITextIndexedSupport {

    private static final long serialVersionUID = 8001147474527008487L;
    
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
     * @Fields areaName : 区域名称
     */
    private String regionName;
    
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
	Assert.hasText(regionName, "区域名称不能为空");
	return regionName;
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
