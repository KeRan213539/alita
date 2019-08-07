package top.klw8.alita.fileupload.controllerAdvice;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @ClassName: ErrorHandlingControllerAdvice
 * @Description: 处理所有Controller异常
 * @author klw
 * @date 2018年3月23日 下午1:30:05
 */
//@ControllerAdvice(basePackages = "com.rrl.sdd.web.appApi.actions")
public class ErrorHandlingControllerAdvice {

    private static Logger logger = LoggerFactory.getLogger(ErrorHandlingControllerAdvice.class);
    
    /**
     * @Title: handleValidationError
     * @Description: 处理表单验证,业务异常
     * @param ex
     * @return
     */
//    @ExceptionHandler(SddValidatorException.class)
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public AppApiResponse<?> handleValidationError(SddValidatorException ex) {
//	String errMsg = null;
//	if(StringUtils.isBlank(ex.getErrorMsg())) {
//	    errMsg = ex.getStatusCode().getDefaultMessage();
//	} else {
//	    errMsg = ex.getErrorMsg();
//	}
//        return makeResponse(ex.getStatusCode(), errMsg);
//    }
//    
//    /**
//     * @Title: handleValidationError
//     * @Description: 处理其他异常
//     * @param ex
//     * @return
//     */
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public AppApiResponse<?> handleValidationError(Exception ex) {
//	logger.error("服务器内部错误:====", ex);
//        return makeResponse(ResponseStatusCodeEnum._500, "服务器内部错误:====" + ex.getMessage());
//    }
//    
//    private AppApiResponse<?> makeResponse(ResponseStatusCodeEnum statusCode, String statusMessage) {
//	AppApiResponse<Object> response = new AppApiResponse<>(new Object());
//	AppApiResponseHeader respHeader = new AppApiResponseHeader();
//        response.setHeader(respHeader);
//        respHeader.setStatusCode(statusCode);
//        respHeader.setStatusMessage(statusMessage);
//        return response;
//    }
    
}
