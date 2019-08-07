package top.klw8.alita.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

/**
 * @ClassName: GatewayServerApplication
 * @Description: 启动 spring cloud gateway (路由网关)
 * @author klw
 * @date 2018-11-14 15:42:54
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class GatewayServerApplication {

    public static void main(String[] args) {
	SpringApplication.run(GatewayServerApplication.class);
    }
    
    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
	return builder.routes()
		.route(p -> p
	                .path("/admin/**")
	                .filters(f -> f
	                	.hystrix(config -> config
        	                        .setName("adminServiceHystrix")
        	                        .setFallbackUri("forward:/fallback")))
	                .uri("lb://restful-api-admin"))
		.route(p -> p
	                .path("/demo/**")
	                .filters(f -> f
	                	.hystrix(config -> config
        	                        .setName("demoHystrix")
        	                        .setFallbackUri("forward:/fallback")))
	                .uri("lb://restful-api-demo"))
		.build();
    }

}
