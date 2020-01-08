package top.klw8.alita;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.logging.LoggingSystem;
import top.klw8.alita.utils.redis.RedisTagEnum;
import top.klw8.alita.utils.redis.RedisUtil;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: BasicServiceTestApplication
 * @Description: basic service 测试
 * @date 2019/10/16 17:16
 */
@MapperScan("top.klw8.alita.service.authority.mapper")
public class BasicServiceTestApplication extends BaseServiceApplication {

    public static void main(String[] args) {
        System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);  // 彻底关闭 spring boot 自带的 LoggingSystem
        new SpringApplicationBuilder(BasicServiceTestApplication.class)
                .web(WebApplicationType.NONE) // 非 Web 应用
                .run(args);

        // 测试 RedisUtil
//        System.out.println(RedisUtil.incr("test2", 5L, RedisTagEnum.REDIS_TAG_DEFAULT));
//        System.out.println(RedisUtil.incr("test2", 5L, RedisTagEnum.REDIS_TAG_DEFAULT));
//        System.out.println(RedisUtil.incr("test2", 5L, RedisTagEnum.REDIS_TAG_DEFAULT));
    }

}
