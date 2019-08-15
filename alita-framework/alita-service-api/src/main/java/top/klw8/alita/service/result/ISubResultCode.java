package top.klw8.alita.service.result;

/**
 * @author klw
 * @ClassName: ISubResultCode
 * @Description: 业务ResultCode需要实现的接口
 * @date 2019/6/6 17:06
 */
public interface ISubResultCode {

    /**
     * @author klw
     * @Description: 获取完整code(分类+业务)
     * @Date 2019/6/6 17:11
     * @Param []
     * @return java.lang.String
     */
    String getCode();

    /**
     * @author klw
     * @Description: 获取code对应的消息
     * @Date 2019/6/6 17:11
     * @Param []
     * @return java.lang.String
     */
    String getCodeMsg();

    /**
     * @author klw
     * @Description: 获取类别
     * @Date 2019/6/10 12:55
     * @Param []
     * @return com.ejkj.common.resultCode.ResultCodeEnum
     */
    IResultCode getClassify();

}
