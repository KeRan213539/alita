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
package top.klw8.alita.demo.web.demo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import top.klw8.alita.demo.web.demo.datasecured.*;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.datasecured.DataSecured;

import java.io.File;
import java.io.InputStream;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DataAuthorityDemoController
 * @Description: 数据权限
 * @date 2020/4/22 13:57
 */
@Api(tags = {"alita-restful-API--demoAPI"})
@RestController
@RequestMapping("/${spring.application.name}/dataau")
public class DataAuthorityDemoController {

    @ApiOperation(value = "get测试", notes = "get测试", httpMethod = "GET", produces = "application/json")
    @GetMapping("/getTest")
    @AuthorityRegister(catlogName = "数据权限demo", catlogShowIndex = 99,
            authorityName = "get测试", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0, dataSecuredSource = GetTestDsSource.class)
    @DataSecured(resource = "getTest")
    public Mono<String> getTest(String str1, String str2){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>begin call 【get】");
        System.out.println("str1 ==== " + str1);
        System.out.println("str2 ==== " + str2);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<end call 【get】");
        return Mono.just("OK");
    }

    @ApiOperation(value = "post测试", notes = "post测试", httpMethod = "POST", produces = "application/json")
    @PostMapping("/postTest")
    @AuthorityRegister(catlogName = "数据权限demo", catlogShowIndex = 99,
            authorityName = "post测试", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0, dataSecuredSource = PostTestDsSource.class)
    @DataSecured(parser = DemoResourceParser.class)
    public Mono<String> postTest(String str1, @RequestBody String json){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>begin call 【post】");
        System.out.println("str1 ==== " + str1);
        System.out.println("json ==== " + json);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<end call 【post】");
        return Mono.just("OK");
    }

    @ApiOperation(value = "图片上传测试", notes = "图片上传测试", httpMethod = "POST", produces = "application/json")
    @PostMapping("/fileTest")
    @AuthorityRegister(catlogName = "数据权限demo", catlogShowIndex = 99,
            authorityName = "图片上传测试", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0, dataSecuredSource = FileTestDsSource.class)
    @DataSecured(resource = "fileTest", fileUpload = true)
    public Mono<String> fileTest(String str1, @RequestPart("str2") String str2, @RequestPart("file") FilePart filePart){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>begin call 【file】");
        System.out.println("str1 ==== " + str1);
        System.out.println("str2 ==== " + str2);
        System.out.println("fileName ====" + filePart.filename());
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<end call 【file】");
        return DataBufferUtils.join(filePart.content()).map(buffer -> {
            if (buffer.readableByteCount() <= 0) {
                return "faild";
            }
            try {
                InputStream is = buffer.asInputStream(true);
                FileUtils.copyInputStreamToFile(is, new File("E:\\test\\testFileUpload.jpg"));
                is.close();
                DataBufferUtils.release(buffer);
            } catch (Exception e) {
                return e.getMessage();
            }
            return "OK";
        }).as(Mono::from);
    }

    @ApiOperation(value = "url参数测试", notes = "url参数测试", httpMethod = "GET", produces = "application/json")
    @GetMapping("/urlPrarmTest/{urlPrarm}")
    @AuthorityRegister(catlogName = "数据权限demo", catlogShowIndex = 99,
            authorityName = "url参数测试", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @DataSecured(parser = DemoResourceParser.class)
    public Mono<String> urlPrarmTest(String str1, @PathVariable("urlPrarm")String urlPrarm){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>begin call 【urlPrarmTest】");
        System.out.println("urlPrarm ==== " + urlPrarm);
        System.out.println("str1 ==== " + str1);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<end call 【urlPrarmTest】");
        return Mono.just("OK");
    }

    @ApiOperation(value = "url参数测试POST", notes = "url参数测试POST", httpMethod = "POST", produces = "application/json")
    @PostMapping("/urlPrarmTestPost/{urlPrarm}")
    @AuthorityRegister(catlogName = "数据权限demo", catlogShowIndex = 99,
            authorityName = "url参数测试POST", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0, dataSecuredSource = DemoAuthoritysResourceSource.class, dataSecuredSourceEnum = DemoAuthoritysResourceSourceEnum.class)
    @DataSecured(parser = DemoResourceParser.class)
    public Mono<String> urlPrarmTestPost(String str1, @PathVariable("urlPrarm")String urlPrarm){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>begin call 【urlPrarmTestPost】");
        System.out.println("urlPrarm ==== " + urlPrarm);
        System.out.println("str1 ==== " + str1);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<end call 【urlPrarmTestPost】");
        return Mono.just("OK");
    }

    @ApiOperation(value = "NO Mapping 测试--GET", notes = "NO Mapping 测试--GET", httpMethod = "GET", produces = "application/json")
    @GetMapping
    @AuthorityRegister(catlogName = "数据权限demo", catlogShowIndex = 99,
            authorityName = "NO Mapping 测试--GET", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0, dataSecuredSource = DemoAuthoritysResourceSource.class)
    @DataSecured(parser = DemoResourceParser.class)
    public Mono<String> testGetNoMapping(String str1){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>begin call 【testGetNoMapping】");
        System.out.println("str1 ==== " + str1);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<end call 【testGetNoMapping】");
        return Mono.just("OK");
    }

    @ApiOperation(value = "NO Mapping 测试--POST", notes = "NO Mapping 测试--POST", httpMethod = "POST", produces = "application/json")
    @PostMapping
    @AuthorityRegister(catlogName = "数据权限demo", catlogShowIndex = 99,
            authorityName = "NO Mapping 测试--POST", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @DataSecured(parser = DemoResourceParser.class)
    public Mono<String> testPostNoMapping(String str1){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>begin call 【testPostNoMapping】");
        System.out.println("str1 ==== " + str1);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<end call 【testPostNoMapping】");
        return Mono.just("OK");
    }

}
