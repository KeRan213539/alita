package top.klw8.alita.starter.service.common;

import top.klw8.alita.service.result.JsonResult;

import java.util.concurrent.CompletableFuture;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ServiceUtil
 * @Description: 工具类
 * @date 2019/10/16 9:53
 */
public class ServiceUtil {

    public static CompletableFuture buildFuture(JsonResult result){
        return CompletableFuture.supplyAsync(() -> result, ServiceContext.executor);
    }

}
