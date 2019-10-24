package top.klw8.alita.web.user.vo;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: PageRequest
 * @Description: 分页参数
 * @date 2019/10/23 17:35
 */
@Getter
@Setter
@ToString
public class PageRequest {

    @ApiParam(value = "页码")
    private Integer page = 1;

    @ApiParam(value = "每页数据量")
    private Integer size = 10;

}
