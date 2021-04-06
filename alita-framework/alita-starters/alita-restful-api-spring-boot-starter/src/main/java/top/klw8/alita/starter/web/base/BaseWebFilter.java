package top.klw8.alita.starter.web.base;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * 拦截器基类.
 *
 * @author klw(213539 @ qq.com)
 * @ClassName: BaseWebFilter
 * @date 2020/9/10 15:47
 */
public abstract class BaseWebFilter implements WebFilter {
    
    protected Mono<Void> sendJsonStr(ServerHttpResponse response, String str) {
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
