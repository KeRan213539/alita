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
package top.klw8.alita.demo.web.demo.mybatisvo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 提供Controller使用的VO
 * 2019-08-19 21:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExtUserInfoVo {

    @ApiParam(value = "编号")
    private String id;

    @ApiParam(value = "用户编号")
    private String userId;

    @ApiParam(value = "用户邮箱地址")
    private String userEmail;

    @ApiParam(value = "用户等级")
    private Integer userLevel;

    @ApiParam(value = "用户昵称")
    private String userNick;

}
