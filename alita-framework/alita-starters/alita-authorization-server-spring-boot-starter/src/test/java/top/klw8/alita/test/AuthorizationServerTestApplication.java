package top.klw8.alita.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.annotation.ComponentScan;
import top.klw8.alita.base.springctx.EnableSpringApplicationContextUtil;


/**
 * @ClassName: AuthorizationServerTestApplication
 * @Description: 认证服务测试
 * @author klw(213539@qq.com)
 * @date 2020/8/12 16:47
 *
 * code is far away from bugs with the god animal protecting
 *     I love animals. They taste delicious.
 *              ┏┓   ┏┓
 *             ┏┛┻━━━┛┻┓
 *             ┃   ☃   ┃
 *             ┃ ┳┛ ┗┳ ┃
 *             ┃   ┻   ┃
 *             ┗━┓   ┏━┛
 *               ┃   ┗━━━━━━━┓
 *               ┃  神兽保佑   ┣┓
 *               ┃  永无BUG   ┏┛
 *               ┗━┓┓┏━━━┓┓┏━┛
 *                  ┃┫┫   ┃┫┫
 *                  ┗┻┛   ┗┻┛
 */
@SpringBootApplication
@EnableSpringApplicationContextUtil
@ComponentScan(basePackages = {"top.klw8.alita.starter.authorization.cfg"})
public class AuthorizationServerTestApplication {

    public static void main(String[] args) {
        System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);  // 彻底关闭 spring boot 自带的 LoggingSystem
        SpringApplication.run(AuthorizationServerTestApplication.class, args);
    }

}
