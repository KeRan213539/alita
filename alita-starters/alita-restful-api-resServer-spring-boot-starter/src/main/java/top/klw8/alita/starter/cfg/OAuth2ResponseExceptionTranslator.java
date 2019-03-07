package top.klw8.alita.starter.cfg;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

import top.klw8.alita.starter.web.common.JsonResult;
import top.klw8.alita.starter.web.common.enums.ResultCodeEnum;
import top.klw8.alita.starter.web.common.enums.ResultStatusEnum;


/**
 * @ClassName: OAuth2ResponseExceptionTranslator
 * @Description: token返回消息转换器
 * @author klw
 * @date 2018年12月7日 下午2:34:34
 */
public class OAuth2ResponseExceptionTranslator implements WebResponseExceptionTranslator<JsonResult> {

    @Override
    public ResponseEntity<JsonResult> translate(Exception e) {
	Throwable throwable = e.getCause();
	if (throwable instanceof InvalidTokenException) {
	    return new ResponseEntity<JsonResult>(JsonResult.sendResult(ResultStatusEnum.FAILED, ResultCodeEnum._250, "token 失效", null), HttpStatus.UNAUTHORIZED);
	}
	return new ResponseEntity<JsonResult>(JsonResult.sendResult(ResultStatusEnum.FAILED, ResultCodeEnum._500, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
