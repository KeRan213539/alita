package top.klw8.alita.starter.authorization.cfg;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;

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
              jacksonObjectMapperBuilder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
              jacksonObjectMapperBuilder.failOnEmptyBeans(false);
              jacksonObjectMapperBuilder.featuresToEnable(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS);
              jacksonObjectMapperBuilder.featuresToEnable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
              jacksonObjectMapperBuilder.dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
          }

      };
  }

}
