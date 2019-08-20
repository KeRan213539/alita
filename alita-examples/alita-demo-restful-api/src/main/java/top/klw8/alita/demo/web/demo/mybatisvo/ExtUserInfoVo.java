package top.klw8.alita.demo.web.demo.mybatisvo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author freedom
 * @version 1.0
 * @ClassName ExtUserInfoVo
 * @Description 提供Controller使用的VO
 * @date 2019-08-19 21:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExtUserInfoVo {

    @ApiParam(value = "用户编号")
    private String userId;

    @ApiParam(value = "用户邮箱地址")
    private String userEmail;

    @ApiParam(value = "用户等级")
    private Integer userLevel;

}
