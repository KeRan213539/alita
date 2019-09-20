package top.klw8.alita.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DubboDebugToolController
 * @Description: dubbo调试工具接口
 * @date 2019/9/19 17:21
 */
@Api(tags = {"alita-restful-API--demoAPI"})
@RestController
@RequestMapping("${spring.application.name}/demo")
@Slf4j
public class DubboDebugToolController {

    public Mono<String> callDubboService(){

    }

}
