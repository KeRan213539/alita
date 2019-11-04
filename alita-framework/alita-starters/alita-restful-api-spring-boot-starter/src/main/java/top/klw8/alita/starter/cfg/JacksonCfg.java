package top.klw8.alita.starter.cfg;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * @ClassName: JacksonCfg
 * @Description: 
 * @author klw
 * @date 2018年12月11日 17:49:59
 */
@Configuration
public class JacksonCfg {
  
  @Primary
  @Bean
  public Jackson2ObjectMapperBuilderCustomizer customJackson() {
      return new Jackson2ObjectMapperBuilderCustomizer() {

          @Override
          public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
              jacksonObjectMapperBuilder.serializationInclusion(Include.NON_NULL);
              jacksonObjectMapperBuilder.failOnUnknownProperties(false);
              // 属性名格式由驼峰转成下划线
//              jacksonObjectMapperBuilder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
              jacksonObjectMapperBuilder.failOnEmptyBeans(false);
              jacksonObjectMapperBuilder.featuresToEnable(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS);
              jacksonObjectMapperBuilder.featuresToEnable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
              jacksonObjectMapperBuilder.dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

              DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
              jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
              jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));

              DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
              jacksonObjectMapperBuilder.serializerByType(LocalDate.class, new LocalDateSerializer(formatter2));
              jacksonObjectMapperBuilder.deserializerByType(LocalDate.class, new LocalDateDeserializer(formatter2));


          }

      };
  }

}
