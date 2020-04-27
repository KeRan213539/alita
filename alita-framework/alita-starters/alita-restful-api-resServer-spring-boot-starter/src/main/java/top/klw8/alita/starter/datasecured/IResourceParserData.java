package top.klw8.alita.starter.datasecured;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: IResourceParserData
 * @Description: 资源解析器参数
 * @date 2020/4/24 14:21
 */
public interface IResourceParserData {

    List<String> getQueryPrarm(String prarmName);

    List<String> getFormData(String prarmName);

    String getJsonString();

    String getXmlString();

}
