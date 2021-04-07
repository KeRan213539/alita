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
package top.klw8.alita.web.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.starter.annotations.AuthorityCatlogRegister;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.cfg.AuthorityAppInfoInConfigBean;
import top.klw8.alita.starter.common.UserCacheHelper;
import top.klw8.alita.starter.datasecured.DataSecured;
import top.klw8.alita.starter.utils.ResServerTokenUtil;
import top.klw8.alita.starter.web.base.WebapiBaseController;
import top.klw8.alita.utils.redis.TokenRedisUtil;
import top.klw8.alita.validator.UseValidator;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;
import top.klw8.alita.web.authority.ds.AppTagParser;
import top.klw8.alita.web.user.vo.AlitaUserAccountRequest;
import top.klw8.alita.web.user.vo.ChangeUserPasswordByUserIdRequest;
import top.klw8.alita.starter.web.common.vo.PageRequest;
import top.klw8.alita.web.user.vo.ChangeUserStatusRequest;
import top.klw8.alita.web.user.vo.SaveUserRolesRequest;

import java.time.LocalDate;

import static top.klw8.alita.web.common.CatlogsConstant.CATLOG_NAME_USER_ADMIN;
import static top.klw8.alita.web.common.CatlogsConstant.CATLOG_INDEX_USER_ADMIN;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: SysUserAdminController
 * @Description: 用户管理
 * @date 2019/10/15 14:08
 */
@Api(tags = {"alita-restful-API--用户管理"})
@RestController
@RequestMapping("/${spring.application.name}/admin/userManage")
@Slf4j
@AuthorityCatlogRegister(name = CATLOG_NAME_USER_ADMIN, showIndex = CATLOG_INDEX_USER_ADMIN)
public class SysUserAdminController extends WebapiBaseController {

    @DubboReference(async = true)
    private IAlitaUserProvider userProvider;

    @Autowired
    private AuthorityAppInfoInConfigBean currectApp;

    @Autowired
    private UserCacheHelper userCacheHelper;

    @Value("${alita.oauth2.token.storeInRedis:false}")
    private boolean tokenStoreUseReids;

    @ApiOperation(value = "用户列表(分页)", notes = "用户列表(分页)", httpMethod = "GET", produces = "application/json")
    @GetMapping("/userList")
    @AuthorityRegister(authorityName = "用户列表(分页)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户姓名(支持模糊查询)", paramType = "query"),
            @ApiImplicitParam(name = "enabled", value = "账户是否启用,不传查全部状态", paramType = "query",
                    dataType = "boolean", example = "true"),
            @ApiImplicitParam(name = "createDateBegin", value = "注册时间-开始", paramType = "query",
                    dataType = "java.time.LocalDate", example = "2019-10-24"),
            @ApiImplicitParam(name = "createDateEnd", value = "注册时间-结束", paramType = "query",
                    dataType = "java.time.LocalDate", example = "2019-10-25"),
            @ApiImplicitParam(name = "appTag", value = "应用标识(不传查全部)", paramType = "query"),
    })
    @DataSecured(parser = AppTagParser.class)
    public Mono<JsonResult> userList(String userName, Boolean enabled, @RequestParam(required = false) LocalDate createDateBegin,
                                     @RequestParam(required = false) LocalDate createDateEnd, String appTag, PageRequest page){
        AlitaUserAccount user = new AlitaUserAccount();
        if(StringUtils.isNotBlank(userName)){
            user.setUserName(userName);
        }
        if(null != enabled){
            user.setEnabled1(enabled);
        }
        return Mono.fromFuture(userProvider.userList(user, createDateBegin, createDateEnd, appTag, new Page(page.getPage(), page.getSize())));
    }

    @ApiOperation(value = "根据用户ID查询该用户的角色和权限", notes = "根据用户ID查询该用户的角色和权限", httpMethod = "GET", produces = "application/json")
    @GetMapping("/userAllRoles")
    @AuthorityRegister(authorityName = "根据用户ID查询该用户的角色和权限", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "query", required = true),
            @ApiImplicitParam(name = "appTag", value = "应用标识(不传查全部)", paramType = "query"),
    })
    @UseValidator
    @DataSecured(parser = AppTagParser.class)
    public Mono<JsonResult> userAllRoles(
            @Required(validatFailMessage = "用户ID不能为空")
            @NotEmpty(validatFailMessage = "用户ID不能为空")
                    String userId
            , String appTag
    ){
        return Mono.fromFuture(userProvider.getUserAllRoles(userId, appTag));
    }

    @ApiOperation(value = "保存用户拥有的角色(替换原有角色)", notes = "保存用户拥有的角色(替换原有角色)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/saveUserRoles")
    @AuthorityRegister(authorityName = "保存用户拥有的角色(替换原有角色)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    @DataSecured(parser = AppTagParser.class)
    public Mono<JsonResult> saveUserRoles(@RequestBody SaveUserRolesRequest req){
        return Mono.fromFuture(userProvider.saveUserRoles(req.getUserId(), req.getRoleIds(), req.getAppTag()));
    }

    @ApiOperation(value = "新增用户", notes = "新增用户", httpMethod = "POST", produces = "application/json")
    @PostMapping("/addSaveUser")
    @AuthorityRegister(authorityName = "新增用户", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> addSaveUser(@RequestBody AlitaUserAccountRequest req){
        if(!req.getUserPwd().equals(req.getUserPwd2())){
            return Mono.just(JsonResult.badParameter("两次输入的密码不一致"));
        }
        AlitaUserAccount userToSave = new AlitaUserAccount();
        BeanUtils.copyProperties(req, userToSave);
        return Mono.fromFuture(userProvider.addSaveUser(userToSave));
    }

    @ApiOperation(value = "根据用户ID修改用户密码", notes = "根据用户ID修改用户密码", httpMethod = "POST", produces = "application/json")
    @PostMapping("/changeUserPasswordByUserId")
    @AuthorityRegister(authorityName = "根据用户ID修改用户密码", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> changeUserPasswordByUserId(@RequestBody ChangeUserPasswordByUserIdRequest req){
        if(!req.getNewPwd().equals(req.getNewPwd2())){
            return Mono.just(JsonResult.badParameter("两次输入的密码不一致"));
        }
        return Mono.fromFuture(userProvider.changeUserPasswordByUserId(req.getUserId(), req.getOldPwd(),
                req.getNewPwd()));
    }

    @ApiOperation(value = "用户详情", notes = "用户详情(不返回密码)", httpMethod = "GET", produces = "application/json")
    @GetMapping("/userInfo")
    @AuthorityRegister(authorityName = "用户详情(不返回密码)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "query", required = true)
    })
    @UseValidator
    public Mono<JsonResult> userInfo(
            @Required(validatFailMessage = "用户ID不能为空")
            @NotEmpty(validatFailMessage = "用户ID不能为空")
            @ApiParam(value = "用户ID", required=true)
            String userId
    ){
        return Mono.fromFuture(userProvider.userInfo(userId));
    }

    @ApiOperation(value = "禁用/启用用户", notes = "禁用/启用用户", httpMethod = "POST", produces = "application/json")
    @PostMapping("/changeUserStatus")
    @AuthorityRegister(authorityName = "禁用/启用用户", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> changeUserStatus(ServerHttpRequest request, @RequestBody ChangeUserStatusRequest req){
        if(tokenStoreUseReids && !req.getEnabled().booleanValue()){
            // 禁用用户,移除缓存中的token
            String[] appTagAndChannelTag = ResServerTokenUtil.getAppTagAndChannelTag(ResServerTokenUtil.getToken(request));
            TokenRedisUtil.removeAccessToken(req.getUserId(), appTagAndChannelTag[0], appTagAndChannelTag[1]);
            TokenRedisUtil.removeRefreshToken(req.getUserId(), appTagAndChannelTag[0], appTagAndChannelTag[1]);
            userCacheHelper.removeUserAuthoritysInCache(req.getUserId(), currectApp.getAppTag());
        }
        return Mono.fromFuture(userProvider.changeUserStatus(req.getUserId(), req.getEnabled().booleanValue()));
    }

}
