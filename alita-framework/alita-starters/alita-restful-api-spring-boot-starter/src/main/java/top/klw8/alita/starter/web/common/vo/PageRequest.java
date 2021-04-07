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
package top.klw8.alita.starter.web.common.vo;

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
