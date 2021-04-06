package top.klw8.alita.web.user.vo;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: SaveUserRolesRequest
 * @Description: 保存用户拥有的角色(替换原有角色)接口请求
 * @date 2019/10/29 16:36
 */
@Getter
@Setter
public class SaveUserRolesRequest {

    @Required("用户ID不能为空")
    @NotEmpty("用户ID不能为空")
    @ApiParam(value = "用户ID", required = true)
    private String userId;

    @Required("多个角色ID不能为空")
    @NotEmpty("多个角色ID不能为空")
    @ApiParam(value = "多个角色ID", required = true)
    private List<String> roleIds;

    @NotEmpty("appTag并不能为空")
    @ApiParam(value = "操作的角色所属应用的appTag")
    private String appTag;
}
