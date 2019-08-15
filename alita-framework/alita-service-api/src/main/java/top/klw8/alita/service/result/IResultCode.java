package top.klw8.alita.service.result;

/**
 * @author klw
 * @ClassName: IResultCode
 * @Description: ResultCode分类需要实现的接口
 * @date 2019/6/6 17:06
 */
public interface IResultCode {

    /**
     * @author klw
     * @Description: 获取分类code
     * @Date 2019/6/6 17:11
     * @Param []
     * @return java.lang.String
     */
    String getCode();

    /**
     * @author klw
     * @Description: 获取分类名称
     * @Date 2019/6/6 17:11
     * @Param []
     * @return java.lang.String
     */
    String getCodeName();

}
