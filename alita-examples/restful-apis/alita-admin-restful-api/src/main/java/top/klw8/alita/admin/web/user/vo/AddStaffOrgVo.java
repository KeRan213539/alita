package top.klw8.alita.admin.web.user.vo;


import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.user.StaffOrg;
import top.klw8.alita.starter.web.base.vo.IBaseCrudVo;

/**
 * @ClassName: AddStaffOrgVo
 * @Description: 添加员工的部门信息 VO
 * @author klw
 * @date 2019-01-25 16:25:23
 */
@Getter
@Setter
public class AddStaffOrgVo implements IBaseCrudVo<StaffOrg> {

    @ApiModelProperty(value = "所属城市")
    private String cityCode;
    
    @ApiModelProperty(value = "上级部门ID")
    private Long prantId;
    
    @ApiModelProperty(value = "部门名称")
    private String orgName;
    

    @Override
    public StaffOrg buildEntity() {
	StaffOrg org = new StaffOrg();
	BeanUtils.copyProperties(this, org);
	return org;
    }
    
}
