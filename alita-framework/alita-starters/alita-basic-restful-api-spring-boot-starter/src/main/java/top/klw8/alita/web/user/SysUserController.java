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
package top.klw8.alita.web.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.result.code.CommonResultCodeEnum;
import top.klw8.alita.starter.annotations.AuthorityCatlogRegister;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.cfg.AuthorityAppInfoInConfigBean;
import top.klw8.alita.starter.utils.ResServerTokenUtil;
import top.klw8.alita.validator.UseValidator;

import static top.klw8.alita.web.common.CatlogsConstant.CATLOG_NAME_USER_CORRELATION;
import static top.klw8.alita.web.common.CatlogsConstant.CATLOG_INDEX_USER_CORRELATION;


/**
 * @author klw(213539 @ qq.com)
 * @ClassName: SysUserController
 * @Description: 用户相关
 * @date 2019/9/26 16:50
 */
@Api(tags = {"alita-restful-API--用户相关"})
@RestController
@RequestMapping("/${spring.application.name}/user")
@Slf4j
@AuthorityCatlogRegister(name = CATLOG_NAME_USER_CORRELATION, showIndex = CATLOG_INDEX_USER_CORRELATION)
public class SysUserController {

    @DubboReference(async = true)
    private IAlitaUserProvider userProvider;

    @Autowired
    private AuthorityAppInfoInConfigBean currectApp;

    @ApiOperation(value = "获取当前登录用户的菜单", notes = "获取当前登录用户的菜单", httpMethod = "POST", produces = "application/json")
    @PostMapping("/userMenus")
    @UseValidator
    @AuthorityRegister(authorityName = "获取当前登录用户的菜单", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    public Mono<JsonResult> userMenus(@ApiIgnore ServerHttpRequest request){
        String userId = ResServerTokenUtil.getUserId(request);
        if (userId == null) {
            return Mono.just(JsonResult.failed(CommonResultCodeEnum.TOKEN_ERR));
        }
        return Mono.fromFuture(userProvider.findUserAuthorityMenus(userId, currectApp.getAppTag()));
    }

}
