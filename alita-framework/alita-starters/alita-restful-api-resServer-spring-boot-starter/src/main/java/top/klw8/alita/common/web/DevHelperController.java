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
package top.klw8.alita.common.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import org.apache.dubbo.config.annotation.DubboReference;
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
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import reactor.core.publisher.Mono;
import top.klw8.alita.entitys.authority.AlitaAuthoritysMenu;
import top.klw8.alita.entitys.authority.AlitaAuthoritysCatlog;
import top.klw8.alita.entitys.authority.AlitaAuthoritysResource;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;
import top.klw8.alita.service.api.authority.IDevHelperProvider;
import top.klw8.alita.service.result.IResultCode;
import top.klw8.alita.service.result.ISubResultCode;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.result.SubResultCode;
import top.klw8.alita.service.result.code.ResultCodeEnum;
import top.klw8.alita.starter.annotations.AuthorityCatlogRegister;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.annotations.PublicDataSecuredRegister;
import top.klw8.alita.starter.auscan.IAuthoritysResourceSourceItem;
import top.klw8.alita.starter.auscan.IAuthoritysResourceSource;
import top.klw8.alita.starter.cfg.AuthorityAppInfoInConfigBean;
import top.klw8.alita.utils.AuthorityUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

/**
 * 开发辅助工具
 * 2018年12月7日 下午3:55:04
 */
@Api(tags = {"alita-restful-API--开发辅助工具"})
@RestController
@RequestMapping("/devHelper")
@Slf4j
@Profile("dev")
public class DevHelperController {

    @DubboReference(async = true)
    private IAuthorityAdminProvider authorityAdminProvider;

    @DubboReference(async = true)
    private IDevHelperProvider devHelperProvider;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment env;

    @Autowired
    private AuthorityAppInfoInConfigBean currectApp;

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
        Map<String, AlitaAuthoritysCatlog> tempMap = new HashMap<>();
        for (Entry<RequestMappingInfo, HandlerMethod> methodEntry : methodMap.entrySet()) {
            HandlerMethod v = methodEntry.getValue();
            Method method = v.getMethod();
            String authorityAction;
            AlitaAuthoritysCatlog catlog = null;
            String moduleName = "";

            if (method.isAnnotationPresent(AuthorityRegister.class)) {
                // 获取方法所在的控制器类,拿到类注解 RequestMapping, 并从 RequestMapping 中获取url前缀
                Class<?> controllerClass = v.getBeanType();
                // 找 AuthorityCatlogRegister
                if (controllerClass.isAnnotationPresent(AuthorityCatlogRegister.class)) {
                    AuthorityCatlogRegister catlogRegister = controllerClass.getAnnotation(AuthorityCatlogRegister.class);
                    if(StringUtils.isBlank(catlogRegister.name())){
                        return Mono.just(JsonResult.failed(controllerClass.getName() + "的 AuthorityCatlogRegister 中没有设置catlog名称,注册失败"));
                    }
                    catlog = tempMap.get(catlogRegister.name());
                    if (catlog == null) {
                        catlog = new AlitaAuthoritysCatlog();
                        catlog.setCatlogName(catlogRegister.name());
                        catlog.setShowIndex(catlogRegister.showIndex());
                        catlog.setRemark(catlogRegister.remark());
                        catlog.setAuthorityList(new ArrayList<>(16));
                        tempMap.put(catlogRegister.name(), catlog);
                    }
                    moduleName = "【" + catlog.getCatlogName() + "】";
                }
                authorityAction = env.resolvePlaceholders(AuthorityUtil.getCompleteMappingUrl(v));

                AuthorityRegister register = method.getAnnotation(AuthorityRegister.class);

                // 如果AuthorityRegister中有catlog信息,就用AuthorityRegister中的
                if (StringUtils.isNotBlank(register.catlogName()) && register.catlogShowIndex() > 0) {
                    // 先从缓存中拿
                    catlog = tempMap.get(register.catlogName());
                    if(catlog == null) {
                        // 缓存中没有,新增
                        catlog = new AlitaAuthoritysCatlog();
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
                    return Mono.just(JsonResult.failed(controllerClass.getName() + "." + method.getName()
                            + "  没有 AuthorityCatlogRegister 注解,并且 AuthorityRegister 中也没有 catlog 信息,注册失败"));
                }
                AlitaAuthoritysMenu au = new AlitaAuthoritysMenu();
                au.setAuthorityName(register.authorityName());
                au.setAuthorityType(register.authorityType());
                au.setAuthorityAction(authorityAction);
                au.setShowIndex(register.authorityShowIndex());
                au.setRemark(moduleName + (StringUtils.isBlank(register.authorityRemark()) ? register.authorityName() : register.authorityRemark()));


                List<AlitaAuthoritysResource> dataSecuredList = new LinkedList<>();
                // 解析数据权限枚举源
                if(register.dataSecuredSourceEnum() != IAuthoritysResourceSourceItem.class){
                    if(Enum.class.isAssignableFrom(register.dataSecuredSourceEnum())){
                        Object[] enumConstants = register.dataSecuredSourceEnum().getEnumConstants();
                        for (Object obj : enumConstants){
                            IAuthoritysResourceSourceItem enumItem = (IAuthoritysResourceSourceItem)obj;
                            AlitaAuthoritysResource sds = new AlitaAuthoritysResource();
                            sds.setResource(enumItem.getResource());
                            sds.setRemark(enumItem.getRemark());
                            dataSecuredList.add(sds);
                        }
                    } else {
                        log.warn("【{}】中配制的数据权限源【{}#dataSecuredSourceEnum()】仅支持枚举类型,将忽略...", authorityAction, AuthorityRegister.class.getName());
                    }
                }

                // 解析数据权限源,有就入库,没有就继续
                if(register.dataSecuredSource() != IAuthoritysResourceSource.class){
                    // 有数据权限源
                    IAuthoritysResourceSource staticSource = applicationContext.getBean(register.dataSecuredSource());
                    Assert.notNull(staticSource, "【" + authorityAction + "】没有找到数据权限静态数据源,数据源需要放入spring容器中,请检查");
                    List<IAuthoritysResourceSourceItem> itemList = staticSource.getDataSecuredSourceList();
                    if(CollectionUtils.isEmpty(itemList)){
                        log.warn("【" + authorityAction + "】中配制的数据权限静态数据源返回结果为空,请检查!");
                    } else {
                        for(IAuthoritysResourceSourceItem item : itemList){
                            AlitaAuthoritysResource sds = new AlitaAuthoritysResource();
                            sds.setResource(item.getResource());
                            sds.setRemark(item.getRemark());
                            dataSecuredList.add(sds);
                        }
                    }
                }

                if(!dataSecuredList.isEmpty()) {
                    au.setDataSecuredList(dataSecuredList);
                }

                catlog.getAuthorityList().add(au);
            }
        }

        // 处理全局数据权限
        List<AlitaAuthoritysResource> publicDataSecuredList = new ArrayList<>(16);
        Map<String, Object> publicDataSecureds = applicationContext.getBeansWithAnnotation(PublicDataSecuredRegister.class);
        if(null != publicDataSecureds && !publicDataSecureds.isEmpty()){
            publicDataSecureds.forEach((key, value) -> {
                if(!IAuthoritysResourceSource.class.isAssignableFrom(value.getClass())){
                    log.error("【{}】使用了 @PublicDataSecuredRegister 注解,但没实现 IDataSecuredSource 接口",value.getClass().getName());
                } else {
                    Class<?> vClass = value.getClass();
                    PublicDataSecuredRegister ann = vClass.getAnnotation(PublicDataSecuredRegister.class);
                    IAuthoritysResourceSource dsSource = (IAuthoritysResourceSource)value;
                    List<IAuthoritysResourceSourceItem> dsSourceList = dsSource.getDataSecuredSourceList();
                    if(CollectionUtils.isEmpty(dsSourceList)){
                        log.warn("【{}】中返回的全局数据权限数据为空,将没有任何全局数据权限入库",value.getClass().getName());
                    } else {
                        // 静态数据源,入库
                        for (IAuthoritysResourceSourceItem item : dsSourceList) {
                            AlitaAuthoritysResource sds = new AlitaAuthoritysResource();
                            sds.setResource(item.getResource());
                            sds.setRemark(item.getRemark());
                            publicDataSecuredList.add(sds);
                        }
                    }
                }
            });
        }

        return Mono.fromFuture(devHelperProvider.batchAddAuthoritysAndCatlogs(
                new ArrayList<>(tempMap.values()), publicDataSecuredList, isAdd2SuperAdmin,
                currectApp.getAppEntity()));
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
        return Mono.fromFuture(devHelperProvider.addAllAuthoritys2AdminRole(currectApp.getAppEntity()));
    }

}
