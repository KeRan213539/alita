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
package top.klw8.alita.web.authority;

import org.apache.dubbo.config.annotation.DubboReference;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import top.klw8.alita.entitys.authority.AlitaAuthoritysAppChannel;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.service.api.authority.IAuthorityAppProvider;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.starter.annotations.AuthorityCatlogRegister;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.aures.AuthoritysResource;
import top.klw8.alita.starter.web.base.WebapiBaseController;
import top.klw8.alita.starter.web.common.vo.PageRequest;
import top.klw8.alita.validator.UseValidator;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;
import top.klw8.alita.web.authority.ds.AppTagParser;
import top.klw8.alita.web.authority.vo.AuthoritysAppChannelRequest;

import static top.klw8.alita.web.common.CatlogsConstant.CATLOG_INDEX_AU_ADMIN;
import static top.klw8.alita.web.common.CatlogsConstant.CATLOG_NAME_AU_ADMIN;

/**
 * 应用渠道管理相关接口.
 * 2021/4/26 9:59
 */
@Api(tags = {"alita-restful-API--应用渠道管理"})
@RestController
@RequestMapping("/${spring.application.name}/admin/appchannel")
@Slf4j
@AuthorityCatlogRegister(name = CATLOG_NAME_AU_ADMIN, showIndex = CATLOG_INDEX_AU_ADMIN)
public class AuthorityAppChannelController extends WebapiBaseController {

    @DubboReference(async = true)
    private IAuthorityAppProvider appProvider;

    @ApiOperation(value = "新增应用渠道", notes = "新增应用渠道", httpMethod = "POST", produces = "application/json")
    @PostMapping("/save")
    @AuthorityRegister(authorityName = "新增应用渠道", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> save(@RequestBody AuthoritysAppChannelRequest req){
        AlitaAuthoritysAppChannel authorityAppChannel = new AlitaAuthoritysAppChannel();
        BeanUtils.copyProperties(req, authorityAppChannel);
        return Mono.fromFuture(appProvider.addAuthorityAppChannel(authorityAppChannel));
    }

    @ApiOperation(value = "删除应用渠道", notes = "删除应用渠道", httpMethod = "DELETE")
    @DeleteMapping("/delete/{appTag}/{channelTag}")
    @AuthorityRegister(authorityName = "删除应用渠道", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @AuthoritysResource(parser = AppTagParser.class)
    @UseValidator
    public Mono<JsonResult> delete(
            @PathVariable("appTag")
            @Required("appTag 不能为空")
            @NotEmpty("appTag 不能为空")
                    String appTag,
            @PathVariable("channelTag")
            @Required("channelTag 不能为空")
            @NotEmpty("channelTag 不能为空")
                    String channelTag){
        return Mono.fromFuture(appProvider.deleteAuthorityAppChannel(channelTag));
    }

    @ApiOperation(value = "修改应用渠道", notes = "修改应用渠道", httpMethod = "PUT", produces = "application/json")
    @PutMapping("/update")
    @AuthorityRegister(authorityName = "修改应用渠道", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> update(@RequestBody AlitaAuthoritysAppChannel req){
        AlitaAuthoritysAppChannel authorityAppChannel = new AlitaAuthoritysAppChannel();
        BeanUtils.copyProperties(req, authorityAppChannel);
        return Mono.fromFuture(appProvider.updateAuthorityAppChannel(authorityAppChannel));
    }

    @ApiOperation(value = "应用渠道详情", notes = "应用渠道详情", httpMethod = "GET")
    @GetMapping("/get/{channelTag}")
    @AuthorityRegister(authorityName = "应用渠道详情", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> get(@PathVariable String channelTag){
        return Mono.fromFuture(appProvider.authorityAppChannelInfo(channelTag));
    }

    @ApiOperation(value = "应用渠道列表(非分页)", notes = "应用渠道列表(非分页)", httpMethod = "GET")
    @GetMapping("/list")
    @AuthorityRegister(authorityName = "应用渠道列表(非分页)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @AuthoritysResource(parser = AppTagParser.class)
    @UseValidator
    public Mono<JsonResult> list(
            @RequestParam(value = "appTag", required = true)
            @Required("appTag 不能为空")
            @NotEmpty("appTag 不能为空")
                    String appTag) {
        return Mono.fromFuture(appProvider.authorityAppChannelList(appTag));
    }

    @ApiOperation(value = "应用渠道列表(分页)", notes = "应用渠道列表(分页)", httpMethod = "GET")
    @GetMapping("/page")
    @AuthorityRegister(authorityName = "应用渠道列表(分页)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> page(@RequestParam(value = "appTag", required = true) String appTag,
                                 @RequestParam(value = "channelTag", required = false) String channelTag,
                                 @RequestParam(value = "remark", required = false) String remark,  PageRequest page){
        return Mono.fromFuture(appProvider.authorityAppChannelPage(appTag, channelTag, remark, new Page(page.getPage(), page.getSize())));
    }



}
