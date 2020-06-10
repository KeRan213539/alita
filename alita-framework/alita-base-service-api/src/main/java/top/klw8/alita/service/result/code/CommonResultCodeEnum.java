package top.klw8.alita.service.result.code;

import top.klw8.alita.service.result.IResultCode;
import top.klw8.alita.service.result.ISubResultCode;

/**
 * @author klw
 * @ClassName: CommonResultCodeEnum
 * @Description: 通用返回码
 * @date 2019/6/10 16:13
 */
public enum CommonResultCodeEnum implements ISubResultCode {

    OK("200", "请求处理成功"),

    ERROR("500", "请求处理出现错误"),

    NO_TOKEN("401", "请求中没有token"),

    TOKEN_ERR("403", "请求中没有token或者token不正确"),

    NO_PRIVILEGES("403", "没有权限"),

    REGISTER_NO("5001", "用户未注册"),

    BAD_PARAMETER("5002", "参数错误"),

    LOGIN_TIMEOUT("5003", "登录超时"),

    DATA_SECURED_NO_RES("5004", "数据权限注解没有配制资源和解析器"),


    ;


    /**
     * @author klw
     * @Description: 所属分类, 写死
     * @Date 2019/6/6 17:41
     * @Param
     * @return
     */
    private IResultCode classify = ResultCodeEnum.COMMON;

    /**
     * @author klw
     * @Description: 业务code(3位)
     */
    private String subCode;


    /**
     * @author klw
     * @Description: code 对应的消息
     */
    private String codeMsg;

    CommonResultCodeEnum(String subCode, String codeMsg){
        this.subCode = subCode;
        this.codeMsg = codeMsg;
    }

    @Override
    public String getCode() {
        return classify.getCode() + subCode;
    }

    @Override
    public String getCodeMsg() {
        return codeMsg;
    }

    @Override
    public IResultCode getClassify() {
        return classify;
    }
}