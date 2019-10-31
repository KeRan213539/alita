package top.klw8.alita.web.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.starter.annotations.AuthorityCatlogRegister;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.web.base.WebapiBaseController;
import top.klw8.alita.validator.UseValidator;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;
import top.klw8.alita.web.user.vo.AlitaUserAccountRequest;
import top.klw8.alita.web.user.vo.ChangeUserPasswordByUserIdRequest;
import top.klw8.alita.starter.web.common.vo.PageRequest;
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
@RequestMapping("/${spring.application.name}/admin/user")
@Slf4j
@AuthorityCatlogRegister(name = CATLOG_NAME_USER_ADMIN, showIndex = CATLOG_INDEX_USER_ADMIN)
public class SysUserAdminController extends WebapiBaseController {

    @Reference(async = true)
    private IAlitaUserProvider userProvider;

    @ApiOperation(value = "用户列表(分页)", notes = "用户列表(分页)", httpMethod = "GET", produces = "application/json")
    @GetMapping("/userList")
    @AuthorityRegister(authorityName = "用户列表(分页)", authorityType = AuthorityTypeEnum.URL,
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
    @AuthorityRegister(authorityName = "根据用户ID查询该用户的角色和权限", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "query", required = true)
    })
    @UseValidator
    public Mono<JsonResult> userAllRoles(
            @Required(validatFailMessage = "用户ID不能为空")
            @NotEmpty(validatFailMessage = "用户ID不能为空")
            @ApiParam(value = "用户ID", required=true)
                    String userId
    ){
        return Mono.fromFuture(userProvider.getUserAllRoles(userId));
    }

    @ApiOperation(value = "保存用户拥有的角色(替换原有角色)", notes = "保存用户拥有的角色(替换原有角色)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/saveUserRoles")
    @AuthorityRegister(authorityName = "保存用户拥有的角色(替换原有角色)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> saveUserRoles(@RequestBody SaveUserRolesRequest req){
        return Mono.fromFuture(userProvider.saveUserRoles(req.getUserId(), req.getRoleIds()));
    }

    @ApiOperation(value = "新增用户", notes = "新增用户", httpMethod = "POST", produces = "application/json")
    @PostMapping("/addSaveUser")
    @AuthorityRegister(authorityName = "新增用户", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> addSaveUser(@RequestBody AlitaUserAccountRequest req){
        if(!req.getUserPwd().equals(req.getUserPwd2())){
            return Mono.just(JsonResult.sendBadParameterResult("两次输入的密码不一致"));
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
            return Mono.just(JsonResult.sendBadParameterResult("两次输入的密码不一致"));
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
    public Mono<JsonResult> changeUserStatus(
            @Required(validatFailMessage = "用户ID不能为空")
            @NotEmpty(validatFailMessage = "用户ID不能为空")
            @ApiParam(value = "用户ID", required=true)
                    String userId,
            @Required(validatFailMessage = "是否启用不能为空")
            @NotEmpty(validatFailMessage = "是否启用不能为空")
            @ApiParam(value = "是否启用(true为启用,false为禁用)", required=true)
                    Boolean enabled
    ){
        return Mono.fromFuture(userProvider.changeUserStatus(userId, enabled.booleanValue()));
    }

}
