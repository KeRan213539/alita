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
package top.klw8.alita.fileupload.controllerAdvice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
