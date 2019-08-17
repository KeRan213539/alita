package top.klw8.alita.service.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.service.result.code.CommonResultCodeEnum;


/**
 * @author klw
 * @ClassName: JsonResult
 * @Description: alita统一消息返回格式包装
 * @date 2018年12月7日 下午1:36:48
 */
@Getter
@Setter
@ToString
public class JsonResult<T> implements java.io.Serializable {

    /**
     * @author klw(213539@qq.com)
     * @Description: 响应状态码
     */
    private String code;

    /**
     * @author klw(213539@qq.com)
     * @Description: 响应消息
     */
    private String message;

    /**
     * @author klw(213539@qq.com)
     * @Description: 响应数据
     */
    private T data;

    public JsonResult(){}

    public JsonResult(ISubResultCode subResultCode){
        this.code = subResultCode.getCode();
        this.message = subResultCode.getCodeMsg();
    }

    public JsonResult(ISubResultCode subResultCode, T data){
        this.code = subResultCode.getCode();
        this.message = subResultCode.getCodeMsg();
        this.data = data;
    }

    public JsonResult(ISubResultCode subResultCode, String message, T data){
        this.code = subResultCode.getCode();
        this.message = message;
        this.data = data;
    }

    public static JsonResult sendSuccessfulResult() {
        return new JsonResult(CommonResultCodeEnum.OK, null);
    }

    public static <T> JsonResult sendSuccessfulResult(T data) {
        return new JsonResult(CommonResultCodeEnum.OK, data);
    }

    public static <T>  JsonResult sendSuccessfulResult(String message) {
        return new JsonResult(CommonResultCodeEnum.OK, message, null);
    }

    public static <T>  JsonResult sendSuccessfulResult(String message, T data) {
        return new JsonResult(CommonResultCodeEnum.OK, message, data);
    }

    public static <T> JsonResult sendFailedResult(ISubResultCode subResultCode, T data) {
        return new JsonResult(subResultCode, data);
    }

    public static JsonResult sendFailedResult(ISubResultCode subResultCode) {
        return new JsonResult(subResultCode);
    }

    public static JsonResult sendFailedResult(ISubResultCode subResultCode, String errorMsg) {
        return new JsonResult(subResultCode, errorMsg, null);
    }

    public static JsonResult sendFailedResult(String errorMsg) {
        return new JsonResult(CommonResultCodeEnum.ERROR, errorMsg, null);
    }

}
