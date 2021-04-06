package top.klw8.alita.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.util.AntPathMatcher;
import top.klw8.alita.starter.BaseWebApiApplication;

import java.util.Map;


/**
 * @author klw
 * @ClassName: DemoWebApiApplication
 * @Description: demo restful接口服务启动器
 * @date 2018年9月29日 下午4:12:17
 */
public class DemoWebApiApplication extends BaseWebApiApplication {

    public static void main(String[] args) {
        System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);  // 彻底关闭 spring boot 自带的 LoggingSystem
        SpringApplication.run(DemoWebApiApplication.class, args);

//        AntPathMatcher matcher = new AntPathMatcher();
//        System.out.println(matcher.match("/auth1/{uuid}/{}", "/auth1/xxxx-xxx-xxx/xxx"));
//        Map<String, String> result = matcher.extractUriTemplateVariables("/auth1/{uuid1}-{uuid1}-{uuid3}/xxx", "/auth1/1111-222-333/xxx");
//        System.out.println(result);

//        RedisUtil.set("test_111222", "testtesttest", 300L, RedisTagEnum.REDIS_TAG_TEST);
    }
}
