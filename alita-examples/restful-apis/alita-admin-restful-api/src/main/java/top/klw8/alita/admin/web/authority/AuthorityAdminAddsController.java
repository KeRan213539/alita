package top.klw8.alita.admin.web.authority;

import java.util.concurrent.Future;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;
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
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.authority.ISystemAuthoritysCatlogService;
import top.klw8.alita.service.api.authority.ISystemAuthoritysService;
import top.klw8.alita.service.api.authority.ISystemRoleService;
import top.klw8.alita.service.api.user.IAlitaUserService;
import top.klw8.alita.service.utils.EntityUtil;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.web.base.WebapiBaseController;
import top.klw8.alita.starter.web.common.CallBackMessage;
import top.klw8.alita.starter.web.common.JsonResult;
import top.klw8.alita.utils.generator.PkGeneratorBySnowflake;
import top.klw8.alita.validator.UseValidator;

/**
 * @ClassName: AuthorityAdminController
 * @Description: 权限管理相关--新增操作
 * @author klw
 * @date 2018年11月28日 下午5:12:56
 */
@Api(tags = {"alita-restful-API--权限管理--新增"})
@RestController
@RequestMapping("/admin/au")
public class AuthorityAdminAddsController extends WebapiBaseController {

    @Reference(async=true)
    private ISystemAuthoritysService auService;
    
    @Reference(async=true)
    private ISystemAuthoritysCatlogService catlogService;
    
    @Reference(async=true)
    private ISystemRoleService roleService;
    
    @Reference(async=true)
    private IAlitaUserService userService;
    
    @ApiOperation(value = "新增权限", notes = "新增权限", httpMethod = "POST", produces = "application/json")
    @PostMapping("/addAuthority")
    @UseValidator
    @AuthorityRegister(catlogName = "权限管理", catlogShowIndex = 99,
	    authorityName = "添加权限", authorityType = AuthorityTypeEnum.URL,
	    authorityShowIndex = 0)
    public Mono<JsonResult> addAuthority(SystemAuthoritysVo auvo) throws Exception {
	Long catlogId = auvo.getCatlogId();
	catlogService.findById(catlogId);
	Future<SystemAuthoritysCatlog> catlogTask = RpcContext.getContext().getFuture();
	SystemAuthoritysCatlog catlog = catlogTask.get();
	if(catlog == null) {
	    return Mono.just(JsonResult.sendFailedResult("权限目录不存在【" + catlogId + "】", null)); 
	}
	SystemAuthoritys au = new SystemAuthoritys();
	BeanUtils.copyProperties(auvo, au);
	au.setId(PkGeneratorBySnowflake.INSTANCE.nextId());
	au.setCatlog(catlog);
	SystemAuthoritys saveResult = auService.save(au);
	if(EntityUtil.isEntityNotEmpty(saveResult)) {
	    catlogService.addAuthority2Catlog(catlog.getId(), au);
	    Future<Integer> addResultTask = RpcContext.getContext().getFuture();
	    int addResult = addResultTask.get();
	    if (addResult > 0) {
		return Mono.just(JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, "保存成功"));
	    } else {
		return Mono.just(JsonResult.sendFailedResult("保存失败", null));
	    }
	} else {
	    return Mono.just(JsonResult.sendFailedResult("保存失败", null)); 
	}
	
    }
    
    @ApiOperation(value = "新增权限目录", notes = "新增权限目录", httpMethod = "POST", produces = "application/json")
    @PostMapping("/addCatlog")
    @UseValidator
    @AuthorityRegister(catlogName = "权限管理", catlogShowIndex = 99,
	    authorityName = "添加权限目录", authorityType = AuthorityTypeEnum.URL,
    authorityShowIndex = 0)
    public Mono<JsonResult> addCatlog(SystemAuthoritysCatlogVo catlogvo) throws Exception {
	SystemAuthoritysCatlog catlog = new SystemAuthoritysCatlog();
	BeanUtils.copyProperties(catlogvo, catlog);
	catlogService.save(catlog);
	Future<SystemAuthoritysCatlog> saveResultTask = RpcContext.getContext().getFuture();
	SystemAuthoritysCatlog saveResult = saveResultTask.get();
	if(EntityUtil.isEntityNotEmpty(saveResult)) {
	    return Mono.just(JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, "保存成功")); 
	} else {
	    return Mono.just(JsonResult.sendFailedResult("保存失败", null)); 
	}
    }
    
    @ApiOperation(value = "新增角色", notes = "新增角色", httpMethod = "POST", produces = "application/json")
    @PostMapping("/addSysRole")
    @UseValidator
    @AuthorityRegister(catlogName = "权限管理", catlogShowIndex = 99,
	    authorityName = "添加角色", authorityType = AuthorityTypeEnum.URL,
    authorityShowIndex = 0)
    public Mono<JsonResult> addSysRole(SystemRoleVo rolevo) throws Exception {
	SystemRole role = new SystemRole();
	BeanUtils.copyProperties(rolevo, role);
	roleService.save(role);
	Future<SystemRole> saveResultTask = RpcContext.getContext().getFuture();
	SystemRole saveResult = saveResultTask.get();
	if(EntityUtil.isEntityNotEmpty(saveResult)) {
	    return Mono.just(JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, "保存成功")); 
	} else {
	    return Mono.just(JsonResult.sendFailedResult("保存失败", null)); 
	}
    }
    
    @ApiOperation(value = "向角色中添加权限", notes = "向角色中添加权限", httpMethod = "POST", produces = "application/json")
    @PostMapping("/addRoleAu")
    @UseValidator
    @AuthorityRegister(catlogName = "权限管理", catlogShowIndex = 99,
	    authorityName = "向角色中添加权限", authorityType = AuthorityTypeEnum.URL,
    authorityShowIndex = 0)
    public Mono<JsonResult> addRoleAu(AddRoleAuRequest req) throws Exception {
	roleService.findById(req.getRoleId());
	Future<SystemRole> roleTask = RpcContext.getContext().getFuture();
	SystemRole role = roleTask.get();
	if(role == null || null == role.getId()) {
	    return Mono.just(JsonResult.sendFailedResult("角色不存在", null)); 
	}
	auService.findById(req.getAuId());
	Future<SystemAuthoritys> auTask = RpcContext.getContext().getFuture();
	SystemAuthoritys au = auTask.get();
	if(au == null || null == au.getId()) {
	    return Mono.just(JsonResult.sendFailedResult("权限不存在", null)); 
	}
	roleService.addAuthority2Role(role.getId(), au);
	Future<Integer> saveResultTask = RpcContext.getContext().getFuture();
	int saveResult = saveResultTask.get();
	if(saveResult > 0) {
	    return Mono.just(JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, "添加成功")); 
	} else {
	    return Mono.just(JsonResult.sendFailedResult("添加失败", null)); 
	}
    }
    
    @ApiOperation(value = "向用户中添加角色", notes = "向用户中添加角色", httpMethod = "POST", produces = "application/json")
    @PostMapping("/addUserRole")
    @UseValidator
    @AuthorityRegister(catlogName = "权限管理", catlogShowIndex = 99,
	    authorityName = "向用户中添加角色", authorityType = AuthorityTypeEnum.URL,
    authorityShowIndex = 0)
    public Mono<JsonResult> addUserRole(AddUserRoleRequest req) throws Exception {
	userService.findById(req.getUserId());
	Future<AlitaUserAccount> userTask = RpcContext.getContext().getFuture();
	AlitaUserAccount user = userTask.get();
	if(user == null || null == user.getId()) {
	    return Mono.just(JsonResult.sendFailedResult("用户不存在", null)); 
	}
	roleService.findById(req.getRoleId());
	Future<SystemRole> roleTask = RpcContext.getContext().getFuture();
	SystemRole role = roleTask.get();
	if(role == null || null == role.getId()) {
	    return Mono.just(JsonResult.sendFailedResult("角色不存在", null)); 
	}
	userService.addRole2User(user.getId(), role);
	Future<Integer> saveResultTask = RpcContext.getContext().getFuture();
	int saveResult = saveResultTask.get();
	if(saveResult > 0) {
	    return Mono.just(JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, "添加成功")); 
	} else {
	    return Mono.just(JsonResult.sendFailedResult("添加失败", null)); 
	}
    }
    
}
