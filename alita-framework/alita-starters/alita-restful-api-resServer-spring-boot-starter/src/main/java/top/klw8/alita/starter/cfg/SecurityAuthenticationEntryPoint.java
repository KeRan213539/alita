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
import top.klw8.alita.starter.web.common.JsonResult;
import top.klw8.alita.starter.web.common.enums.ResultCodeEnum;
import top.klw8.alita.starter.web.common.enums.ResultStatusEnum;


/**
 * @ClassName: SecurityAuthenticationEntryPoint
 * @Description: 缺少 token 的异常返回消息转换器
 * @author klw
 * @date 2018年12月7日 下午2:46:55
 */
public class SecurityAuthenticationEntryPoint implements ServerAuthenticationEntryPoint, ServerAccessDeniedHandler {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange,
	    AuthenticationException authException) {
	return sendJsonStr(exchange.getResponse(), JSON.toJSONString(JsonResult.sendResult(ResultStatusEnum.FAILED, ResultCodeEnum._250, "缺少token", null, false)));
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
	return sendJsonStr(exchange.getResponse(), JSON.toJSONString(JsonResult.sendResult(ResultStatusEnum.FAILED, ResultCodeEnum._403, "访问被拒绝", null, false)));
	
    }
    
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
