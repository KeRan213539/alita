package top.klw8.alita.starter.web.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.UnpooledByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.result.code.CommonResultCodeEnum;
import top.klw8.alita.starter.cfg.ResServerAuthPathCfgBean;
import top.klw8.alita.starter.common.UserCacheHelper;
import top.klw8.alita.starter.datasecured.DataSecured;
import top.klw8.alita.starter.datasecured.DataSecuredControllerMethodsCache;
import top.klw8.alita.starter.datasecured.ResourceParserData;
import top.klw8.alita.starter.web.base.SynchronossFormFieldPart;

/**
 * @author klw
 * @ClassName: AuthorityInterceptor
 * @Description: 权限拦截器
 * @date 2018年12月6日 下午2:48:28
 */
@Slf4j
public class AuthorityInterceptor implements WebFilter {

    private final static String MONO_DATA_KEY_CONTENT_TYPE = "contentType";

    private final static String MONO_DATA_KEY_RESOURCE_PARSER_DATA = "rpd";

    private final static String MONO_DATA_KEY_DATA_SECURED = "dataSecured";

    private final static String MONO_DATA_KEY_NEW_REQUEST = "newRequest";

    @Autowired
    private UserCacheHelper userCacheHelper;

    @Resource
    private ResServerAuthPathCfgBean cfgBean;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String reqPath = request.getURI().getPath();

        // 判断请求的路径是否需要验证权限
//        for (String authPath : cfgBean.getAuthPath()) {
//            if (pathMatcher.match(authPath, reqPath)) {
//
//                List<String> tokenList = request.getHeaders().get("Authorization");
//                if (tokenList == null || tokenList.isEmpty()) {
//                    return sendJsonStr(response, JSON.toJSONString(JsonResult.sendFailedResult(CommonResultCodeEnum.NO_TOKEN)));
//                }
//                String jwtToken = tokenList.get(0);
//                String userId = TokenUtil.getUserId(jwtToken);
//                if (userId == null) {
//                    return sendJsonStr(response, JSON.toJSONString(JsonResult.sendFailedResult(CommonResultCodeEnum.TOKEN_ERR)));
//                }
//                Map<String, String> authorityMap = userCacheHelper.getUserAuthority(userId);
//                if (authorityMap == null) {
//                    return sendJsonStr(response, JSON.toJSONString(JsonResult.sendFailedResult(CommonResultCodeEnum.LOGIN_TIMEOUT)));
//                }
//                if (StringUtils.isEmpty(authorityMap.get(reqPath))) {
//                    // 没有权限
//                    return sendJsonStr(response, JSON.toJSONString(JsonResult.sendFailedResult(CommonResultCodeEnum.NO_PRIVILEGES)));
//                }
//                break;
//            }
//        }



        DataSecured dataSecuredAnnotation = DataSecuredControllerMethodsCache.getMethod(request.getMethod(), reqPath);
        if(dataSecuredAnnotation == null){
            //没有数据权限注解,继续执行下一个拦截器
            System.out.println("====没有数据权限注解====" + reqPath);
            return chain.filter(exchange.mutate().request(request).build());
        }
        Map<String, Object> monoDatMapa = new HashMap<>();
        monoDatMapa.put(MONO_DATA_KEY_RESOURCE_PARSER_DATA, new ResourceParserData());
        // 纯get的时候没有contentType
        monoDatMapa.put(MONO_DATA_KEY_CONTENT_TYPE, request.getHeaders().getContentType());
        monoDatMapa.put(MONO_DATA_KEY_DATA_SECURED, dataSecuredAnnotation);
        return Mono.just(monoDatMapa).map(monoData -> {
            ResourceParserData rpd = (ResourceParserData) monoData.get(MONO_DATA_KEY_RESOURCE_PARSER_DATA);
            // 处理 query 参数(url里通过?xxx=xxx传入的参数)
            request.getQueryParams().forEach((k, v) -> rpd.putQueryPrarm(k, v));
            return monoData;
        }).zipWith(exchange.getFormData(), (monoData, valueMap) -> {
            MediaType contentType = (MediaType) monoData.get(MONO_DATA_KEY_CONTENT_TYPE);
            if(contentType != null && MediaType.APPLICATION_FORM_URLENCODED.equals(contentType)){
                // 只处理 application/x-www-form-urlencoded
                ResourceParserData rpd = (ResourceParserData) monoData.get(MONO_DATA_KEY_RESOURCE_PARSER_DATA);
                valueMap.forEach((k, v) -> {
                    rpd.putQueryPrarm(k, (List<String>)(Object)v);
                });
            }
            return monoData;
        }).zipWith(exchange.getMultipartData(), (monoData, valueMap) -> {
            // 处理multipart/form-data 有文件上传的,只处理非文件的数据
            ResourceParserData rpd = (ResourceParserData) monoData.get(MONO_DATA_KEY_RESOURCE_PARSER_DATA);
            DataSecured dataSecured = (DataSecured) monoData.get(MONO_DATA_KEY_DATA_SECURED);
            MediaType contentType = (MediaType) monoData.get(MONO_DATA_KEY_CONTENT_TYPE);
            if(contentType != null && dataSecured.fileUpload()){
                // 处理 ====contentType====multipart/form-data 有文件上传的
                System.out.println("处理 ====contentType====multipart/form-data 有文件上传的");
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
                                // TODO 处理 form-data 里的数据, 下面这行添加挪到处理的方法里
                                formDataList.add(str);
                            });
                        } else {
                            // 文件数据不处理,直接塞回去
                            partList.add(part);
                        }
                    });
                    valueMap.put(k, partList);
                    rpd.putFormData(k, formDataList);
                });
            }
            return monoData;
        }).map(monoData -> {
            // 处理post数据,form-data(非文件上传,也就是非二进制方式),raw, binary(文件), graphQL 都能拿到东西
            ResourceParserData rpd = (ResourceParserData) monoData.get(MONO_DATA_KEY_RESOURCE_PARSER_DATA);
            DataSecured dataSecured = (DataSecured) monoData.get(MONO_DATA_KEY_DATA_SECURED);
            MediaType contentType = (MediaType) monoData.get(MONO_DATA_KEY_CONTENT_TYPE);
            if(contentType != null && !dataSecured.fileUpload() && !MediaType.APPLICATION_FORM_URLENCODED.equals(contentType)){
//                Flux<DataBuffer> bodyFlux = request.getBody().map(dataBuffer -> {
                request.getBody().map(dataBuffer -> {
                    //TODO binary(文件) 的时候如何判断是文件   ====contentType====image
                    // 只处理下面几种,其他不处理
                    // graphQL  application/json
                    // form-data:  multipart/form-data   研究如何解析, 这个的时候 contentType 里面有个边界(boundary)参数
                    // raw + json:  application/json
                    // raw + xml:  application/xml
                    System.out.println("============form-data(非文件上传,也就是非二进制方式),raw, binary(文件), graphQL 都能拿到东西==========================");
                    InputStream buffer = dataBuffer.asInputStream();
                    byte[] bytes = new byte[0];
                    try {
                        bytes = IOUtils.toByteArray(buffer);
                    } catch (IOException e) {
                        log.error("", e);
                    }
                    System.out.println("==getBody==" + new String(bytes));
                    List<String> testList = new ArrayList<>(1);
                    testList.add(new String(bytes));
                    rpd.putFormData("test", testList);
                    NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
                    DataBufferUtils.release(dataBuffer);
                    return nettyDataBufferFactory.wrap(bytes);
                }).collectList().subscribe(list -> {
                    ServerHttpRequest newRequest = new ServerHttpRequestDecorator(request){
                        @Override
                        public Flux<DataBuffer> getBody() {
                            return Flux.fromIterable(list);
//                        return bodyFlux;
                        }
                    };
                    monoData.put(MONO_DATA_KEY_NEW_REQUEST, newRequest);
                });
            }
            return monoData;
        }).map( monoData -> {
            //TODO 调用资源解析器
            return monoData;
        }).flatMap(monoData -> {
//            if(true){
//                return sendJsonStr(response, JSON.toJSONString(JsonResult.sendFailedResult(CommonResultCodeEnum.NO_PRIVILEGES)));
//            }
            //继续执行下一个拦截器
            ServerHttpRequest newRequest = (ServerHttpRequest) monoData.get(MONO_DATA_KEY_NEW_REQUEST);
            if(newRequest == null){
                return chain.filter(exchange);
            } else {
                return chain.filter(exchange.mutate().request(newRequest).build());
            }
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

}
