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
package top.klw8.alita.web.authority.vo;

import io.swagger.annotations.ApiParam;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;
import top.klw8.alita.web.authority.vo.enums.HttpMethodPrarm;

/**
 * @ClassName: SaveAuthoritysRequest
 * @Description: 权限保存的的请求
 * @author klw
 * @date 2018年11月28日 下午6:22:37
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class SaveAuthoritysRequest {

    /**
     * @author xp
     * @Description: id
     */
    @ApiParam(value = "ID", required=true)
    private String id;

    @Required(validatFailMessage = "菜单所属权限目录的ID不能为空")
    @NotEmpty(validatFailMessage = "菜单所属权限目录的ID不能为空")
    @ApiParam(value = "菜单所属权限目录的ID", required=true)
    private String catlogId;

    @NotEmpty(validatFailMessage = "URL类型权限所属MENU类型权限的ID不能为空")
    @ApiParam(value = "URL类型权限所属MENU类型权限的ID")
    private String menuId;
    
    @Required(validatFailMessage = "权限名称不能为空")
    @NotEmpty(validatFailMessage = "权限名称不能为空")
    @ApiParam(value = "权限名称", required=true)
    private String authorityName;
    
    @Required(validatFailMessage = "权限动作不能为空")
    @NotEmpty(validatFailMessage = "权限动作不能为空")
    @ApiParam(value = "权限动作,根据类型来,如果是URL相对路径(如路径为: http://localhost:8080/order/list, 则相对路径为: /order/list", required=true)
    private String authorityAction;
    
    @Required(validatFailMessage = "权限类型不能为空")
    @ApiParam(value = "权限类型:<br />"
	    	+ "作为菜单显示: MENU  <br />"
	    	+ "是URL相对路径: URL", required=true, allowableValues= "MENU,URL")
    private AuthorityTypeEnum authorityType;

    @ApiParam(value = "httpMethod, 权限类型为URL时必传")
    private HttpMethodPrarm httpMethod;
    
    @Required(validatFailMessage = "显示顺序不能为空")
    @ApiParam(value = "作为菜单时的显示顺序,非菜单传0", required=true)
    private Integer showIndex;
    
    @ApiParam(value = "备注")
    private String remark;
}
