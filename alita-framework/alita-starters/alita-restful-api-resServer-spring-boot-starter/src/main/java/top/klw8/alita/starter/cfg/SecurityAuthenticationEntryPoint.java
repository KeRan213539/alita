package top.klw8.alita.starter.cfg;

import java.nio.charset.Charset;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson.JSON;

import reactor.core.publisher.Mono;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.result.code.CommonResultCodeEnum;


/**
 * @author klw
 * @ClassName: SecurityAuthenticationEntryPoint
 * @Description: 缺少 token 的异常返回消息转换器
 * @date 2018年12月7日 下午2:46:55
 */
public class SecurityAuthenticationEntryPoint implements ServerAuthenticationEntryPoint, ServerAccessDeniedHandler {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange,
                               AuthenticationException authException) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return sendJsonStr(exchange.getResponse(), JSON.toJSONString(JsonResult.sendFailedResult(CommonResultCodeEnum.NO_TOKEN)));
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return sendJsonStr(exchange.getResponse(), JSON.toJSONString(JsonResult.sendFailedResult(CommonResultCodeEnum.NO_PRIVILEGES)));
    }

    private Mono<Void> sendJsonStr(ServerHttpResponse response, String str) {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        response.getHeaders().set("Cache-Control", "no-cache");
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        DataBuffer buffer = dataBufferFactory.wrap(str.getBytes(
                Charset.defaultCharset()));
        return response.writeWith(Mono.just(buffer))
                .doOnError(error -> DataBufferUtils.release(buffer));
    }

}
