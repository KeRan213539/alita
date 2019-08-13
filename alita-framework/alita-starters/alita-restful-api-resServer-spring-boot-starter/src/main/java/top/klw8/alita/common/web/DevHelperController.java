package top.klw8.alita.common.web;


import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Mono;
import top.klw8.alita.base.springctx.SpringApplicationContextUtil;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.authority.ISystemAuthoritysCatlogService;
import top.klw8.alita.service.api.authority.ISystemAuthoritysService;
import top.klw8.alita.service.api.authority.ISystemRoleService;
import top.klw8.alita.service.api.user.IAlitaUserService;
import top.klw8.alita.service.utils.EntityUtil;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.common.UserCacheHelper;
import top.klw8.alita.starter.web.base.WebapiBaseController;
import top.klw8.alita.starter.web.common.JsonResult;
import top.klw8.alita.utils.UUIDUtil;

/**
 * @author klw
 * @ClassName: DevHelperController
 * @Description: 开发辅助工具
 * @date 2018年12月7日 下午3:55:04
 */
@Api(tags = {"alita-restful-API--开发辅助工具"})
@RestController
@RequestMapping("/devHelper")
@Profile("dev")
public class DevHelperController {

    @Reference(async = true)
    private IAlitaUserService userService;

    @Reference(async = true)
    private ISystemAuthoritysService auService;

    @Reference(async = true)
    private ISystemAuthoritysCatlogService catlogService;

    @Reference(async = true)
    private ISystemRoleService roleService;

    @Autowired
    private UserCacheHelper userCacheHelper;

    @ApiOperation(value = "自动扫描并注册所有权限", notes = "自动扫描并注册所有权限", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isAdd2SuperAdmin", value = "是否自动添加到超级管理员角色和帐号中", dataType = "boolean",
                    paramType = "query", example = "true", defaultValue = "true", required = true)
    })
    @PostMapping("/registeAllAuthority")
    public JsonResult registeAllAuthority(boolean isAdd2SuperAdmin) throws Exception {

        // 被略过的控制器方法
        List<String> ignoredAuList = new ArrayList<>();

        RequestMappingHandlerMapping reqMapping = SpringApplicationContextUtil.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> methodMap = reqMapping.getHandlerMethods();
        for (Entry<RequestMappingInfo, HandlerMethod> methodEntry : methodMap.entrySet()) {
            HandlerMethod v = methodEntry.getValue();
            Method method = v.getMethod();
            String authorityActionPrefix = null;
            String authorityAction = null;
            String moduleName = "";
            SystemAuthoritysCatlog catlog = null;

            // 获取方法所在的控制器类,拿到类注解 RequestMapping, 并从 RequestMapping 中获取url前缀
            Class<?> controllerClass = v.getBeanType();
            Class<?> superClass = controllerClass.getSuperclass();
//            if (superClass.getName().equals(WebapiCrudBaseController.class.getName())) {
//                // 如果是 WebapiCRUDBaseController 的子类,找 AuthorityCatlogRegister
//                if (controllerClass.isAnnotationPresent(AuthorityCatlogRegister.class)) {
//                    AuthorityCatlogRegister catlogRegister = controllerClass.getAnnotation(AuthorityCatlogRegister.class);
//                    catlogService.findByCatlogName(catlogRegister.name());
//                    Future<SystemAuthoritysCatlog> catlogTask = RpcContext.getContext().getFuture();
//                    catlog = catlogTask.get();
//                    if (catlog == null) {
//                        catlog = new SystemAuthoritysCatlog();
//                        catlog.setCatlogName(catlogRegister.name());
//                        catlog.setShowIndex(catlogRegister.showIndex());
//                        catlog.setRemark(catlogRegister.remark());
//                        catlogService.save(catlog);
//                        Future<SystemAuthoritysCatlog> catlogSaveTask = RpcContext.getContext().getFuture();
//                        catlogSaveTask.get();
//                    }
//                    moduleName = "【" + catlog.getCatlogName() + "】";
//                }
//            } else if (!superClass.getName().equals(WebapiBaseController.class.getName())) {
//                // 既不是 WebapiCRUDBaseController 的子类也不是 WebapiBaseController 的子类,说明不是业务相关(权限相关)的,直接忽略
//                continue;
//            }
            if (!superClass.getName().equals(WebapiBaseController.class.getName())) {
                // 既不是 WebapiCRUDBaseController 的子类也不是 WebapiBaseController 的子类,说明不是业务相关(权限相关)的,直接忽略
                continue;
            }
            if (controllerClass.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping mapping = controllerClass.getAnnotation(RequestMapping.class);
                authorityActionPrefix = mapping.value()[0];
            } else {
                authorityActionPrefix = "";
            }

            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping mapping = method.getAnnotation(RequestMapping.class);
                authorityAction = authorityActionPrefix + mapping.value()[0];
            } else if (method.isAnnotationPresent(PostMapping.class)) {
                PostMapping mapping = method.getAnnotation(PostMapping.class);
                authorityAction = authorityActionPrefix + mapping.value()[0];
            } else if (method.isAnnotationPresent(GetMapping.class)) {
                GetMapping mapping = method.getAnnotation(GetMapping.class);
                authorityAction = authorityActionPrefix + mapping.value()[0];
            } else if (method.isAnnotationPresent(PutMapping.class)) {
                PutMapping mapping = method.getAnnotation(PutMapping.class);
                authorityAction = authorityActionPrefix + mapping.value()[0];
            } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                DeleteMapping mapping = method.getAnnotation(DeleteMapping.class);
                authorityAction = authorityActionPrefix + mapping.value()[0];
            } else {
                // 不可能走到这里, RequestMappingHandlerMapping.getHandlerMethods()拿到的都是有mapping注解的方法
                return JsonResult.sendFailedResult(method.getDeclaringClass().getName() + "." + method.getName() + "() 没有 mapping 注解", null);
            }
            if (method.isAnnotationPresent(AuthorityRegister.class)) {
                AuthorityRegister register = method.getAnnotation(AuthorityRegister.class);
                CompletableFuture<SystemAuthoritys> auTask = auService.findByAuAction(authorityAction);
                SystemAuthoritys au = auTask.get();
                if (au != null) {
                    // 权限已存在,略过
                    continue;
                }
                // 先检查 catlog 是否存在
                if (catlog == null) {
                    // 如果没有 AuthorityCatlogRegister 注解,那么这里 catlog 就是 null
                    if (StringUtils.isEmpty(register.catlogName()) || register.catlogShowIndex() < 0) {
                        // 如果权限注册注解里面没有这几个属性, 直接略过这个权限
                        ignoredAuList.add(method.getDeclaringClass().getName() + "." + method.getName() + "()");
                        continue;
                    }
                    CompletableFuture<SystemAuthoritysCatlog> catlogTask = catlogService.findByCatlogName(register.catlogName());
                    catlog = catlogTask.get();
                    if (catlog == null) {
                        catlog = new SystemAuthoritysCatlog();
                        catlog.setCatlogName(register.catlogName());
                        catlog.setShowIndex(register.catlogShowIndex());
                        catlog.setRemark(register.catlogRemark());
                        CompletableFuture<Boolean> auSaveTask = catlogService.save(catlog);
                        auSaveTask.get();
                    }
                }
                au = new SystemAuthoritys();
                au.setId(UUIDUtil.getRandomUUIDString());
                au.setAuthorityName(moduleName + register.authorityName());
                au.setAuthorityType(register.authorityType());
                au.setAuthorityAction(authorityAction);
                au.setShowIndex(register.authorityShowIndex());
                au.setRemark(register.authorityRemark());
                au.setCatlog(catlog);
                Future<Boolean> auSaveTask = auService.save(au);
                auSaveTask.get();

                // 添加到超级管理员角色和用户中
                if (isAdd2SuperAdmin) {
                    CompletableFuture<SystemRole> future =  roleService.getById("5c85fc8b645d423b3c071ab6");
                    SystemRole superAdminRole = future.get();
                    if (superAdminRole == null) {
                        superAdminRole = new SystemRole();
                        superAdminRole.setId("5c85fc8b645d423b3c071ab6");
                        superAdminRole.setRoleName("超级管理员");
                        superAdminRole.setRemark("超级管理员");
                        CompletableFuture<Boolean> roleSaveTask = roleService.save(superAdminRole);
                        roleSaveTask.get();
                    }

                    CompletableFuture<AlitaUserAccount> superAdminTask = userService.getById("5c85fc8b645d423b3c071ab7");
                    AlitaUserAccount superAdmin = superAdminTask.get();
                    if (superAdmin == null) {
                        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
                        superAdmin = new AlitaUserAccount("admin", pwdEncoder.encode("123456"));
                        superAdmin.setId("5c85fc8b645d423b3c071ab7");
                        superAdmin.setCreateDate(LocalDateTime.now());
                        CompletableFuture<Boolean> userSaveTask = userService.save(superAdmin);
                        userSaveTask.get();
                    }
                    // 这里有可能用户存在并有这个角色,下面这个操作不会重复添加
                    CompletableFuture<Integer> addRole2UserTask = userService.addRole2User(superAdmin.getId(), superAdminRole);
                    addRole2UserTask.get();

                    CompletableFuture<SystemAuthoritys> auTask2 = auService.findByAuAction(authorityAction);
                    au = auTask2.get();
                    if (au == null) {
                        // 权限不存在,略过
                        continue;
                    }
                    CompletableFuture<Integer> addAuthority2RoleTask = roleService.addAuthority2Role(superAdminRole.getId(), au);
                    addAuthority2RoleTask.get();
                }
            }
        }

        if (ignoredAuList.isEmpty()) {
            return JsonResult.sendSuccessfulResult("注册完成", null);
        } else {
            return JsonResult.sendSuccessfulResult("注册完成,但有一些控制器方法被忽略,见返回数据", ignoredAuList);
        }
    }

    @ApiOperation(value = "刷新缓存中的管理员权限", notes = "刷新缓存中的管理员权限", httpMethod = "POST", produces = "application/json")
    @PostMapping("/refreshAdminAuthoritys")
    public Mono<JsonResult> refreshAdminAuthoritys() throws Exception {
        //查询管理员用户,把权限查出来
        // TODO 下面这里需要把角色和权限都关联查询出来
        CompletableFuture<AlitaUserAccount> superAdminTask = userService.getById("5c85fc8b645d423b3c071ab7");
        return Mono.just(superAdminTask.get()).map(superAdmin -> {
            if (EntityUtil.isEntityEmpty(superAdmin)) {
                return JsonResult.sendFailedResult("管理员用户不存在", null);
            }
            userCacheHelper.putUserInfo2Cache(superAdmin);
            return JsonResult.sendSuccessfulResult("刷新完成", null);
        });

    }

}
