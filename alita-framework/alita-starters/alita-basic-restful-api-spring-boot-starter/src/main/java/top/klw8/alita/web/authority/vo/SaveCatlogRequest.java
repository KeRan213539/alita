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
import lombok.*;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

/**
 * @ClassName: SaveCatlogRequest
 * @Description: 保存权限目录的请求
 * @author klw
 * @date 2018年12月5日 下午3:01:02
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class SaveCatlogRequest {

    @NotEmpty(validatFailMessage = "id不能为空")
    @ApiParam(value = "id")
    private String id;

    @Required(validatFailMessage = "所属应用标识不能为空")
    @NotEmpty(validatFailMessage = "所属应用标识不能为空")
    @ApiParam(value = "所属应用标识", required=true)
    private String appTag;

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
