package top.klw8.alita.web.authority.vo;

import io.swagger.annotations.ApiParam;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: SaveDataSecuredRequest
 * @Description: 数据权限保存请求参数
 * @date 2020/5/20 17:32
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class SaveDataSecuredRequest {

    @NotEmpty(validatFailMessage = "id不能为空")
    @ApiParam(value = "id")
    private String id;


    /**
     * @author klw(213539@qq.com)
     * @Description: 所属权限ID
     */
    @Required(validatFailMessage = "所属权限ID不能为空")
    @NotEmpty(validatFailMessage = "所属权限ID不能为空")
    @ApiParam(value = "所属权限ID(全局数据权限传: PUBLIC_DATA_SECURED_AUTHORITY)", required=true)
    private String authoritysId;

    @NotEmpty(validatFailMessage = "所属应用标识不能为空")
    @ApiParam(value = "所属应用标识(全局数据权限必传,普通数据权限不传)")
    private String appTag;

    /**
     * @author klw(213539@qq.com)
     * @Description: 资源标识
     */
    @Required(validatFailMessage = "资源标识不能为空")
    @NotEmpty(validatFailMessage = "资源标识不能为空")
    @ApiParam(value = "资源标识", required=true)
    private String resource;

    /**
     * @author klw(213539@qq.com)
     * @Description: 备注/名称
     */
    @ApiParam(value = "备注/名称")
    private String remark;

}