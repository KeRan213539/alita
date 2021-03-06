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
package top.klw8.alita.demo.web.demo.vo;

import io.swagger.annotations.ApiParam;

/**
 * demo 请求
 * 2018年9月29日 下午5:10:20
 */
public class DemoRequest {

    @ApiParam(value = "如果为空就抛异常", required = false)
//    @Required(validatFailMessage="用户名不能为空!")
//    @NotEmpty(validatFailMessage="用户名不能为空2!")
    private String abc;

    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }
    
}
