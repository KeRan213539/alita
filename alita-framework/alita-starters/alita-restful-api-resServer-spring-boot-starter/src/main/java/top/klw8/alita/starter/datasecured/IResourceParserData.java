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

    /***
     * @author klw(213539@qq.com)
     * @Description: 获取url地址参数(通过 @PathVariable 参数)
     * @Date 2020/5/11 14:27
     * @param: prarmName
     * @return java.lang.String
     */
    String getPathPrarm(String prarmName);

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取请求的url参数(?号之后的key=value)
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
