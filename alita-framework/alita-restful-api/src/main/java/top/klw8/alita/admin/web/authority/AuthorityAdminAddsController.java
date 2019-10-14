package top.klw8.alita.admin.web.authority;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Mono;
import top.klw8.alita.admin.web.authority.vo.AddRoleAuRequest;
import top.klw8.alita.admin.web.authority.vo.AddUserRoleRequest;
import top.klw8.alita.admin.web.authority.vo.SystemAuthoritysCatlogVo;
import top.klw8.alita.admin.web.authority.vo.SystemAuthoritysVo;
import top.klw8.alita.admin.web.authority.vo.SystemRoleVo;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.web.base.WebapiBaseController;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.validator.UseValidator;

/**
 * @author klw
 * @ClassName: AuthorityAdminController
 * @Description: 权限管理相关--新增操作
 * @date 2018年11月28日 下午5:12:56
 */
@Api(tags = {"alita-restful-API--权限管理--新增"})
@RestController
@RequestMapping("/${spring.application.name}/admin/au")
@Slf4j
public class AuthorityAdminAddsController extends WebapiBaseController {

    @Reference(async = true)
    private IAuthorityAdminProvider auProvider;

    @ApiOperation(value = "新增权限", notes = "新增权限", httpMethod = "POST", produces = "application/json")
    @PostMapping("/addAuthority")
    @UseValidator
    @AuthorityRegister(catlogName = "权限管理", catlogShowIndex = 99,
            authorityName = "添加权限", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    public Mono<JsonResult> addAuthority(SystemAuthoritysVo auvo) {
        SystemAuthoritys target = new SystemAuthoritys();
        BeanUtils.copyProperties(auvo, target);
        return Mono.fromFuture(auProvider.addAuthority(target));

    }

    @ApiOperation(value = "新增权限目录", notes = "新增权限目录", httpMethod = "POST", produces = "application/json")
    @PostMapping("/addCatlog")
    @UseValidator
    @AuthorityRegister(catlogName = "权限管理", catlogShowIndex = 99,
            authorityName = "添加权限目录", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    public Mono<JsonResult> addCatlog(SystemAuthoritysCatlogVo catlogvo) {
        SystemAuthoritysCatlog catlog = new SystemAuthoritysCatlog();
        BeanUtils.copyProperties(catlogvo, catlog);
        return Mono.fromFuture(auProvider.addCatlog(catlog));
    }

    @ApiOperation(value = "新增角色", notes = "新增角色", httpMethod = "POST", produces = "application/json")
    @PostMapping("/addSysRole")
    @UseValidator
    @AuthorityRegister(catlogName = "权限管理", catlogShowIndex = 99,
            authorityName = "添加角色", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    public Mono<JsonResult> addSysRole(SystemRoleVo rolevo) {
        SystemRole role = new SystemRole();
        BeanUtils.copyProperties(rolevo, role);
        return Mono.fromFuture(auProvider.addSysRole(role));
    }

    @ApiOperation(value = "向角色中添加权限", notes = "向角色中添加权限", httpMethod = "POST", produces = "application/json")
    @PostMapping("/addRoleAu")
    @UseValidator
    @AuthorityRegister(catlogName = "权限管理", catlogShowIndex = 99,
            authorityName = "向角色中添加权限", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    public Mono<JsonResult> addRoleAu(AddRoleAuRequest req) {
        return Mono.fromFuture(auProvider.addRoleAu(req.getRoleId(), req.getAuId()));
    }

    @ApiOperation(value = "向用户中添加角色", notes = "向用户中添加角色", httpMethod = "POST", produces = "application/json")
    @PostMapping("/addUserRole")
    @UseValidator
    @AuthorityRegister(catlogName = "权限管理", catlogShowIndex = 99,
            authorityName = "向用户中添加角色", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    public Mono<JsonResult> addUserRole(AddUserRoleRequest req) {
        return Mono.fromFuture(auProvider.addUserRole(req.getUserId(), req.getRoleId()));
    }

}
