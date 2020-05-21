package top.klw8.alita.service.result.code;

import top.klw8.alita.service.result.IResultCode;
import top.klw8.alita.service.result.ISubResultCode;
import top.klw8.alita.service.result.SubResultCode;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AuthorityResultCodeEnum
 * @Description: 权限相关
 * @date 2019/8/15 16:52
 */
@SubResultCode
public enum AuthorityResultCodeEnum implements ISubResultCode {

    CATLOG_NOT_EXIST("001", "权限目录不存在"),

    ROLE_NOT_EXIST("002", "角色不存在"),

    AUTHORITY_NOT_EXIST("003", "权限不存在"),

    USER_NOT_EXIST("004", "用户不存在"),

    SYSTEM_DATA_SECURED_HAS_EXIST("005", "该资数据权限已存在(全局或者同权限中,数据权限资源标识不能相同)"),

    ;
    /**
     * @author klw
     * @Description: 所属分类, 写死
     * @Date 2019/6/6 17:41
     * @Param
     * @return
     */
    private IResultCode classify = ResultCodeEnum.AUTHORITY;

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

    AuthorityResultCodeEnum(String subCode, String codeMsg){
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
