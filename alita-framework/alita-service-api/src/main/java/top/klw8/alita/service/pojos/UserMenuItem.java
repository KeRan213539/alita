/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
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
