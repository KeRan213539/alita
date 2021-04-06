package top.klw8.alita.fileupload.cfg.resultCode;

import top.klw8.alita.service.result.IResultCode;
import top.klw8.alita.service.result.ISubResultCode;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AuthorityResultCodeEnum
 * @Description: 权限相关
 * @date 2019/8/15 16:52
 */
public enum ResultSubCodeEnum implements ISubResultCode {

    FILE_NAME_EMPTY("001", "文件名不能为空"),

    FILE_DATA_EMPTY("002", "文件数据为空"),

    FILE_UPLOAD_FAIL("003", "文件上传失败"),

    DELETE_OSS_FILE_FAIL("004", "删除OSS文件失败"),


    ;
    /**
     * @author klw
     * @Description: 所属分类, 写死
     * @Date 2019/6/6 17:41
     * @Param
     * @return
     */
    private IResultCode classify = ResultCodeEnum.FILE_UPLOAD;

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

    ResultSubCodeEnum(String subCode, String codeMsg){
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
