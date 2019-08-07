package top.klw8.alita.demo.web.demo.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.entitys.demo.MongoDBTest;
import top.klw8.alita.service.common.GeoPoint;
import top.klw8.alita.starter.web.base.vo.IBaseCrudVo;
import top.klw8.alita.validator.annotations.GeoLatitude;
import top.klw8.alita.validator.annotations.GeoLongitude;

/**
 * @ClassName: MongoDBTestVo
 * @Description: MongoDBTest 的 VO
 * @author klw
 * @date 2019年1月25日 下午1:51:09
 */
@Getter
@Setter
@ToString
public class MongoDBTestVo implements IBaseCrudVo<MongoDBTest> {
    
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "工资")
    private BigDecimal salary;
    
    @ApiModelProperty(value = "位置的经度")
    @GeoLongitude
    private Double longitude;
    
    @ApiModelProperty(value = "位置的纬度")
    @GeoLatitude
    private Double latitude;

    /*
     * <p>Title: buildEntity</p>
     * @author klw
     * <p>Description: </p>
     * @return
     * @see top.klw8.alita.starter.web.base.vo.IBaseCrudVo#buildEntity()
     */
    @Override
    public MongoDBTest buildEntity() {
	MongoDBTest e = new MongoDBTest();
	e.setName(name);
	e.setSalary(salary);
	e.setSalary2(salary);
	e.setLocation(new GeoPoint(latitude, longitude));
	return e;
    }

}
