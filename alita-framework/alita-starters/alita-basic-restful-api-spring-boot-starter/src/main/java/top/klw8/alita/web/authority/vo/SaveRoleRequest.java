package top.klw8.alita.web.authority.vo;


import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

import java.util.List;

/**
 * @ClassName: SaveRoleRequest
 * @Description: 保存角色的请求
 * @author klw
 * @date 2018年12月4日 下午5:18:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SaveRoleRequest {

    @NotEmpty(validatFailMessage = "角色ID不能为空")
    @ApiParam(value = "角色ID")
    private String roleId;

    @Required(validatFailMessage = "角色名称不能为空")
    @NotEmpty(validatFailMessage = "角色名称不能为空")
    @ApiParam(value = "角色名称", required=true)
    private String roleName;
    
    @ApiParam(value = "备注")
    private String remark;

    @ApiParam(value = "要复制的角色ID,可以从该角色中复制权限到新的角色,如果此参数有值,则 auIdList 参数将被忽略")
    private String copyAuFromRoleId;

    @ApiParam(value = "该角色中的权限,如果要复制的角色ID有值,则此参数将被忽略")
    private List<String> auIdList;
    
}
