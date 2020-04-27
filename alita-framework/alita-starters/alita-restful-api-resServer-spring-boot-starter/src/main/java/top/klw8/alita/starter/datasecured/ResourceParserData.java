package top.klw8.alita.starter.datasecured;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ResourceParserData
 * @Description: 资源解析器参数,不包含文件上传中的文件. 线程安全.不支持序列化传输
 * @date 2020/4/24 14:05
 */
public class ResourceParserData implements IResourceParserData {

    /**
     * @author klw(213539@qq.com)
     * @Description: url参数
     */
    private Map<String, List<String>> queryPrarms;

    /**
     * @author klw(213539@qq.com)
     * @Description: post 的表单参数
     */
    private Map<String, List<String>> formData;

    /**
     * @author klw(213539@qq.com)
     * @Description: post 的 json 字符串
     */
    private String jsonString;

    /**
     * @author klw(213539@qq.com)
     * @Description: post 的 xml 字符串
     */
    private String xmlString;

    public void putQueryPrarm(String prarmName, List<String> prarmValue){
        if(this.queryPrarms == null) {
            synchronized (this) {
                if(this.queryPrarms == null) {
                    this.queryPrarms = new ConcurrentHashMap<>(16);
                }
            }
        }
        this.queryPrarms.put(prarmName, prarmValue);
    }

    public List<String> getQueryPrarm(String prarmName){
        if(this.queryPrarms == null){
            return null;
        }
        return this.queryPrarms.get(prarmName);
    }

    public void putFormData(String prarmName, List<String> prarmValue){
        if(this.formData == null) {
            synchronized (this) {
                if(this.formData == null) {
                    this.formData = new ConcurrentHashMap<>(16);
                }
            }
        }
        this.formData.put(prarmName, prarmValue);
    }

    public List<String> getFormData(String prarmName){
        if(this.formData == null){
            return null;
        }
        return this.formData.get(prarmName);
    }

    public void setJsonString(String jsonString){
        this.jsonString = jsonString;
    }

    public String getJsonString(){
        return this.jsonString;
    }

    public void setXmlString(String xmlString){
        this.xmlString = xmlString;
    }

    public String getXmlString(){
        return this.xmlString;
    }

}
