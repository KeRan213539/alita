package top.klw8.alita.starter.web.base.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.base.BaseEntity;
import top.klw8.alita.validator.annotations.GeoLatitude;
import top.klw8.alita.validator.annotations.GeoLongitude;

/**
 * @ClassName: ListPrarmVo
 * @Description: 分页参数Vo
 * @author klw
 * @date 2018年10月10日 下午1:43:34
 */
@ApiModel(value = "ListPrarmVo", description = "列表查询参数")
@Getter
@Setter
public abstract class ListPrarmVo<E extends BaseEntity> implements Serializable, Cloneable, IBaseCrudVo<E> {
    
    private static final long serialVersionUID = -1257470207732914007L;

    @ApiModelProperty(value = "搜索关键字")
    private String keywords;
    
    @ApiModelProperty(value = "排序(正序: ASC; 倒序: DESC)", allowableValues = "ASC,DESC")
    private String sortDirection;
    
    @ApiModelProperty(value = "地理位置查询的中心点经度,不使用地理位置查询时可空")
    @GeoLongitude
    private Double longitude;
    
    @ApiModelProperty(value = "地理位置查询的中心点纬度,不使用地理位置查询时可空")
    @GeoLatitude
    private Double latitude;
    
}
