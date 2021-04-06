package top.klw8.alita.starter.utils;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: FormDataNoFileParserUtil
 * @Description: 解析非文件上传的form data数据工具类
 * @date 2020/4/29 10:19
 */
public class FormDataNoFileParserUtil {

    private final static String FORM_DATA_PLACEHOLDER = "Content-Disposition: form-data; name=";

    /**
     * @author klw(213539@qq.com)
     * @Description: 解析表单数据字符串,解析类似如下格式的字符串
     ----------------------------233968767889846891411876
     Content-Disposition: form-data; name="data1"

     111
     ----------------------------233968767889846891411876
     Content-Disposition: form-data; name="data2"

     xxx
     ----------------------------233968767889846891411876
     Content-Disposition: form-data; name="data3"

     2321ddssd
     ----------------------------233968767889846891411876--
     *
     * @Date 2020/4/29 10:27
     * @param: dataStr  表单数据字符串
     * @param: boundary  分界符
     * @return java.util.Map<java.lang.String,java.util.List<java.lang.String>>
     */
    public static Map<String, List<String>> parser(String dataStr, String boundary){
        Assert.hasText(dataStr, "表单数据字符串不能为空");
        Assert.hasText(boundary, "分界符不能为空");
        String separators = "--" + boundary;
        String[] dataStrSpliteds = dataStr.replace("\r\n", "").replace("\n", "").split(separators);
        Map<String, List<String>> result = new HashMap<>(16);
        for(String dataStrSplited : dataStrSpliteds){
            int begin = dataStrSplited.indexOf(FORM_DATA_PLACEHOLDER);
            int end = dataStrSplited.lastIndexOf("\"");
            if(begin != -1){
                String name = dataStrSplited.substring(begin + FORM_DATA_PLACEHOLDER.length() + 1, end);
                String value = dataStrSplited.substring(end + 1);
                List<String> values = result.get(name);
                if(values == null){
                    values = new ArrayList<>(16);
                    result.put(name, values);
                }
                values.add(value);
            }
        }
        return result;
    }

}
