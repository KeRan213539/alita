package top.klw8.alita.admin.web.authority.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

/**
 * @ClassName: SystemAuthoritysVo
 * @Description: SystemAuthoritys 的VO
 * @author klw
 * @date 2018年11月28日 下午6:22:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemAuthoritysVo {

    @Required(validatFailMessage = "菜单所属权限目录的ID不能为空")
    @NotEmpty(validatFailMessage = "菜单所属权限目录的ID不能为空")
    @ApiModelProperty(value = "菜单所属权限目录的ID", required=true)
    private String catlogId;
    
    @Required(validatFailMessage = "权限名称不能为空")
    @NotEmpty(validatFailMessage = "权限名称不能为空")
    @ApiModelProperty(value = "权限名称", required=true)
    private String authorityName;
    
    @Required(validatFailMessage = "权限动作不能为空")
    @NotEmpty(validatFailMessage = "权限动作不能为空")
    @ApiModelProperty(value = "权限动作,根据类型来,如果是URL相对路径(如路径为: http://localhost:8080/order/list, 则相对路径为: /order/list", required=true)
    private String authorityAction;
    
    @Required(validatFailMessage = "权限类型不能为空")
    @ApiModelProperty(value = "权限类型:<br />"
	    	+ "作为菜单显示: MENU  <br />"
	    	+ "是URL相对路径: URL", required=true)
    private AuthorityTypeEnum authorityType;
    
    @Required(validatFailMessage = "显示顺序不能为空")
    @ApiModelProperty(value = "作为菜单时的显示顺序,非菜单传0", required=true)
    private Integer showIndex;
    
    @ApiModelProperty(value = "备注")
    private String remark;
    
}
