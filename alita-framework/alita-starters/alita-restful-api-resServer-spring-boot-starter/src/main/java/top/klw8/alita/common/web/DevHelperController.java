package top.klw8.alita.common.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import reactor.core.publisher.Mono;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;
import top.klw8.alita.service.api.authority.IDevHelperProvider;
import top.klw8.alita.service.result.IResultCode;
import top.klw8.alita.service.result.ISubResultCode;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.result.SubResultCode;
import top.klw8.alita.service.result.code.ResultCodeEnum;
import top.klw8.alita.starter.annotations.AuthorityCatlogRegister;
import top.klw8.alita.starter.annotations.AuthorityRegister;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
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
@Slf4j
@Profile("dev")
public class DevHelperController {

    @Reference(async = true)
    private IAuthorityAdminProvider authorityAdminProvider;

    @Reference(async = true)
    private IDevHelperProvider devHelperProvider;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment env;

    @Value("${alita.devHelper.resultCodeClassPackage:}")
    private String resultCodeClassPackage;

    @ApiOperation(value = "自动扫描并注册所有权限", notes = "自动扫描并注册所有权限", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isAdd2SuperAdmin", value = "是否自动添加到超级管理员角色和帐号中", dataType = "boolean",
                    paramType = "query", example = "true", defaultValue = "true", required = true)
    })
    @PostMapping("/registeAllAuthority")
    public Mono<JsonResult> registeAllAuthority(boolean isAdd2SuperAdmin) {
        RequestMappingHandlerMapping reqMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> methodMap = reqMapping.getHandlerMethods();
        Map<String, SystemAuthoritysCatlog> tempMap = new HashMap<>();
        for (Entry<RequestMappingInfo, HandlerMethod> methodEntry : methodMap.entrySet()) {
            HandlerMethod v = methodEntry.getValue();
            Method method = v.getMethod();
            String authorityActionPrefix;
            String authorityAction;
            SystemAuthoritysCatlog catlog = null;
            String moduleName = "";

            if (method.isAnnotationPresent(AuthorityRegister.class)) {
                // 获取方法所在的控制器类,拿到类注解 RequestMapping, 并从 RequestMapping 中获取url前缀
                Class<?> controllerClass = v.getBeanType();
                // 找 AuthorityCatlogRegister
                if (controllerClass.isAnnotationPresent(AuthorityCatlogRegister.class)) {
                    AuthorityCatlogRegister catlogRegister = controllerClass.getAnnotation(AuthorityCatlogRegister.class);
                    if(StringUtils.isBlank(catlogRegister.name())){
                        return Mono.just(JsonResult.sendFailedResult(controllerClass.getName() + "的 AuthorityCatlogRegister 中没有设置catlog名称,注册失败"));
                    }
                    catlog = tempMap.get(catlogRegister.name());
                    if (catlog == null) {
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
                    authorityActionPrefix = env.resolvePlaceholders(mapping.value()[0]);
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
                authorityAction = env.resolvePlaceholders(authorityAction);

                AuthorityRegister register = method.getAnnotation(AuthorityRegister.class);

                // 如果AuthorityRegister中有catlog信息,就用AuthorityRegister中的
                if (StringUtils.isNotBlank(register.catlogName()) && register.catlogShowIndex() > 0) {
                    // 先从缓存中拿
                    catlog = tempMap.get(register.catlogName());
                    if(catlog == null) {
                        // 缓存中没有,新增
                        catlog = new SystemAuthoritysCatlog();
                        catlog.setCatlogName(register.catlogName());
                        catlog.setShowIndex(register.catlogShowIndex());
                        catlog.setRemark(register.catlogRemark());
                        catlog.setAuthorityList(new ArrayList<>(16));
                        tempMap.put(register.catlogName(), catlog);
                    }
                }
                // AuthorityRegister 中没有catlog信息, 取 AuthorityCatlogRegister 的
                if (catlog == null) {
                    // 因为之前处理过 AuthorityCatlogRegister 和 AuthorityRegister,这里catlog不应该为null
                    // 为null说明没有 AuthorityCatlogRegister 注解, AuthorityRegister 中也没有 catlog信息
                    return Mono.just(JsonResult.sendFailedResult(controllerClass.getName() + "." + method.getName()
                            + "  没有 AuthorityCatlogRegister 注解,并且 AuthorityRegister 中也没有 catlog 信息,注册失败"));
                }
                SystemAuthoritys au = new SystemAuthoritys();
                au.setAuthorityName(register.authorityName());
                au.setAuthorityType(register.authorityType());
                au.setAuthorityAction(authorityAction);
                au.setShowIndex(register.authorityShowIndex());
                au.setRemark(moduleName + (StringUtils.isBlank(register.authorityRemark()) ? register.authorityName() : register.authorityRemark()));
                catlog.getAuthorityList().add(au);
            }
        }
        return Mono.fromFuture(devHelperProvider.batchAddAuthoritysAndCatlogs(new ArrayList<>(tempMap.values()), isAdd2SuperAdmin));
    }

    @ApiOperation(value = "刷新缓存中的管理员权限", notes = "刷新缓存中的管理员权限", httpMethod = "POST", produces = "application/json")
    @PostMapping("/refreshAdminAuthoritys")
    public Mono<JsonResult> refreshAdminAuthoritys() {
        return Mono.fromFuture(authorityAdminProvider.refreshUserAuthoritys("d84c6b4ed9134d468e5a43d467036c46"));
    }

    @GetMapping("/statusCodeInfo")
    @ResponseBody
    public Mono<String> statusCodeInfo() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        MetadataReaderFactory metaReader = new CachingMetadataReaderFactory(resourceLoader);
        List<Resource> resourceList = new ArrayList<>(16);
        StringBuilder sbAll = new StringBuilder();
        try {
            Resource[] resources = resolver.getResources("classpath*:top/klw8/alita/service/result/code/*.class");
            Collections.addAll(resourceList, resources);
            if (StringUtils.isNotBlank(resultCodeClassPackage)) {
                String[] resultCodeClassPackages = resultCodeClassPackage.split(",");
                for (String pack : resultCodeClassPackages) {
                    Collections.addAll(resourceList, resolver.getResources("classpath*:" + pack.replace(".", "/") + "/*.class"));
                }
            }
            StringBuilder sb = new StringBuilder();
            StringBuilder sbFirst = new StringBuilder();
            for (Resource resource : resourceList) {
                MetadataReader reader = metaReader.getMetadataReader(resource);
                if (reader.getAnnotationMetadata().hasAnnotation(SubResultCode.class.getName())) {
                    Class<?> onwClass = null;
                    try {
                        onwClass = Class.forName(reader.getClassMetadata().getClassName());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    Method method = onwClass.getMethod("values");
                    ISubResultCode inter[] = (ISubResultCode[]) method.invoke(null);
                    boolean ifThisEnumFistRead = true;
                    for (ISubResultCode stateEnum : inter) {

                        IResultCode classify = stateEnum.getClassify();
                        if (classify.equals(ResultCodeEnum.COMMON)) {
                            if (ifThisEnumFistRead) {
                                ifThisEnumFistRead = false;
                                sbFirst.append("<tr><td colspan=\"2\" style=\"background-color: aliceblue;text-align: center;\">").append(classify.getCodeName()).append("</td></tr>");
                            }
                            sbFirst.append("<tr><td>").append(stateEnum.getCode()).append("</td><td>").append(stateEnum.getCodeMsg()).append("</td></tr>");
                        } else {
                            if (ifThisEnumFistRead) {
                                ifThisEnumFistRead = false;
                                sb.append("<tr><td colspan=\"2\" style=\"background-color: aliceblue;text-align: center;\">").append(classify.getCodeName()).append("</td></tr>");
                            }
                            sb.append("<tr><td>").append(stateEnum.getCode()).append("</td><td>").append(stateEnum.getCodeMsg()).append("</td></tr>");
                        }
                    }
                }
            }
            sbAll.append("<table><thead><tr><th style=\"font-size:20px;\"><strong>状态码</strong></th><th style=\"font-size:20px;\"><strong>说明</strong></th></tr></thead>");
            sbAll.append("<tbody>");
            sbAll.append(sbFirst.toString());
            sbAll.append(sb.toString());
            sbAll.append("</tbody>");
            sbAll.append("</table>");
        } catch (IOException e) {
            log.error("", e);
        }
        return Mono.just(sbAll.toString());

    }

    @ApiOperation(value = "添加所有权限到管理员角色和管理员账户,如果管理员角色或账户不存在则创建", notes = "添加所有权限" +
            "到管理员角色和管理员账户,如果管理员角色或账户不存在则创建", httpMethod = "POST", produces = "application/json")
    @PostMapping("/addAllAuthoritys2AdminRole")
    public Mono<JsonResult> addAllAuthoritys2AdminRole() {
        return Mono.fromFuture(devHelperProvider.addAllAuthoritys2AdminRole());
    }

}
