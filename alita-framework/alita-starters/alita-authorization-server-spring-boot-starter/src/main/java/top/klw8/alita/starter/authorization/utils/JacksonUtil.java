package top.klw8.alita.starter.authorization.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.text.SimpleDateFormat;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: JacksonUtil
 * @Description: Jackson工具类
 * @date 2019/9/24 20:09
 */
public class JacksonUtil {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        // 解决问题：Could not write JSON: No serializer found for
        // class org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer and no properties discovered to
        // create BeanSerializer
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.setFailOnUnknownId(false);
        objectMapper.setFilterProvider(filterProvider);

//        objectMapper.setSerializationInclusion(Include.NON_NULL); // 转JSON的时候不能为空，如果为空的字段则不进入JSON对象
//        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() { // null转空字符串
//            @Override
//            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
//            gen.writeString("");
//            }
//        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(dateFormat);
    }

    public static String writeValueAsString(Object obj){
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex){
            return null;
        }

    }

}
