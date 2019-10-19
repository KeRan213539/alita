package top.klw8.alita.web.authority.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

/**
 * @ClassName: SystemAuthoritysCatlogVo
 * @Description: 权限的目录 VO
 * @author klw
 * @date 2018年12月5日 下午3:01:02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemAuthoritysCatlogVo {
    
    @Required(validatFailMessage = "目录名称不能为空")
    @NotEmpty(validatFailMessage = "目录名称不能为空")
    @ApiParam(value = "目录名称", required=true)
    private String catlogName;
    
    @Required(validatFailMessage = "显示顺序不能为空")
    @ApiParam(value = "显示顺序", required=true)
    private Integer showIndex;
    
    @ApiParam(value = "备注")
    private String remark;

}
