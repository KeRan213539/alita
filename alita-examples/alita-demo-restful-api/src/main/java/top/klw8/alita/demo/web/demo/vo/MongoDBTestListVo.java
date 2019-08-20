package top.klw8.alita.demo.web.demo.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.entitys.demo.mongo.MongoDBTest;
import top.klw8.alita.starter.web.base.vo.ListPrarmVo;

/**
 * @author klw
 * @ClassName: MongoDBTestListVo
 * @Description: demo 获取列表的VO
 * @date 2019年1月26日 下午5:25:19
 */
@Getter
@Setter
@ToString
public class MongoDBTestListVo extends ListPrarmVo<MongoDBTest> {

    private static final long serialVersionUID = -5804689421796908265L;

    @ApiParam(value = "姓名")
    private String name;

    @ApiParam(value = "工资")
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
