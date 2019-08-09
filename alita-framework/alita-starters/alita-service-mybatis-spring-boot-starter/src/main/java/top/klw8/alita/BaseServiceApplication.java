package top.klw8.alita;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import top.klw8.alita.base.springctx.EnableSpringApplicationContextUtil;

/**
 * @ClassName: BaseServiceApplication
 * @Description: 服务启动器基类,主要用于配制所有服务都需要的注解
 * @author klw
 * @date 2019年2月20日 下午6:05:19
 */
@SpringBootApplication
@EnableSpringApplicationContextUtil
public class BaseServiceApplication {

}
