package top.klw8.alita.starter.web.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import io.netty.buffer.UnpooledByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.Part;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.result.code.CommonResultCodeEnum;
import top.klw8.alita.starter.cfg.ResServerAuthPathCfgBean;
import top.klw8.alita.starter.common.UserCacheHelper;
import top.klw8.alita.starter.common.WebApiContext;
import top.klw8.alita.starter.datasecured.*;
import top.klw8.alita.starter.utils.AuthorityUtil;
import top.klw8.alita.starter.utils.FormDataNoFileParserUtil;
import top.klw8.alita.starter.utils.TokenUtil;
import top.klw8.alita.starter.web.base.SynchronossFormFieldPart;

/**
 * @author klw
 * @ClassName: AuthorityInterceptor
 * @Description: 权限拦截器
 * @date 2018年12月6日 下午2:48:28
 */
@Slf4j
public class AuthorityInterceptor implements WebFilter {

    private final static String MONO_DATA_KEY_NEW_REQUEST = "newRequest";

    public static final List<MediaType> legalLogMediaTypes = Lists.newArrayList(
            MediaType.APPLICATION_XML,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_JSON_UTF8,
            MediaType.MULTIPART_FORM_DATA,
            MediaType.TEXT_XML);


    @Autowired
    private UserCacheHelper userCacheHelper;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RequestMappingHandlerMapping reqMapping;

    @Autowired
    private Environment env;

    @Resource
    private ResServerAuthPathCfgBean cfgBean;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String reqPath = request.getURI().getPath();
        String authorityAction = null;

        String userId = null;
        List<String> tokenList = request.getHeaders().get("Authorization");
        if (CollectionUtils.isNotEmpty(tokenList)) {
            String jwtToken = tokenList.get(0);
            userId = TokenUtil.getUserId(jwtToken);
        }

        // 判断请求的路径是否需要验证权限
        for (String authPath : cfgBean.getAuthPath()) {
            if (pathMatcher.match(authPath, reqPath)) {
                if (userId == null) {
                    return sendJsonStr(response, JSON.toJSONString(JsonResult.sendFailedResult(CommonResultCodeEnum.TOKEN_ERR)));
                }
                Map<String, String> authorityMap = userCacheHelper.getUserAuthority(userId);
                if (authorityMap == null) {
                    return sendJsonStr(response, JSON.toJSONString(JsonResult.sendFailedResult(CommonResultCodeEnum.LOGIN_TIMEOUT)));
                }
                // 通过反射获取class中定义的 mapping 用于匹配权限(库里存的是原始的mapping,如果使用了url参数,就无法匹配到权限,所以拿原始mapping来匹配)
                HandlerMethod handlerMethod = reqMapping.getHandlerInternal(exchange).block();
                if(handlerMethod == null){
                    // 没找到 handlerMethod 直接过,spring 会返回404
                    return chain.filter(exchange);
                }
                authorityAction = env.resolvePlaceholders(AuthorityUtil.getCompleteMappingUrl(handlerMethod));
                if (StringUtils.isBlank(authorityAction) || StringUtils.isEmpty(authorityMap.get(authorityAction))) {
                    // 没有权限
                    return sendJsonStr(response, JSON.toJSONString(JsonResult.sendFailedResult(CommonResultCodeEnum.NO_PRIVILEGES)));
                }
                break;
            }
        }


        // 判断是否需要验证数据权限
        DataSecured dataSecuredAnnotation = DataSecuredControllerMethodsCache.getMethod(request.getMethod(), authorityAction);
        if(dataSecuredAnnotation == null){
            //没有数据权限注解,继续执行下一个拦截器
            return chain.filter(exchange.mutate().request(request).build());
        }

        if (userId == null) {
            return sendJsonStr(response, JSON.toJSONString(JsonResult.sendFailedResult(CommonResultCodeEnum.TOKEN_ERR)));
        }

        // 需要验证数据权限
        Map<String, List<String>> dataSecuredMap = userCacheHelper.getUserDataSecured(userId);
        if (dataSecuredMap == null) {
            return sendJsonStr(response, JSON.toJSONString(JsonResult.sendFailedResult(CommonResultCodeEnum.LOGIN_TIMEOUT)));
        }

        // 判断是否需要走解析器,不需要的话直接验证数据权限
        if(dataSecuredAnnotation.parser() == DefaultResourceParser.class){
            // 解析器是默认的,说明没有配制解析器,拿资源标识
            String resTag = dataSecuredAnnotation.resource();
            if(StringUtils.isBlank(resTag)){
                //没有配制解析器,也没有配制资源标识
                return sendJsonStr(response, JSON.toJSONString(JsonResult.sendFailedResult(CommonResultCodeEnum.DATA_SECURED_NO_RES)));
            }
            if(checkDataSecured(reqPath, resTag, dataSecuredMap)){
                // 有数据权限,继续下一个拦截器
                return chain.filter(exchange);
            } else {
                // 没有数据权限
                return sendJsonStr(response, JSON.toJSONString(JsonResult.sendFailedResult(CommonResultCodeEnum.NO_PRIVILEGES)));
            }
        }

        ResourceParserData rpdata = new ResourceParserData(reqPath);
        // 处理 query 参数(url里通过?xxx=xxx传入的参数)
        request.getQueryParams().forEach((k, v) -> rpdata.putQueryPrarm(k, v));

        // 处理 url 路径参数
        Map<String, String> pathPrarms = pathMatcher.extractUriTemplateVariables(authorityAction, reqPath);
        if(MapUtils.isNotEmpty(pathPrarms)){
            rpdata.putAllPathPrarms(pathPrarms);
        }

        MediaType contentType = request.getHeaders().getContentType();
        Mono<ResourceParserData> result;
        Map<String, Object> monoDataMap = new HashMap<>();
        if(contentType != null){
            // 有 contentType, 说明有post数据
            if(MediaType.APPLICATION_FORM_URLENCODED.equals(contentType)){
                // 只处理 application/x-www-form-urlencoded
                result = exchange.getFormData().map(valueMap -> {
                    valueMap.forEach((k, v) -> {
                        rpdata.putQueryPrarm(k, v);
                    });
                    return rpdata;
                });
            } else {
                if(dataSecuredAnnotation.fileUpload()){
                    // 处理 ====contentType====multipart/form-data 有文件上传的
                    result = exchange.getMultipartData().map(valueMap -> {
                        valueMap.forEach((k, v) -> {
                            List<Part> partList = new ArrayList<>(v.size());
                            List<String> formDataList = new ArrayList<>(v.size());
                            v.forEach(part -> {
                                if("org.springframework.http.codec.multipart.SynchronossPartHttpMessageReader$SynchronossFormFieldPart".equals(part.getClass().getName())){
                                    // body里的东西读过就没了,这里读了以后后面的过滤器和controller就拿不到了,所以要塞回去
                                    part.content().subscribe(dataBuff -> {
                                        InputStream buff = dataBuff.asInputStream();
                                        String str = null;
                                        try {
                                            str = IOUtils.toString(buff, Charset.forName("utf-8"));
                                        } catch (IOException e) {
                                            log.error("", e);
                                        }
                                        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
                                        DataBufferUtils.release(dataBuff);
                                        partList.add(new SynchronossFormFieldPart(part.headers(), nettyDataBufferFactory, str));
                                        formDataList.add(str);
                                    });
                                } else {
                                    // 文件数据不处理,直接塞回去
                                    partList.add(part);
                                }
                            });
                            valueMap.put(k, partList);
                            if(!formDataList.isEmpty()){
                                rpdata.putFormData(k, formDataList);
                            }
                        });
                        return rpdata;
                    });
                } else {
                    // 处理post数据,form-data(非文件上传,也就是非二进制方式),raw, binary(文件), graphQL 都能拿到东西
                    result = request.getBody().map(dataBuffer -> {
                        // 只处理下面几种,其他不处理
                        // graphQL  application/json
                        // form-data:  multipart/form-data
                        // raw + json:  application/json
                        // raw + xml:  application/xml
                        InputStream buffer = dataBuffer.asInputStream();
                        byte[] bytes = new byte[0];
                        try {
                            bytes = IOUtils.toByteArray(buffer);
                        } catch (IOException e) {
                            log.error("", e);
                        }
                        if(legalLogMediaTypes.contains(contentType)){
                            String bodyData = new String(bytes);
                            // 要处理的类型
                            if(MediaType.APPLICATION_XML.equals(contentType) || MediaType.APPLICATION_XML.equals(contentType)){
                                // 处理XML
                                rpdata.setXmlString(bodyData);
                            } else if(MediaType.APPLICATION_JSON.equals(contentType) || MediaType.APPLICATION_JSON_UTF8.equals(contentType)){
                                // 处理 JSON
                                rpdata.setJsonString(bodyData);
                            } else {
                                // 处理 form-data
                                rpdata.putAllFormData(FormDataNoFileParserUtil.parser(bodyData, contentType.getParameter("boundary")));
                            }
                        }
                        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
                        DataBufferUtils.release(dataBuffer);
                        return nettyDataBufferFactory.wrap(bytes);
                    }).collectList().map(dataBufferList -> {
                        ServerHttpRequest newRequest = new ServerHttpRequestDecorator(request){
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return Flux.fromIterable(dataBufferList);
                            }
                        };
                        monoDataMap.put(MONO_DATA_KEY_NEW_REQUEST, newRequest);
                        return rpdata;
                    });
                }
            }
        } else {
            result = Mono.just(rpdata);
        }

        return result.map(rpd -> {
            // 调用资源解析器,并把解析器返回的资源给下一步
            IResourceParser resourceParser = applicationContext.getBean(dataSecuredAnnotation.parser());
            Assert.notNull(resourceParser, "【" + reqPath + "】没有找到数据权限资源解析器,解析器需要放入spring容器中,请检查");
            String[] parserResult = resourceParser.parseResource(rpd);
            if(parserResult == null || parserResult.length <= 0){
                parserResult = new String[0];
            }
            return parserResult;
        }).flatMap(parserResult -> {
            // 验证数据权限,并做相应处理
            if(parserResult.length > 0 && checkDataSecured(reqPath, parserResult, dataSecuredMap)){
                // 有数据权限,继续下一个拦截器
                ServerHttpRequest newRequest = (ServerHttpRequest) monoDataMap.get(MONO_DATA_KEY_NEW_REQUEST);
                if(newRequest == null){
                    return chain.filter(exchange);
                } else {
                    return chain.filter(exchange.mutate().request(newRequest).build());
                }
            }
            // 没有数据权限
            return sendJsonStr(response, JSON.toJSONString(JsonResult.sendFailedResult(CommonResultCodeEnum.NO_PRIVILEGES)));
        });
    }

    private Mono<Void> sendJsonStr(ServerHttpResponse response, String str) {
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        response.getHeaders().set("Cache-Control", "no-cache");
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        DataBuffer buffer = dataBufferFactory.wrap(str.getBytes(
                Charset.defaultCharset()));
        return response.writeWith(Mono.just(buffer))
                .doOnError(error -> DataBufferUtils.release(buffer));
    }

    private boolean checkDataSecured(String reqUrl, String resTag, Map<String, List<String>> dataSecuredMap){
        return checkDataSecured(reqUrl, new String[]{resTag}, dataSecuredMap);
    }

    private boolean checkDataSecured(String reqUrl, String[] resTags, Map<String, List<String>> dataSecuredMap){
        // 先在当前请求的权限下的数据权限中找
        List<String> sdList = dataSecuredMap.get(reqUrl);
        if(sdList != null){
            for(String resTag : resTags){
                if(sdList.contains(resTag)){
                    return Boolean.TRUE;
                }
            }
        }
        // 再找全局
        List<String> publicSDList = dataSecuredMap.get(WebApiContext.CACHE_KEY_USER_PUBLIC_DATA_SECUREDS);
        if(publicSDList != null){
            for(String resTag : resTags){
                if(publicSDList.contains(resTag)){
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

}
