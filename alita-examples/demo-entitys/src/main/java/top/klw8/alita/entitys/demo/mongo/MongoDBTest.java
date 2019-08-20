package top.klw8.alita.entitys.demo.mongo;

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
import top.klw8.alita.service.base.mongo.common.MongoBaseEntity;
import top.klw8.alita.utils.AnalyzerUtil;


/**
 * @ClassName: MongoDBTest
 * @Description: MongoDBTest
 * @author klw
 * @date 2017年10月27日 下午4:11:52
 */
@Document(collection = "demo")  // spring data mongodb 的注解
@Getter
@Setter
@ToString
//@EqualsAndHashCode(callSuper = false)
//@ApiModel(value = "MongoDBTest", description = "demo用户")
public class MongoDBTest extends MongoBaseEntity implements ITextIndexedCustomSupport, IGeoSearchSupport {

    private static final long serialVersionUID = 3002834053850347862L;

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
