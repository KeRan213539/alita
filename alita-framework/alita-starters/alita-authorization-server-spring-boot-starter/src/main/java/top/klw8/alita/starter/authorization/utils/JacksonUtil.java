/*
 * Copyright 2018-2021, ranke (213539@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.starter.authorization.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.text.SimpleDateFormat;

/**
 * Jackson工具类
 * 2019/9/24 20:09
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
