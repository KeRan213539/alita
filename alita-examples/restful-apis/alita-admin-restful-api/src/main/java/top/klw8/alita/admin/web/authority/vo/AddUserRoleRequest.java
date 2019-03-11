package top.klw8.alita.admin.web.authority.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

/**
 * @ClassName: AddUserRoleRequest
 * @Description: 向用户中添加角色接口请求参数
 * @author klw
 * @date 2018年12月5日 下午5:13:23
 */
@Data
public class AddUserRoleRequest {

    @Required(validatFailMessage = "用户ID不能为空")
    @NotEmpty(validatFailMessage = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID", required=true)
    private String userId;
    
    @Required(validatFailMessage = "角色ID不能为空")
    @NotEmpty(validatFailMessage = "角色ID不能为空")
    @ApiModelProperty(value = "角色ID", required=true)
    private String roleId;
    
}
