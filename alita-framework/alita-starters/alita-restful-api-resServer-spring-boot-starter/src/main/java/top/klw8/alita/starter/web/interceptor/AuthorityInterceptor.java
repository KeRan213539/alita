package top.klw8.alita.starter.web.interceptor;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.alibaba.fastjson.JSON;

import reactor.core.publisher.Mono;
import top.klw8.alita.starter.cfg.ResServerAuthPathCfgBean;
import top.klw8.alita.starter.common.UserCacheHelper;
import top.klw8.alita.starter.utils.TokenUtil;
import top.klw8.alita.starter.web.common.JsonResult;
import top.klw8.alita.starter.web.common.enums.ResultCodeEnum;
import top.klw8.alita.starter.web.common.enums.ResultStatusEnum;

/**
 * @ClassName: AuthorityInterceptor
 * @Description: 权限拦截器
 * @author klw
 * @date 2018年12月6日 下午2:48:28
 */
@Component
public class AuthorityInterceptor implements WebFilter {
    
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
        for(String authPath : cfgBean.getAuthPath()) {
            if(pathMatcher.match(authPath, reqPath)) {
        	
        	List<String> tokenList = request.getHeaders().get("Authorization");
        	if(tokenList == null || tokenList.isEmpty()) {
        	    return sendJsonStr(response, JSON.toJSONString(JsonResult.sendResult(ResultStatusEnum.FAILED, ResultCodeEnum._250, "需要token", null, false)));
        	}
        	String jwtToken = tokenList.get(0);
        	String userId = TokenUtil.getUserId(jwtToken);
        	if(userId == null) {
        	    return sendJsonStr(response, JSON.toJSONString(JsonResult.sendResult(ResultStatusEnum.FAILED, ResultCodeEnum._250, "token不正确", null, false)));
        	}
        	Map<String, String> authorityMap = userCacheHelper.getUserAuthority(userId);
        	if(authorityMap == null) {
        	    return sendJsonStr(response, JSON.toJSONString(JsonResult.sendResult(ResultStatusEnum.FAILED, ResultCodeEnum._250, "登录超时", null, false)));
        	}
        	if (StringUtils.isEmpty(authorityMap.get(reqPath))) {
        	    // 没有权限
        	    return sendJsonStr(response, JSON.toJSONString(JsonResult.sendResult(ResultStatusEnum.FAILED, ResultCodeEnum._403, "没有权限", null, false)));
        	}
        	break;
            }
        }
        //继续执行下一个拦截器
        return chain.filter(exchange);
	
    }
        
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//	Long userId = TokenUtil.getUserId();
//	
//	Map<String, String> authorityMap = userCacheHelper.getUserAuthority(userId);
//	if(authorityMap == null) {
//	    sendJsonStr(response, JSON.toJSONString(JsonResult.sendResult(ResultStatusEnum.FAILED, ResultCodeEnum._250, "登录超时", null, false)));
//	    return false;
//	}
//	
//	// 获取用户请求的域名及项目根路径
//	String scheme = request.getScheme();
//	String serverName = request.getServerName();
//	int port = request.getServerPort();
//	String path = request.getContextPath();
//	String basePath = "";
//	if (port == WebApiContext.WEB_PORT_HTTP || port == WebApiContext.WEB_PORT_HTTPS) {
//	    basePath = scheme + "://" + serverName + ":" + path + "/";
//	} else {
//	    basePath = scheme + "://" + serverName + ":" + port + path + "/";
//	}
//	
//	// 获取用户当前请求的路径
//	String accessingURL = "";
//	if (request.getRequestURL().toString().length() <= (basePath.length() + 1)) {
//	    accessingURL = basePath;
//	} else {
//	    accessingURL = request.getRequestURL().toString().substring(basePath.length() - 1);
//	}
//	
//	if (StringUtils.isEmpty(authorityMap.get(accessingURL))) {
//	    // 没有权限
//	    sendJsonStr(response, JSON.toJSONString(JsonResult.sendResult(ResultStatusEnum.FAILED, ResultCodeEnum._403, "没有权限", null, false)));
//	    return false;
//	}
//	
//	return true;
//    }
    
    private Mono<Void> sendJsonStr(ServerHttpResponse response, String str){
	response.setStatusCode(HttpStatus.BAD_REQUEST);
	response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
	response.getHeaders().set("Cache-Control", "no-cache");
	DataBufferFactory dataBufferFactory = response.bufferFactory();
	DataBuffer buffer = dataBufferFactory.wrap(str.getBytes(
		Charset.defaultCharset()));
	return response.writeWith(Mono.just(buffer))
		.doOnError( error -> DataBufferUtils.release(buffer));
    }
    
}
