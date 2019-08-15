package top.klw8.alita.common.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import reactor.core.publisher.Mono;
import top.klw8.alita.base.springctx.SpringApplicationContextUtil;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;
import top.klw8.alita.service.api.authority.IDevHelperProvider;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.starter.annotations.AuthorityCatlogRegister;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.utils.UUIDUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
    private IAuthorityAdminProvider authorityAdminProvider;

    @Reference(async = true)
    private IDevHelperProvider devHelperProvider;

    @ApiOperation(value = "自动扫描并注册所有权限", notes = "自动扫描并注册所有权限", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isAdd2SuperAdmin", value = "是否自动添加到超级管理员角色和帐号中", dataType = "boolean",
                    paramType = "query", example = "true", defaultValue = "true", required = true)
    })
    @PostMapping("/registeAllAuthority")
    public Mono<JsonResult> registeAllAuthority(boolean isAdd2SuperAdmin) {
        RequestMappingHandlerMapping reqMapping = SpringApplicationContextUtil.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> methodMap = reqMapping.getHandlerMethods();
        Map<String, SystemAuthoritysCatlog> tempMap = new HashMap<>();
        for (Entry<RequestMappingInfo, HandlerMethod> methodEntry : methodMap.entrySet()) {
            HandlerMethod v = methodEntry.getValue();
            Method method = v.getMethod();
            String authorityActionPrefix;
            String authorityAction;
            SystemAuthoritysCatlog catlog = null;
            String moduleName = "";

            // 获取方法所在的控制器类,拿到类注解 RequestMapping, 并从 RequestMapping 中获取url前缀
            Class<?> controllerClass = v.getBeanType();
            // 找 AuthorityCatlogRegister
            if (controllerClass.isAnnotationPresent(AuthorityCatlogRegister.class)) {
                AuthorityCatlogRegister catlogRegister = controllerClass.getAnnotation(AuthorityCatlogRegister.class);
                catlog = tempMap.get(catlogRegister.name());
                if(catlog == null){
                    catlog = new SystemAuthoritysCatlog();
                    catlog.setCatlogName(catlogRegister.name());
                    catlog.setShowIndex(catlogRegister.showIndex());
                    catlog.setRemark(catlogRegister.remark());
                    catlog.setAuthorityList(new ArrayList<>(16));
                    tempMap.put(catlogRegister.name(), catlog);
                }
                moduleName = "【" + catlog.getCatlogName() + "】";
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
                return Mono.just(JsonResult.sendFailedResult(method.getDeclaringClass().getName() + "." + method.getName() + "() 没有 mapping 注解"));
            }
            if (method.isAnnotationPresent(AuthorityRegister.class)) {
                AuthorityRegister register = method.getAnnotation(AuthorityRegister.class);
                // 先检查 catlog 是否存在
                if (catlog == null) {
                    // 如果没有 AuthorityCatlogRegister 注解,那么这里 catlog 就是 null
                    if (StringUtils.isEmpty(register.catlogName()) || register.catlogShowIndex() < 0) {
                        // 如果权限注册注解里面没有这几个属性, 直接略过这个权限
                        continue;
                    }
                    catlog = new SystemAuthoritysCatlog();
                    catlog.setCatlogName(register.catlogName());
                    catlog.setShowIndex(register.catlogShowIndex());
                    catlog.setRemark(register.catlogRemark());
                    catlog.setAuthorityList(new ArrayList<>(16));
                    tempMap.put(register.catlogName(), catlog);
                }
                SystemAuthoritys au = new SystemAuthoritys();
                au.setId(UUIDUtil.getRandomUUIDString());
                au.setAuthorityName(moduleName + register.authorityName());
                au.setAuthorityType(register.authorityType());
                au.setAuthorityAction(authorityAction);
                au.setShowIndex(register.authorityShowIndex());
                au.setRemark(register.authorityRemark());
                catlog.getAuthorityList().add(au);
            }
        }
        return Mono.fromFuture(devHelperProvider.batchAddAuthoritysAndCatlogs(new ArrayList<>(tempMap.values()), isAdd2SuperAdmin));
    }

    @ApiOperation(value = "刷新缓存中的管理员权限", notes = "刷新缓存中的管理员权限", httpMethod = "POST", produces = "application/json")
    @PostMapping("/refreshAdminAuthoritys")
    public Mono<JsonResult> refreshAdminAuthoritys() {
        return Mono.fromFuture(authorityAdminProvider.refreshAdminAuthoritys("5c85fc8b645d423b3c071ab7"));
    }

}
