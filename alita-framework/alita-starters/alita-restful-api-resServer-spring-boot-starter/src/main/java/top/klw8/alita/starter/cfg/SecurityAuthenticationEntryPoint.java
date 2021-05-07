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
 * 缺少 token 的异常返回消息转换器
 * 2018年12月7日 下午2:46:55
 */
public class SecurityAuthenticationEntryPoint implements ServerAuthenticationEntryPoint, ServerAccessDeniedHandler {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange,
                               AuthenticationException authException) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return sendJsonStr(exchange.getResponse(), JSON.toJSONString(JsonResult.failed(CommonResultCodeEnum.NO_TOKEN)));
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return sendJsonStr(exchange.getResponse(), JSON.toJSONString(JsonResult.failed(CommonResultCodeEnum.NO_PRIVILEGES)));
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
