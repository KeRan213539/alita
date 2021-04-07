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
package top.klw8.alita.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.datasecured.DataSecured;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: TestController
 * @Description: 测试
 * @date 2020/7/28 10:36
 */
@Api(tags = {"alita-restful-API--测试"})
@RestController
@RequestMapping("/${spring.application.name}/admin/test")
public class TestController {

    @ApiOperation(value = "get测试", notes = "get测试", httpMethod = "GET", produces = "application/json")
    @GetMapping("/getTest")
    @AuthorityRegister(catlogName = "数据权限demo", catlogShowIndex = 99,
            authorityName = "get测试", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @DataSecured(resource = {"getTest", "getTest2"})
    public Mono<String> getTest(String str1, String str2){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>begin call 【get】");
        System.out.println("str1 ==== " + str1);
        System.out.println("str2 ==== " + str2);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<end call 【get】");
        return Mono.just("OK");
    }

}
