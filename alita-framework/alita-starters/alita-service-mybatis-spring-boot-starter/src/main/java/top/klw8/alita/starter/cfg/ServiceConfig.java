package top.klw8.alita.starter.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.klw8.alita.config.redis.RedisRegister;

/**
 * @ClassName: ServiceConfig
 * @Description: Service 配制
 * @author klw
 * @date 2018年12月21日 下午4:26:40
 */
@Configuration
@Import(RedisRegister.class)
public class ServiceConfig {

}
