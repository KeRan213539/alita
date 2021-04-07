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
package top.klw8.alita.web.authority;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import top.klw8.alita.entitys.authority.SystemAuthoritysApp;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.service.api.authority.IAuthorityAppProvider;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.starter.annotations.AuthorityCatlogRegister;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.datasecured.DataSecured;
import top.klw8.alita.starter.web.base.WebapiBaseController;
import top.klw8.alita.starter.web.common.vo.PageRequest;
import top.klw8.alita.validator.UseValidator;
import top.klw8.alita.web.authority.ds.AppTagParser;
import top.klw8.alita.web.authority.vo.AuthorityAppRequest;

import static top.klw8.alita.web.common.CatlogsConstant.CATLOG_INDEX_AU_ADMIN;
import static top.klw8.alita.web.common.CatlogsConstant.CATLOG_NAME_AU_ADMIN;

/**
 * @author zhaozheng
 * @description:
 * @date: 2020-07-16
 */
@Api(tags = {"alita-restful-API--应用管理"})
@RestController
@RequestMapping("/${spring.application.name}/admin/app")
@Slf4j
@AuthorityCatlogRegister(name = CATLOG_NAME_AU_ADMIN, showIndex = CATLOG_INDEX_AU_ADMIN)
public class AuthorityAppController extends WebapiBaseController {

    @DubboReference(async = true)
    private IAuthorityAppProvider appProvider;

    @ApiOperation(value = "新增应用", notes = "新增应用", httpMethod = "POST", produces = "application/json")
    @PostMapping("/save")
    @AuthorityRegister(authorityName = "新增应用", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    @DataSecured(parser = AppTagParser.class)
    public Mono<JsonResult> save(@RequestBody AuthorityAppRequest req){
        SystemAuthoritysApp authorityApp = new SystemAuthoritysApp();
        BeanUtils.copyProperties(req, authorityApp);
        return Mono.fromFuture(appProvider.addAuthorityApp(authorityApp));
    }

    @ApiOperation(value = "删除应用", notes = "删除应用", httpMethod = "DELETE")
    @DeleteMapping("/delete/{appTag}")
    @AuthorityRegister(authorityName = "删除应用", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @DataSecured(parser = AppTagParser.class)
    public Mono<JsonResult> delete(@PathVariable String appTag){
        return Mono.fromFuture(appProvider.deleteAuthorityApp(appTag));
    }

    @ApiOperation(value = "修改应用", notes = "修改应用", httpMethod = "PUT", produces = "application/json")
    @PutMapping("/update")
    @AuthorityRegister(authorityName = "修改应用", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    @DataSecured(parser = AppTagParser.class)
    public Mono<JsonResult> update(@RequestBody AuthorityAppRequest req){
        SystemAuthoritysApp authorityApp = new SystemAuthoritysApp();
        BeanUtils.copyProperties(req, authorityApp);
        return Mono.fromFuture(appProvider.updateAuthorityApp(authorityApp));
    }

    @ApiOperation(value = "应用详情", notes = "应用详情", httpMethod = "GET")
    @GetMapping("/get/{appTag}")
    @AuthorityRegister(authorityName = "应用详情", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @DataSecured(parser = AppTagParser.class)
    public Mono<JsonResult> get(@PathVariable String appTag){
        return Mono.fromFuture(appProvider.authorityApp(appTag));
    }

    @ApiOperation(value = "应用列表(非分页)", notes = "应用列表(非分页)", httpMethod = "GET")
    @GetMapping("/list")
    @AuthorityRegister(authorityName = "应用列表(非分页)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @DataSecured(parser = AppTagParser.class)
    public Mono<JsonResult> list(){
        return Mono.fromFuture(appProvider.authorityAppList());
    }

    @ApiOperation(value = "应用列表(分页)", notes = "应用列表(分页)", httpMethod = "GET")
    @GetMapping("/page")
    @AuthorityRegister(authorityName = "应用列表(分页)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @DataSecured(parser = AppTagParser.class)
    public Mono<JsonResult> page(@RequestParam(value = "appTag", required = false) String appTag,
                                 @RequestParam(value = "remark", required = false) String remark,
                                 @RequestParam(value = "appName", required = false) String appName, PageRequest page){
        return Mono.fromFuture(appProvider.authorityAppPage(appTag,appName, remark, new Page(page.getPage(), page.getSize())));
    }



}
