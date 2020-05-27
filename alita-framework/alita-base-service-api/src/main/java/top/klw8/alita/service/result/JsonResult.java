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

    /**
     * @author klw(213539@qq.com)
     * @Description: 发送自定义code的和消息的Result
     * @Date 2019/10/15 17:28
     * @param: subResultCode
     * @param: errorMsg
     * @return top.klw8.alita.service.result.JsonResult
     */
    public static JsonResult sendFailedResult(ISubResultCode subResultCode, String errorMsg) {
        return new JsonResult(subResultCode, errorMsg, null);
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: 发送code为500的Result
     * @Date 2019/10/15 17:28
     * @param: errorMsg
     * @return top.klw8.alita.service.result.JsonResult
     */
    public static JsonResult sendFailedResult(String errorMsg) {
        return new JsonResult(CommonResultCodeEnum.ERROR, errorMsg, null);
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: 发送参数错误Result
     * @Date 2019/10/15 17:11
     * @param: errorMsg
     * @return top.klw8.alita.service.result.JsonResult
     */
    public static JsonResult sendBadParameterResult(String errorMsg) {
        return new JsonResult(CommonResultCodeEnum.BAD_PARAMETER, errorMsg, null);
    }

}
