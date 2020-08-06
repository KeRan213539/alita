package top.klw8.alita.starter.cfg;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.result.code.CommonResultCodeEnum;


/**
 * @author klw
 * @ClassName: OAuth2ResponseExceptionTranslator
 * @Description: token返回消息转换器
 * @date 2018年12月7日 下午2:34:34
 */
public class OAuth2ResponseExceptionTranslator implements WebResponseExceptionTranslator<JsonResult> {

    @Override
    public ResponseEntity<JsonResult> translate(Exception e) {
        Throwable throwable = e.getCause();
        if (throwable instanceof InvalidTokenException) {
            return new ResponseEntity<>(JsonResult.failed(CommonResultCodeEnum.TOKEN_ERR), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(JsonResult.failed(CommonResultCodeEnum.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
