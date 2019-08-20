package top.klw8.alita.starter.authorization.cfg;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.klw8.alita.config.redis.RedisRegister;
import top.klw8.alita.starter.authorization.validator.AlitaResponseGenerator;
import top.klw8.alita.validator.EnableValidator;

/**
 * @ClassName: WebApiConfig
 * @Description: 
 * @author klw
 * @date 2018年12月4日 上午11:58:05
 */
@Configuration
@RefreshScope
@EnableDiscoveryClient
@EnableValidator(responseMsgGenerator = AlitaResponseGenerator.class)
@Import(RedisRegister.class)
public class WebApiConfig {

}
