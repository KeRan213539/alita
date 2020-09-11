package top.klw8.alita.web.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
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

    @Reference(async = true)
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
