package top.klw8.alita.starter.annotations;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.klw8.alita.base.springctx.EnableSpringApplicationContextUtil;

/**
 * @ClassName: BaseWebApiApplication
 * @Description: web-api 启动器基类,主要用于配制所有服务都需要的注解
 * @author klw
 * @date 2019年2月20日 下午6:11:29
 */
@SpringBootApplication
@EnableSpringApplicationContextUtil
public class BaseWebApiApplication {

}
