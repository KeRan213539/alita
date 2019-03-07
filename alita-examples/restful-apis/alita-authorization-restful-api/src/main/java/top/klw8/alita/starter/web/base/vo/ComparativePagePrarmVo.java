package top.klw8.alita.starter.web.base.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.service.base.dao.prarm.ForPageMode.ComparisonMode;
import top.klw8.alita.service.base.entitys.BaseEntity;
import top.klw8.alita.validator.annotations.GeoLatitude;
import top.klw8.alita.validator.annotations.GeoLongitude;
import top.klw8.alita.validator.annotations.IntegerRange;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

/**
 * @ClassName: ComparativePagePrarmBean
 * @Description: 比较分页方式的 webapi参数
 * @author klw
 * @date 2018年12月26日 下午4:36:44
 */
@ApiModel(value = "ComparativePagePrarmVo", description = "比较分页查询参数")
@Getter
@Setter
public abstract class ComparativePagePrarmVo<E extends BaseEntity> implements Serializable, Cloneable, IBaseCrudVo<E> {

    private static final long serialVersionUID = 4339253900339119981L;
    
    @ApiModelProperty(value = "页码(比较方式时该值无用,原样返回)", required = true)
    private Integer page; 
    
    @ApiModelProperty(value = "每页数据量(默认10)")
    @IntegerRange(min = 1, max = 50, validatFailMessage = "每页数据量不能少于1条,不能大于50条")
    private Integer limit = 10;
    
    @ApiModelProperty(value = "搜索关键字")
    private String keywords;
    
    @ApiModelProperty(value = "排序(正序: ASC; 倒序: DESC)", allowableValues = "ASC,DESC")
    private String sortDirection;

    @ApiModelProperty(value = "比较的字段值(为空则返回第一页)")
    private String fieldValue;
    
    @ApiModelProperty(value = "比较方式,大于/小于(默认小于)", required = true)
    @Required(validatFailMessage = "比较方式不能为空")
    @NotEmpty(validatFailMessage = "比较方式不能为空")
    private ComparisonMode comparisonMode = ComparisonMode.LESS_THAN;
    
    @ApiModelProperty(value = "一次获取多少页的数据", required = true)
    @IntegerRange(min = 1, max = 20, validatFailMessage = "比较方式分页一次最多获取20页数据,至少获取1页数据")
    private int dataPage = 10;
    
    @ApiModelProperty(value = "地理位置查询的中心点经度,不使用地理位置查询时可空")
    @GeoLongitude
    private Double longitude;
    
    @ApiModelProperty(value = "地理位置查询的中心点纬度,不使用地理位置查询时可空")
    @GeoLatitude
    private Double latitude;
    
}
