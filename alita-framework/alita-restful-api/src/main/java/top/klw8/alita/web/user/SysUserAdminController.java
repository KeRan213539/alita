package top.klw8.alita.web.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.validator.UseValidator;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;
import top.klw8.alita.web.user.vo.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: SysUserAdminController
 * @Description: 用户管理
 * @date 2019/10/15 14:08
 */
@Api(tags = {"alita-restful-API--用户管理"})
@RestController
@RequestMapping("/${spring.application.name}/admin/user")
@Slf4j
public class SysUserAdminController {

    @Reference(async = true)
    private IAlitaUserProvider userProvider;

    @ApiOperation(value = "用户列表(分页)", notes = "用户列表(分页)", httpMethod = "GET", produces = "application/json")
    @GetMapping("/userList")
    @AuthorityRegister(catlogName = "用户管理", catlogShowIndex = 99,
            authorityName = "用户列表(分页)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户姓名(支持模糊查询)", paramType = "query"),
            @ApiImplicitParam(name = "locked", value = "账户是否锁定,不传查全部状态", paramType = "query",
                    dataType = "boolean", example = "true"),
            @ApiImplicitParam(name = "createDateBegin", value = "注册时间-开始", paramType = "query",
                    dataType = "java.time.LocalDate", example = "2019-10-24"),
            @ApiImplicitParam(name = "createDateEnd", value = "注册时间-结束", paramType = "query",
                    dataType = "java.time.LocalDate", example = "2019-10-25")
    })
    public Mono<JsonResult> userList(String userName, Boolean locked, LocalDate createDateBegin,
                                     LocalDate createDateEnd, PageRequest page){
        AlitaUserAccount user = new AlitaUserAccount();
        if(StringUtils.isNotBlank(userName)){
            user.setUserName(userName);
        }
        if(null != locked){
            user.setAccountNonLocked1(!locked);
        }
        return Mono.fromFuture(userProvider.userList(user, createDateBegin, createDateEnd, new Page(page.getPage(), page.getSize())));
    }

    @ApiOperation(value = "根据用户ID查询该用户的角色和权限", notes = "根据用户ID查询该用户的角色和权限", httpMethod = "GET", produces = "application/json")
    @GetMapping("/userAllRoles")
    @AuthorityRegister(catlogName = "用户管理", catlogShowIndex = 99,
            authorityName = "根据用户ID查询该用户的角色和权限", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户I", paramType = "query", required = true)
    })
    @UseValidator
    public Mono<JsonResult> userAllRoles(@Required @NotEmpty String userId){
        return Mono.fromFuture(userProvider.getUserAllRoles(userId));
    }

}
