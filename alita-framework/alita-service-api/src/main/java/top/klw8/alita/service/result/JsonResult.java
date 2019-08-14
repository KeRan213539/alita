package top.klw8.alita.service.result;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import top.klw8.alita.service.result.code.ResultCodeEnum;
import top.klw8.alita.service.result.code.ResultStatusEnum;


/**
 * @author klw
 * @ClassName: JsonResult
 * @Description: webapi 项目统一消息返回格式包装
 * @date 2018年12月7日 下午1:36:48
 */
public class JsonResult {

    public JsonResult() {

    }

    public JsonResult(String msg) {
        this.msg = msg;
    }

    /**
     * 请求码0失败，1成功
     */
    private int status = 0;
    /**
     * 消息码默认为 0无错误 2 token失效
     */
    private int code = 0;

    /**
     * 消息
     */
    private String msg;

    /**
     * 返回的json数据
     */
    private Object data;

    private boolean success = false;

    public int getStatus() {
        return status;
    }

    private void setStatus(ResultStatusEnum status) {
        this.status = status.intValue();
    }


    public int getCode() {
        return code;
    }

    private void setCode(ResultCodeEnum code) {
        this.code = code.intValue();
    }

    public String getMsg() {
        return msg;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    private void setData(Object data) {
        this.data = data;
    }


    public Map<String, Object> toMap() {
        return JSON.parseObject(data.toString());
    }

    /**
     * 含返回数据的JsonResult
     *
     * @param successfulMessage
     * @param data
     * @return
     */
    public static JsonResult sendSuccessfulResult(String successfulMessage, Object data) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(ResultStatusEnum.SUCCESS);
        jsonResult.setCode(ResultCodeEnum._200);
        jsonResult.setMsg(successfulMessage);
        jsonResult.setData(data);
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    public static JsonResult sendFailedResult(String failedMessage, Object data) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(ResultStatusEnum.FAILED);
        jsonResult.setCode(ResultCodeEnum._500);
        jsonResult.setMsg(failedMessage);
        jsonResult.setData(data);
        jsonResult.setSuccess(false);
        return jsonResult;
    }

    public static JsonResult sendTokenError() {
        JsonResult json = new JsonResult();
        json.setStatus(ResultStatusEnum.FAILED);
        json.setCode(ResultCodeEnum._250);
        json.setMsg(CallBackMessage.tokenError);
        json.setSuccess(false);
        return json;
    }

    public static JsonResult sendParamError() {
        JsonResult json = new JsonResult();
        json.setStatus(ResultStatusEnum.FAILED);
        json.setCode(ResultCodeEnum._400);
        json.setMsg(CallBackMessage.paramError);
        json.setSuccess(false);
        return json;
    }

    public static JsonResult sendParamError(String msg) {
        JsonResult json = new JsonResult();
        json.setStatus(ResultStatusEnum.FAILED);
        json.setCode(ResultCodeEnum._400);
        json.setMsg(msg);
        json.setSuccess(false);
        return json;
    }

    public static JsonResult sendResult(ResultStatusEnum status, ResultCodeEnum code, String msg, Object data) {
        JsonResult json = new JsonResult();
        json.setStatus(status);
        json.setCode(code);
        json.setMsg(msg);
        json.setData(data);
        return json;
    }

    public static JsonResult sendResult(ResultStatusEnum status, ResultCodeEnum code, String msg, Object data,
                                        boolean success) {
        JsonResult json = new JsonResult();
        json.setStatus(status);
        json.setCode(code);
        json.setMsg(msg);
        json.setData(data);
        json.setSuccess(success);
        return json;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "JsonResult [status=" + status + ", code=" + code + ", msg=" + msg + ", data=" + data
                + "]";
    }
}
