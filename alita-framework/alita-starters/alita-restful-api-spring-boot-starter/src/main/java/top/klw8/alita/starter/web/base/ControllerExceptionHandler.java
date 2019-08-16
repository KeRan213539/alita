package top.klw8.alita.starter.web.base;

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

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Mono<JsonResult> jsonErrorHandler(Throwable e) {
        return Mono.just(JsonResult.sendFailedResult(CommonResultCodeEnum.ERROR));
    }

}
