package top.klw8.alita.starter.service.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ServiceContext
 * @Description: service 的系统上下文
 * @date 2019/8/10 10:11
 */
public class ServiceContext {

    public static final ExecutorService executor;

    static {
        // T(线程数) = N(服务器内核数) * u(期望cpu利用率) * （1 + E(等待时间)/C(计算时间));
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 40 * (1 + 5 / 2));
    }

}
