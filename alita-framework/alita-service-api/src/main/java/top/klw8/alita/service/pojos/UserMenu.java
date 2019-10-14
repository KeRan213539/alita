package top.klw8.alita.service.pojos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.list.SetUniqueList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: UserMenu
 * @Description: 用户菜单目录pojo
 * @date 2019/10/9 15:20
 */
@Getter
@Setter
@ToString
public class UserMenu implements java.io.Serializable {

    private static final long serialVersionUID = 4226666111547632644L;

    @ApiModelProperty(value = "目录名称")
    private String catlogName;

    @ApiModelProperty(value = "显示顺序")
    private Integer showIndex;

    @ApiModelProperty(value = "目录中的菜单项")
    private List<UserMenuItem> itemList;

    public UserMenu(String catlogName, Integer showIndex){
        this.catlogName = catlogName;
        this.showIndex = showIndex;
        this.itemList = SetUniqueList.setUniqueList(new ArrayList<>());
    }

}
