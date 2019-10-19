package top.klw8.alita.web.authority.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

/**
 * @ClassName: AddRoleAuRequest
 * @Description: 向角色中添加权限接口的请求参数
 * @author klw
 * @date 2018年12月4日 下午5:29:22
 */
@Data
public class AddRoleAuRequest {

    @Required(validatFailMessage = "角色ID不能为空")
    @NotEmpty(validatFailMessage = "角色ID不能为空")
    @ApiParam(value = "角色ID", required=true)
    private String roleId;
    
    @Required(validatFailMessage = "权限ID不能为空")
    @NotEmpty(validatFailMessage = "权限ID不能为空")
    @ApiParam(value = "权限ID", required=true)
    private String auId;
    
}
