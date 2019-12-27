package top.klw8.alita.web.authority;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.starter.annotations.AuthorityCatlogRegister;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.web.base.WebapiBaseController;
import top.klw8.alita.validator.UseValidator;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;
import top.klw8.alita.web.authority.vo.SaveAuthoritysRequest;
import top.klw8.alita.web.authority.vo.SaveRoleRequest;
import top.klw8.alita.starter.web.common.vo.PageRequest;
import top.klw8.alita.web.authority.vo.SaveRoleAuthoritysRequest;
import top.klw8.alita.web.authority.vo.SaveCatlogRequest;

import java.util.stream.Collectors;

import static top.klw8.alita.web.common.CatlogsConstant.CATLOG_NAME_AU_ADMIN;
import static top.klw8.alita.web.common.CatlogsConstant.CATLOG_INDEX_AU_ADMIN;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AuthorityAdminController
 * @Description: 权限管理相关(角色管理,权限目录管理,权限管理)
 * @date 2019/10/15 14:13
 */
@Api(tags = {"alita-restful-API--权限管理"})
@RestController
@RequestMapping("/${spring.application.name}/admin/au")
@Slf4j
@AuthorityCatlogRegister(name = CATLOG_NAME_AU_ADMIN, showIndex = CATLOG_INDEX_AU_ADMIN)
public class AuthorityAdminController extends WebapiBaseController {

    @Reference(async = true)
    private IAuthorityAdminProvider auProvider;

    @ApiOperation(value = "角色列表(分页)", notes = "角色列表(分页)", httpMethod = "GET", produces = "application/json")
    @GetMapping("/roleList")
    @AuthorityRegister(authorityName = "角色列表(分页)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名称(支持模糊查询)", paramType = "query"),
    })
    public Mono<JsonResult> roleList(String roleName, PageRequest page){
        return Mono.fromFuture(auProvider.roleList(roleName, new Page(page.getPage(), page.getSize())));
    }

    @ApiOperation(value = "获取全部权限,并根据传入的角色ID标识出该角色拥有的权限", notes = "获取全部权限,并根据传入的角色ID标识出该角色拥有的权限", httpMethod = "GET", produces = "application/json")
    @GetMapping("/markRoleAuthoritys")
    @AuthorityRegister(authorityName = "获取全部权限,并根据传入的角色ID标识出该角色拥有的权限", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", paramType = "query"),
    })
    @UseValidator
    public Mono<JsonResult> markRoleAuthoritys(String roleId){
        return Mono.fromFuture(auProvider.markRoleAuthoritys(roleId));
    }

    @ApiOperation(value = "保存角色的权限(替换原有权限)", notes = "保存角色的权限(替换原有权限)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/saveRoleAuthoritys")
    @AuthorityRegister(authorityName = "保存角色的权限(替换原有权限)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> saveRoleAuthoritys(@RequestBody SaveRoleAuthoritysRequest req){
        return Mono.fromFuture(auProvider.saveRoleAuthoritys(req.getRoleId(), req.getAuIds()));
    }

    @ApiOperation(value = "保存角色", notes = "保存角色(无ID则添加,有则修改),支持从指定角色中复制该角色的权限," +
            "或者提交的时候带上权限ID,会在保存的同时把权限关联上", httpMethod = "POST", produces = "application/json")
    @PostMapping("/saveRole")
    @AuthorityRegister(authorityName = "保存角色(无ID则添加,有则修改)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> saveRole(@RequestBody SaveRoleRequest req){
        SystemRole roleToSave = new SystemRole();
        roleToSave.setRoleName(req.getRoleName());
        roleToSave.setRemark(req.getRemark());
        roleToSave.setAuthorityList(req.getAuIdList().stream().map(s -> {
            SystemAuthoritys au = new SystemAuthoritys();
            au.setId(s);
            return au;
        }).collect(Collectors.toList()));
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
    public Mono<JsonResult> delRole(
            @Required(validatFailMessage = "角色ID不能为空")
            @NotEmpty(validatFailMessage = "角色ID不能为空")
            String roleId
    ){
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
    public Mono<JsonResult> roleInfo(
            @Required(validatFailMessage = "角色ID不能为空")
            @NotEmpty(validatFailMessage = "角色ID不能为空")
                    String roleId
    ){
        return Mono.fromFuture(auProvider.roleInfo(roleId));
    }

    @ApiOperation(value = "权限目录列表(分页)", notes = "权限目录列表(分页)", httpMethod = "GET", produces = "application/json")
    @GetMapping("/catlogList")
    @AuthorityRegister(authorityName = "权限目录列表(分页)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "catlogName", value = "权限目录名称(支持模糊查询)", paramType = "query"),
    })
    public Mono<JsonResult> catlogList(String catlogName, PageRequest page){
        return Mono.fromFuture(auProvider.catlogList(catlogName, new Page(page.getPage(), page.getSize())));
    }

    @ApiOperation(value = "获取全部权限目录,不分页", notes = "获取全部权限目录,不分页", httpMethod = "GET", produces = "application/json")
    @GetMapping("/catlogAll")
    @AuthorityRegister(authorityName = "获取全部权限目录,不分页", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> catlogAll(){
        return Mono.fromFuture(auProvider.catlogAll());
    }

    @ApiOperation(value = "保存权限目录", notes = "保存权限目录(无ID则添加,有则修改)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/saveCatlog")
    @AuthorityRegister(authorityName = "保存权限目录(无ID则添加,有则修改)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> saveCatlog(@RequestBody SaveCatlogRequest req){
        SystemAuthoritysCatlog catlogToSave = new SystemAuthoritysCatlog();
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
    public Mono<JsonResult> delCatlog(
            @Required(validatFailMessage = "权限目录ID不能为空")
            @NotEmpty(validatFailMessage = "权限目录ID不能为空")
                    String catlogId
    ){
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
    public Mono<JsonResult> catlogInfo(
            @Required(validatFailMessage = "权限目录ID不能为空")
            @NotEmpty(validatFailMessage = "权限目录ID不能为空")
                    String catlogId
    ){
        return Mono.fromFuture(auProvider.catlogInfo(catlogId));
    }


    @ApiOperation(value = "菜单权限列表(分页)", notes = "菜单权限列表(分页)", httpMethod = "GET", produces = "application/json")
    @GetMapping("/authoritysMenuList")
    @AuthorityRegister(authorityName = "菜单权限列表-(分页)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "auName", value = "权限名称(支持模糊查询)", paramType = "query"),
    })
    public Mono<JsonResult> authoritysMenuList(String auName, PageRequest page){
        return Mono.fromFuture(auProvider.authoritysMenuList(auName, new Page(page.getPage(), page.getSize())));
    }

    @ApiOperation(value = "保存权限", notes = "保存权限(无ID则添加,有则修改)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/saveAuthority")
    @AuthorityRegister(authorityName = "保存权限(无ID则添加,有则修改)", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> saveAuthority(@RequestBody SaveAuthoritysRequest req){
        SystemAuthoritys auToSave = new SystemAuthoritys();
        BeanUtils.copyProperties(req, auToSave);
        return Mono.fromFuture(auProvider.saveAuthority(auToSave));
    }

    @ApiOperation(value = "删除权限", notes = "删除权限(同时删除角色的关联)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/delAuthority")
    @AuthorityRegister(authorityName = "删除权限", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "auId", value = "权限ID", paramType = "query", required = true)
    })
    @UseValidator
    public Mono<JsonResult> delAuthority(
            @Required(validatFailMessage = "权限ID不能为空")
            @NotEmpty(validatFailMessage = "权限ID不能为空")
                    String auId
    ){
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
    public Mono<JsonResult> auInfo(
            @Required(validatFailMessage = "权限ID不能为空")
            @NotEmpty(validatFailMessage = "权限ID不能为空")
                    String auId
    ){
        return Mono.fromFuture(auProvider.auInfo(auId));
    }

}
