package top.klw8.alita.starter.datasecured;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: IResourceParserData
 * @Description: 资源解析器参数
 * @date 2020/4/24 14:21
 */
public interface IResourceParserData {

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取请求的url,可以根据此参数实现一个解析器多用
     */
    String getRequestUrl();

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取请求的url参数
     */
    List<String> getQueryPrarm(String prarmName);

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取请求的表单参数
     */
    List<String> getFormData(String prarmName);

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取body中的json字符串
     */
    String getJsonString();

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取body中的xml字符串
     */
    String getXmlString();

}
