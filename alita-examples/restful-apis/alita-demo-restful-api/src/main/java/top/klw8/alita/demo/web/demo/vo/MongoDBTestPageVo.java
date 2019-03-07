package top.klw8.alita.demo.web.demo.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.entitys.demo.MongoDBTest;
import top.klw8.alita.starter.web.base.vo.PagePrarmVo;

/**
 * @ClassName: MongoDBTestPageVo
 * @Description: demo skip分页的VO
 * @author klw
 * @date 2019年1月26日 下午5:25:38
 */
@Getter
@Setter
@ToString
public class MongoDBTestPageVo extends PagePrarmVo<MongoDBTest> {

    private static final long serialVersionUID = 5745968434636091640L;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "工资")
    private BigDecimal salary;
    
    @Override
    public MongoDBTest buildEntity() {
	MongoDBTest e = new MongoDBTest();
	e.setName(name);
	e.setSalary(salary);
	e.setSalary2(salary);
	return e;
    }

}
