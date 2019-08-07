package top.klw8.alita.demo.web.test.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.entitys.demo.DevToolsTestEntity;
import top.klw8.alita.entitys.demo.GeoPoint;
import top.klw8.alita.entitys.demo.enums.DevToolsTestEnum;
import top.klw8.alita.starter.web.base.vo.IBaseCrudVo;

/**
 * @ClassName: DevToolsTestVo
 * @Description: 代码生成器测试用 的 vo
 * @author dev-tools
 * @date 2019年03月05日 11:44:29
 */
@Getter
@Setter
@ToString
public class DevToolsTestVo implements IBaseCrudVo<DevToolsTestEntity> {
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "ID主键")
    private String id;
    
    @ApiModelProperty(value = "姓名")
    private String name;
    
    @ApiModelProperty(value = "工资")
    private BigDecimal salary;
    
    @ApiModelProperty(value = "TODO: 添加注释")
    private BigDecimal salary2;
    
    @ApiModelProperty(value = "位置(纬度/经度)")
    private GeoPoint location;
    
    @ApiModelProperty(value = "")
    private DevToolsTestEnum devToolsTestEnum;
    
    @ApiModelProperty(value = "TODO: 添加注释")
    private LocalDateTime createDataTime;
    
    @ApiModelProperty(value = "全文搜索字段")
    private String textIndexedField;
    
    @Override
    public DevToolsTestEntity buildEntity() {
        DevToolsTestEntity e = new DevToolsTestEntity();
        e.setId(new ObjectId(id));
        e.setName(name);
        e.setSalary(salary);
        e.setSalary2(salary2);
        e.setLocation(location);
        e.setDevToolsTestEnum(devToolsTestEnum);
        e.setCreateDataTime(createDataTime);
        e.setTextIndexedField(textIndexedField);
        return e;
    }
}