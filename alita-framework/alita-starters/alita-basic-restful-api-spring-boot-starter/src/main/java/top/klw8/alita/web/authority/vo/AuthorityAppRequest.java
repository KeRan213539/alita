package top.klw8.alita.web.authority.vo;

import io.swagger.annotations.ApiParam;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

/**
 * @author zhaozheng
 * @description:
 * @date: 2020-07-17
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class AuthorityAppRequest {

    @NotEmpty(validatFailMessage = "id不能为空")
    @Required(validatFailMessage = "appTag不能为空")
    @ApiParam(value = "appTag")
    private String appTag;


    @ApiParam(value = "appName")
    private String appName;

    @ApiParam(value = "remark")
    private String remark;
}
