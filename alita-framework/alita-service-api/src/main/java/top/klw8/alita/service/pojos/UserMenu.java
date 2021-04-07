/*
 * Copyright 2018-2021, ranke (213539@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
