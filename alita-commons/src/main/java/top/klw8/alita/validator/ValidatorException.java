package top.klw8.alita.validator;


/**
 * @ClassName: ValidatorException
 * @Description: 表单验证,业务异常
 * @author klw
 * @date 2018年9月17日 13:07:33
 */
public class ValidatorException extends RuntimeException {

    private static final long serialVersionUID = 5968495544349839830L;
    
    private String statusCode;
    
    private String errorMsg;
    
    public ValidatorException(String statusCode, String errorMsg) {
	this.statusCode = statusCode;
	this.errorMsg = errorMsg;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
