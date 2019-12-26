package top.klw8.alita.service.pojos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: UserMenuItem
 * @Description: 用户菜单项pojo
 * @date 2019/10/9 15:20
 */
@Getter
@Setter
@ToString
public class UserMenuItem implements java.io.Serializable {

    private static final long serialVersionUID = 4226666111547632644L;

    @ApiModelProperty(value = "菜单项名称")
    private String itemName;

    @ApiModelProperty(value = "点击跳转的地址")
    private String itemAction;

    @ApiModelProperty(value = "排序")
    private Integer showIndex;

    public UserMenuItem(String itemName, String itemAction, Integer showIndex){
        this.itemName = itemName;
        this.itemAction = itemAction;
        this.showIndex = showIndex;
    }

}