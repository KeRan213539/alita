package top.klw8.alita.web.authority.vo;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: SaveRoleAuthoritysRequest
 * @Description: 保存角色的权限(替换原有权限)接口请求
 * @date 2019/10/29 16:36
 */
@Getter
@Setter
public class SaveRoleAuthoritysRequest {

    @Required("角色ID不能为空")
    @NotEmpty("角色ID不能为空")
    @ApiParam(value = "角色ID", required = true)
    private String roleId;

    @Required("多个权限ID不能为空")
    @NotEmpty("多个权限ID不能为空")
    @ApiParam(value = "多个权限ID", required = true)
    private List<String> auIds;

    @NotEmpty("appTag并不能为空")
    @ApiParam(value = "操作的角色所属应用的appTag")
    private String appTag;
}
