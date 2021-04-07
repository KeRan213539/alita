/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.starter.web.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.result.code.CommonResultCodeEnum;

/**
 * Controller统一异常处理
 */

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Mono<JsonResult> jsonErrorHandler(Throwable e) {
        log.error("请求处理发生异常", e);
        return Mono.just(JsonResult.failed(CommonResultCodeEnum.ERROR));
    }

}
