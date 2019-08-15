package top.klw8.alita.demo.web.demo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import top.klw8.alita.entitys.demo.PgTest;
import top.klw8.alita.service.api.mybatis.IPgTestService;
import top.klw8.alita.service.result.JsonResult;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: MybatisDemoController
 * @Description: mybatis demo
 * @date 2019/8/8 15:47
 */
@Api(tags = {"alita-restful-API--demoAPI"})
@RestController
@RequestMapping("/demo2")
@Slf4j
public class MybatisDemoController {

    @Reference(async = true)
    private IPgTestService pgTestService;

    @ApiOperation(value = "新增保存", notes = "新增保存", httpMethod = "POST", produces = "application/json")
    @PostMapping("/save")
    public Mono<JsonResult> addSave(PgTest vo) throws ExecutionException, InterruptedException {
        pgTestService.save(vo);
        Future<Boolean> task = RpcContext.getContext().getFuture();
        if (task == null) {
            return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
        }
        return Mono.just(task.get()).map(list -> {
            return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, list);
        });
    }

}
