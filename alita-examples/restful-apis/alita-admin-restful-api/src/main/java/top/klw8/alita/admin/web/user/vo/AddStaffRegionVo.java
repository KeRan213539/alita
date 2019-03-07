package top.klw8.alita.admin.web.user.vo;

import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.user.StaffRegion;
import top.klw8.alita.starter.web.base.vo.IBaseCrudVo;

/**
 * @ClassName: AddStaffRegionVo
 * @Description: 增加员工(管家)的负责区域 VO
 * @author klw
 * @date 2019-01-25 16:25:03
 */
@Getter
@Setter
public class AddStaffRegionVo implements IBaseCrudVo<StaffRegion> {

    @ApiModelProperty(value = "所属城市")
    private String cityCode;
    
    @ApiModelProperty(value = "上级区域ID")
    private Long prantId;
    
    @ApiModelProperty(value = "区域名称")
    private String regionName;
    
    @Override
    public StaffRegion buildEntity() {
	StaffRegion region = new StaffRegion();
	BeanUtils.copyProperties(this, region);
	return region;
    }
    
}
