package top.klw8.alita.web.authority.vo;


import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

/**
 * @ClassName: SystemRoleVo
 * @Description: SystemRole的Vo
 * @author klw
 * @date 2018年12月4日 下午5:18:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemRoleVo {

    @Required(validatFailMessage = "角色名称不能为空")
    @NotEmpty(validatFailMessage = "角色名称不能为空")
    @ApiParam(value = "角色名称", required=true)
    private String roleName;
    
    @ApiParam(value = "备注")
    private String remark;
    
}
