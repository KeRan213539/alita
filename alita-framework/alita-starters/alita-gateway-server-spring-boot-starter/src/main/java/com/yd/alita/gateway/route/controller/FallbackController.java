package com.yd.alita.gateway.route.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: FallbackController
 * @Description: fallback
 * @date 2019/11/18 15:07
 */
@RestController
public class FallbackController {

    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }

}
