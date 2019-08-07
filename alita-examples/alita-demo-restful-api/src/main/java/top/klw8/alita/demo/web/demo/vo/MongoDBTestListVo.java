package top.klw8.alita.demo.web.demo.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.entitys.demo.MongoDBTest;
import top.klw8.alita.starter.web.base.vo.ListPrarmVo;

/**
 * @ClassName: MongoDBTestListVo
 * @Description: demo 获取列表的VO
 * @author klw
 * @date 2019年1月26日 下午5:25:19
 */
@Getter
@Setter
@ToString
public class MongoDBTestListVo extends ListPrarmVo<MongoDBTest> {

    private static final long serialVersionUID = -5804689421796908265L;

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
