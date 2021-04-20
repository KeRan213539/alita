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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import org.apache.dubbo.config.annotation.DubboReference;

import org.springframework.beans.BeanUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;
import top.klw8.alita.entitys.authority.AlitaAuthoritysMenu;
import top.klw8.alita.entitys.authority.AlitaAuthoritysCatlog;
import top.klw8.alita.entitys.authority.AlitaAuthoritysResource;
import top.klw8.alita.entitys.authority.AlitaRole;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.result.code.CommonResultCodeEnum;
import top.klw8.alita.starter.annotations.AuthorityCatlogRegister;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.aures.AuthoritysResource;
import top.klw8.alita.starter.utils.ResServerTokenUtil;
import top.klw8.alita.starter.web.base.WebapiBaseController;
import top.klw8.alita.validator.UseValidator;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;
import top.klw8.alita.web.authority.ds.AppTagByIdParser;
import top.klw8.alita.web.authority.ds.AppTagParser;
import top.klw8.alita.web.authority.vo.*;
import top.klw8.alita.starter.web.common.vo.PageRequest;
import top.klw8.alita.web.authority.vo.enums.HttpMethodPrarm;

import java.util.stream.Collectors;

import static top.klw8.alita.web.common.CatlogsConstant.CATLOG_NAME_AU_ADMIN;
import static top.klw8.alita.web.common.CatlogsConstant.CATLOG_INDEX_AU_ADMIN;

/**
 * 权限管理相关(角色管理,权限目录管理,权限管理)
 * 2019/10/15 14:13
 */
@Api(tags = {"alita-restful-API--权限管理"})
@RestController
@RequestMapping("/${spring.application.name}/admin/au")
@Slf4j
@AuthorityCatlogRegister(name = CATLOG_NAME_AU_ADMIN, showIndex = CATLOG_INDEX_AU_ADMIN)
public class AuthorityAdminController extends WebapiBaseController {

    @DubboReference(async = true)
    private IAuthorityAdminProvider auProvider;

    @ApiOperation(value = "角色列表(分页)", notes = "角色列表(分页)", httpMethod = "GET", produces = "application/json")
    @GetMapping("/roleList")
    @AuthorityRegister(authorityName = "角色列表(分页)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名称(支持模糊查询)", paramType = "query"),
            @ApiImplicitParam(name = "appTag", value = "应用标识(不传查全部)", paramType = "query"),
    })
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> roleList(String roleName, String appTag, PageRequest page) {
        return Mono.fromFuture(auProvider.roleList(roleName, appTag, new Page(page.getPage(), page.getSize())));
    }

    @ApiOperation(value = "获取全部角色,不分页", notes = "获取全部角色,不分页", httpMethod = "GET", produces = "application/json")
    @GetMapping("/roleAll")
    @AuthorityRegister(authorityName = "获取全部角色,不分页", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appTag", value = "应用标识(不传查全部)", paramType = "query"),
    })
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> roleAll(String appTag) {
        return Mono.fromFuture(auProvider.roleAll(appTag));
    }

    @ApiOperation(value = "获取全部权限,并根据传入的角色ID标识出该角色拥有的权限", notes = "获取全部权限,并根据传入的角色ID标识出该角色拥有的权限", httpMethod = "GET", produces = "application/json")
    @GetMapping("/markRoleAuthoritys")
    @AuthorityRegister(authorityName = "获取全部权限,并根据传入的角色ID标识出该角色拥有的权限", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", paramType = "query", required = true),
            @ApiImplicitParam(name = "appTag", value = "应用标识(不传查全部)", paramType = "query"),
    })
    @UseValidator
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> markRoleAuthoritys(@Required("角色ID不能为空") @NotEmpty("角色ID不能为空") String roleId,
                                               String appTag) {
        return Mono.fromFuture(auProvider.markRoleAuthoritys(roleId, appTag));
    }

    @ApiOperation(value = "保存角色的权限(替换原有权限)", notes = "保存角色的权限(替换原有权限)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/saveRoleAuthoritys")
    @AuthorityRegister(authorityName = "保存角色的权限(替换原有权限)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> saveRoleAuthoritys(@RequestBody SaveRoleAuthoritysRequest req) {
        return Mono.fromFuture(auProvider.saveRoleAuthoritys(req.getRoleId(), req.getAuIds(), req.getAppTag()));
    }

    @ApiOperation(value = "保存角色", notes = "保存角色(无ID则添加,有则修改),支持从指定角色中复制该角色的权限," +
            "或者提交的时候带上权限ID,会在保存的同时把权限关联上", httpMethod = "POST", produces = "application/json")
    @PostMapping("/saveRole")
    @AuthorityRegister(authorityName = "保存角色(无ID则添加,有则修改)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> saveRole(@RequestBody SaveRoleRequest req) {
        AlitaRole roleToSave = new AlitaRole();
        roleToSave.setId(req.getRoleId());
        roleToSave.setAppTag(req.getAppTag());
        roleToSave.setRoleName(req.getRoleName());
        roleToSave.setRemark(req.getRemark());
        if (CollectionUtils.isNotEmpty(req.getAuIdList())) {
            roleToSave.setAuthorityList(req.getAuIdList().stream().map(s -> {
                AlitaAuthoritysMenu au = new AlitaAuthoritysMenu();
                au.setId(s);
                return au;
            }).collect(Collectors.toList()));
        }
        return Mono.fromFuture(auProvider.saveRole(roleToSave, req.getCopyAuFromRoleId()));
    }

    @ApiOperation(value = "删除角色", notes = "角色删除(没有关联用户才能删,同时清空该角色和权限的关联)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/delRole")
    @AuthorityRegister(authorityName = "删除角色", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", paramType = "query", required = true)
    })
    @UseValidator
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> delRole(
            @Required(validatFailMessage = "角色ID不能为空")
            @NotEmpty(validatFailMessage = "角色ID不能为空")
                    String roleId
    ) {
        return Mono.fromFuture(auProvider.delRole(roleId));
    }

    @ApiOperation(value = "角色详情", notes = "角色详情", httpMethod = "GET", produces = "application/json")
    @GetMapping("/roleInfo")
    @AuthorityRegister(authorityName = "角色详情", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", paramType = "query", required = true)
    })
    @UseValidator
    @AuthoritysResource(parser = AppTagByIdParser.class)
    public Mono<JsonResult> roleInfo(
            @Required(validatFailMessage = "角色ID不能为空")
            @NotEmpty(validatFailMessage = "角色ID不能为空")
                    String roleId
    ) {
        return Mono.fromFuture(auProvider.roleInfo(roleId));
    }

    @ApiOperation(value = "权限目录列表(分页)", notes = "权限目录列表(分页)", httpMethod = "GET", produces = "application/json")
    @GetMapping("/catlogList")
    @AuthorityRegister(authorityName = "权限目录列表(分页)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "catlogName", value = "权限目录名称(支持模糊查询)", paramType = "query"),
            @ApiImplicitParam(name = "appTag", value = "应用标识(不传查全部)", paramType = "query"),
    })
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> catlogList(String catlogName, String appTag, PageRequest page) {
        return Mono.fromFuture(auProvider.catlogList(catlogName, appTag, new Page(page.getPage(), page.getSize())));
    }

    @ApiOperation(value = "获取全部权限目录,不分页", notes = "获取全部权限目录,不分页", httpMethod = "GET", produces = "application/json")
    @GetMapping("/catlogAll")
    @AuthorityRegister(authorityName = "获取全部权限目录,不分页", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appTag", value = "应用标识(不传查全部)", paramType = "query"),
    })
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> catlogAll(String appTag) {
        return Mono.fromFuture(auProvider.catlogAll(appTag));
    }

    @ApiOperation(value = "保存权限目录", notes = "保存权限目录(无ID则添加,有则修改)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/saveCatlog")
    @AuthorityRegister(authorityName = "保存权限目录(无ID则添加,有则修改)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> saveCatlog(@RequestBody SaveCatlogRequest req) {
        AlitaAuthoritysCatlog catlogToSave = new AlitaAuthoritysCatlog();
        BeanUtils.copyProperties(req, catlogToSave);
        return Mono.fromFuture(auProvider.saveCatlog(catlogToSave));
    }

    @ApiOperation(value = "删除权限目录", notes = "删除权限目录(没有权限关联到它才能删除)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/delCatlog")
    @AuthorityRegister(authorityName = "删除权限目录", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "catlogId", value = "权限目录ID", paramType = "query", required = true)
    })
    @UseValidator
    @AuthoritysResource(parser = AppTagByIdParser.class)
    public Mono<JsonResult> delCatlog(
            @Required(validatFailMessage = "权限目录ID不能为空")
            @NotEmpty(validatFailMessage = "权限目录ID不能为空")
                    String catlogId
    ) {
        return Mono.fromFuture(auProvider.delCatlog(catlogId));
    }

    @ApiOperation(value = "权限目录详情", notes = "权限目录详情", httpMethod = "GET", produces = "application/json")
    @GetMapping("/catlogInfo")
    @AuthorityRegister(authorityName = "权限目录详情", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "catlogId", value = "权限目录ID", paramType = "query", required = true)
    })
    @UseValidator
    @AuthoritysResource(parser = AppTagByIdParser.class)
    public Mono<JsonResult> catlogInfo(
            @Required(validatFailMessage = "权限目录ID不能为空")
            @NotEmpty(validatFailMessage = "权限目录ID不能为空")
                    String catlogId
    ) {
        return Mono.fromFuture(auProvider.catlogInfo(catlogId));
    }


    @ApiOperation(value = "权限列表(分页)", notes = "权限列表(分页)", httpMethod = "GET", produces = "application/json")
    @GetMapping("/authoritysList")
    @AuthorityRegister(authorityName = "权限列表-(分页)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "auName", value = "权限名称(支持模糊查询)", paramType = "query"),
            @ApiImplicitParam(name = "auType", value = "权限类型", paramType = "query"),
            @ApiImplicitParam(name = "authorityAction", value = "权限动作(支持模糊查询)", paramType = "query"),
            @ApiImplicitParam(name = "catlogName", value = "权限目录名称(支持模糊查询)", paramType = "query"),
            @ApiImplicitParam(name = "appTag", value = "应用标识(不传查全部)", paramType = "query"),
    })
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> authoritysMenuList(String auName, AuthorityTypeEnum auType,
                                               String authorityAction, String catlogName,
                                               String appTag, PageRequest page) {
        return Mono.fromFuture(
                auProvider.authoritysList(auName, auType,
                        new Page(page.getPage(), page.getSize()), authorityAction, catlogName, appTag));
    }

    @ApiOperation(value = "保存权限", notes = "保存权限(无ID则添加,有则修改)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/saveAuthority")
    @AuthorityRegister(authorityName = "保存权限(无ID则添加,有则修改)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> saveAuthority(@RequestBody SaveAuthoritysRequest req) {
        AlitaAuthoritysMenu auToSave = new AlitaAuthoritysMenu();
        BeanUtils.copyProperties(req, auToSave);
        return Mono.fromFuture(auProvider.saveAuthority(auToSave, null == req.getHttpMethod() ? null : req.getHttpMethod().name()));
    }

    @ApiOperation(value = "删除权限", notes = "删除权限(同时删除角色的关联)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/delAuthority")
    @AuthorityRegister(authorityName = "删除权限", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "auId", value = "权限ID", paramType = "query", required = true)
    })
    @UseValidator
    @AuthoritysResource(parser = AppTagByIdParser.class)
    public Mono<JsonResult> delAuthority(
            @Required(validatFailMessage = "权限ID不能为空")
            @NotEmpty(validatFailMessage = "权限ID不能为空")
                    String auId
    ) {
        return Mono.fromFuture(auProvider.delAuthority(auId));
    }

    @ApiOperation(value = "权限详情", notes = "权限详情", httpMethod = "GET", produces = "application/json")
    @GetMapping("/auInfo")
    @AuthorityRegister(authorityName = "权限详情", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "auId", value = "权限ID", paramType = "query", required = true)
    })
    @UseValidator
    @AuthoritysResource(parser = AppTagByIdParser.class)
    public Mono<JsonResult> auInfo(
            @Required(validatFailMessage = "权限ID不能为空")
            @NotEmpty(validatFailMessage = "权限ID不能为空")
                    String auId
    ) {
        return Mono.fromFuture(auProvider.auInfo(auId));
    }

    @ApiOperation(value = "获取全部权限(按目录分组)", notes = "获取全部权限(按目录分组),用于新增/编辑资源权限时选择所属分组", httpMethod = "GET", produces = "application/json")
    @GetMapping("/allAuList")
    @AuthorityRegister(authorityName = "获取全部权限(按目录分组)", authorityType = AuthorityTypeEnum.URL,
            authorityRemark = "获取全部权限(按目录分组),用于新增/编辑资源权限时选择所属分组",
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appTag", value = "应用标识(不传查全部)", paramType = "query"),
    })
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> allAuList(String appTag) {
        return Mono.fromFuture(auProvider.allAuthoritysWithCatlog(appTag));
    }

    @ApiOperation(value = "根据权限Action获取该权限下的当前登录用户拥有的资源权限和全局资源权限", notes = "根据权限Action获取该权限下的当前登录用户拥有的资源权限和全局资源权限", httpMethod = "GET", produces = "application/json")
    @GetMapping("/authoritysResourceByAuAction")
    @AuthorityRegister(authorityName = "根据权限Action获取该权限下的资源权限和全局资源权限", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "httpMethod", value = "httpMethod", paramType = "query"),
            @ApiImplicitParam(name = "auAction", value = "权限Action", paramType = "query", required = true),
            @ApiImplicitParam(name = "appTag", value = "应用标识", paramType = "query", required = true),
    })
    @UseValidator
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> authoritysResourceByAuAction(
            @ApiIgnore
                    ServerHttpRequest request,

            HttpMethodPrarm httpMethod,

            @Required(validatFailMessage = "权限Action不能为空")
            @NotEmpty(validatFailMessage = "权限Action不能为空")
                    String auAction,

            @Required(validatFailMessage = "应用标识不能为空")
            @NotEmpty(validatFailMessage = "应用标识不能为空")
                    String appTag
    ) {
        String userId = ResServerTokenUtil.getUserId(request);
        if (userId == null) {
            return Mono.just(JsonResult.failed(CommonResultCodeEnum.TOKEN_ERR));
        }
        return Mono.fromFuture(auProvider.authoritysResourceByAuthorityAction(httpMethod == null ? null : httpMethod.name(), auAction, appTag, userId));
    }

    @ApiOperation(value = "资源权限列表(分页)", notes = "资源权限列表(分页)", httpMethod = "GET", produces = "application/json")
    @GetMapping("/authoritysResourceList")
    @AuthorityRegister(authorityName = "资源权限列表(分页)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "resource", value = "资源名称(支持模糊查询)", paramType = "query"),
            @ApiImplicitParam(name = "appTag", value = "应用标识(不传查全部)", paramType = "query"),
    })
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> authoritysResourceList(String resource, String appTag, PageRequest page) {
        return Mono.fromFuture(auProvider.authoritysResourceList(resource, appTag, new Page(page.getPage(), page.getSize())));
    }

    @ApiOperation(value = "保存资源权限", notes = "保存资源权限(无ID则添加,有则修改)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/saveAuthoritysResource")
    @AuthorityRegister(authorityName = "保存资源权限(无ID则添加,有则修改)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    @AuthoritysResource(parser = AppTagParser.class)
    public Mono<JsonResult> saveAuthoritysResource(@RequestBody SaveAuthoritysResourceRequest req) {
        AlitaAuthoritysResource dsToSave = new AlitaAuthoritysResource();
        BeanUtils.copyProperties(req, dsToSave);
        return Mono.fromFuture(auProvider.saveAuthoritysResource(dsToSave));
    }

    @ApiOperation(value = "删除资源权限", notes = "删除资源权限(没有角色关联它才能删除)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/delAuthoritysResource")
    @AuthorityRegister(authorityName = "删除资源权限", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dsId", value = "资源权限ID", paramType = "query", required = true)
    })
    @UseValidator
    @AuthoritysResource(parser = AppTagByIdParser.class)
    public Mono<JsonResult> delAuthoritysResource(
            @Required(validatFailMessage = "资源权限ID不能为空")
            @NotEmpty(validatFailMessage = "资源权限ID不能为空")
                    String dsId
    ) {
        return Mono.fromFuture(auProvider.delAuthoritysResource(dsId));
    }

    @ApiOperation(value = "资源权限详情", notes = "资源权限详情", httpMethod = "GET", produces = "application/json")
    @GetMapping("/authoritysResourceInfo")
    @AuthorityRegister(authorityName = "资源权限详情", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dsId", value = "资源权限ID", paramType = "query", required = true)
    })
    @UseValidator
    @AuthoritysResource(parser = AppTagByIdParser.class)
    public Mono<JsonResult> authoritysResourceInfo(
            @Required(validatFailMessage = "资源权限ID不能为空")
            @NotEmpty(validatFailMessage = "资源权限ID不能为空")
                    String dsId
    ) {
        return Mono.fromFuture(auProvider.authoritysResourceInfo(dsId));
    }

}
