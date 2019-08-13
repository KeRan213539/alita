package top.klw8.alita.entitys.demo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.base.mongodb.annotations.NotPersistence;
import top.klw8.alita.base.mongodb.annotations.QueryLikeField;
import top.klw8.alita.service.base.mongo.common.IGeoSearchSupport;
import top.klw8.alita.service.base.mongo.common.ITextIndexedCustomSupport;
import top.klw8.alita.entitys.demo.enums.DevToolsTestEnum;
import top.klw8.alita.service.base.mongo.common.MongoBaseEntity;
import top.klw8.alita.utils.AnalyzerUtil;

/**
 * @ClassName: DevToolsTestEntity
 * @Description: 代码生成器测试用
 * @author klw
 * @date 2019年3月1日 上午10:47:07
 */
@Document(collection = "devToolsTest")  // spring data mongodb 的注解
@Getter
@Setter
@ToString
public class DevToolsTestEntity extends MongoBaseEntity implements ITextIndexedCustomSupport, IGeoSearchSupport {

    private static final long serialVersionUID = -7036680933571767016L;

    /**
     * @author klw
     * @Fields name : 姓名
     */
    @QueryLikeField
    private String name;

    /**
     * @author klw
     * @Fields salary : 工资
     */
    private BigDecimal salary;
    
    @NotPersistence // spring data mongodb 的注解
    private BigDecimal salary2;
    
    /**
     * @author klw
     * @Fields location : 位置(纬度/经度)
     */
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoPoint location;
    
    /**
     * @author klw
     * @Fields devToolsTestEnum : 测试枚举
     */
    private DevToolsTestEnum devToolsTestEnum;
    
    private LocalDateTime createDataTime;
    
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
	this.textIndexedField = AnalyzerUtil.strAnalyzerReturnsWithSpaceNoSmart(name);
    }
    
}
