package top.klw8.alita.starter.web.interceptor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.result.code.CommonResultCodeEnum;
import top.klw8.alita.starter.cfg.TokenConfigBean;
import top.klw8.alita.starter.utils.ResServerTokenUtil;
import top.klw8.alita.starter.web.base.BaseWebFilter;
import top.klw8.alita.utils.redis.TokenRedisUtil;

/**
 * token验证拦截器.
 *
 * @author klw(213539 @ qq.com)
 * @ClassName: TokenCheckInterceptor
 * @date 2020/9/10 15:35
 */
@Slf4j
@Order(1)
public class TokenCheckInterceptor extends BaseWebFilter {
    
    @javax.annotation.Resource
    private TokenConfigBean tokenConfigBean;
    
    private AntPathMatcher pathMatcher = new AntPathMatcher();
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if(tokenConfigBean.isStoreInRedis()){
            // 如果token放redis中, 则验证token在redis中是否存在
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            String reqPath = request.getURI().getPath();
            // 判断请求的路径是否是排除的,是就直接过
            for (String excludePath : tokenConfigBean.getCheckExcludePaths()) {
                if (pathMatcher.match(excludePath, reqPath)) {
                    return chain.filter(exchange);
                }
            }
            
            // 不是排除的,继续
            String requestToken = ResServerTokenUtil.getToken(request);
            if(StringUtils.isBlank(requestToken)){
                return sendJsonStr(response, JSON.toJSONString(JsonResult.failed(CommonResultCodeEnum.TOKEN_ERR)));
            }
            String userId = ResServerTokenUtil.getUserId(requestToken);
            if(StringUtils.isBlank(userId)){
                return sendJsonStr(response, JSON.toJSONString(JsonResult.failed(CommonResultCodeEnum.TOKEN_ERR)));
            }
            String[] appTagAndChannelTag = ResServerTokenUtil.getAppTagAndChannelTag(requestToken);
            if(ArrayUtils.isEmpty(appTagAndChannelTag)){
                return sendJsonStr(response, JSON.toJSONString(JsonResult.failed(CommonResultCodeEnum.TOKEN_ERR)));
            }
            String cachedToken = TokenRedisUtil.getAccessToken(userId, appTagAndChannelTag[0], appTagAndChannelTag[1]);
            if(StringUtils.isBlank(cachedToken) || !ResServerTokenUtil.removeTokenType(requestToken).equals(cachedToken)){
                return sendJsonStr(response, JSON.toJSONString(JsonResult.failed(CommonResultCodeEnum.TOKEN_ERR)));
            }
        }
        return chain.filter(exchange);
    }
}
