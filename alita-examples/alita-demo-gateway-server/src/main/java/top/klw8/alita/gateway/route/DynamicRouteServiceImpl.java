package top.klw8.alita.gateway.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

/**
 * @ClassName: DynamicRouteServiceImpl
 * @Description: 动态路由服务实现
 * @author klw
 * @date 2018年12月16日 下午8:27:02
 */
//@Service
/**
 * 当时参考的: http://springcloud.cn/view/412
 * TODO: 因为以下原因暂时不做动态配制路由功能(已把当前类相关配制注解注释):
 * 1.没有清空当前所有配制的方式,只能做到动态添加和删除指定的
 * 2.现有方式是为启动的时候通过代码(或者配制文件)静态配制提供的,所以不会有需要清空已有配制的情况
 * 3.等spring cloud gateway官方还提供支持动态的方式,或者有了清空所有配制,或者更好的方式
 */
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

	@Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;

    /**
     * @Title: addRoute
     * @author klw
     * @Description: 增加路由
     * @param definition
     * @return 添加数量
     */
    public int addRoute(RouteDefinition definition) {
	routeDefinitionWriter.save(Mono.just(definition)).subscribe();
	publisher.publishEvent(new RefreshRoutesEvent(this));
	return 1;
    }

    /**
     * @Title: updateRoute
     * @author klw
     * @Description: 更新路由
     * @param definition
     * @return 更新数量
     */
    public int updateRoute(RouteDefinition definition) {
	try {
	    routeDefinitionWriter.delete(Mono.just(definition.getId()));
	} catch (Exception e) {
	    // 异常说明没有该路由可以删除,直接略过,继续增加
	}
	try {
	    routeDefinitionWriter.save(Mono.just(definition)).subscribe();
	    publisher.publishEvent(new RefreshRoutesEvent(this));
	    return 1;
	} catch (Exception e) {
	    return 0;
	}
    }

    /**
     * @Title: deleteRoute
     * @author klw
     * @Description: 删除路由
     * @param id
     * @return
     */
    public Mono<ResponseEntity<Object>> deleteRoute(String id) {
	return routeDefinitionWriter.delete(Mono.just(id))
		.then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())))
		.onErrorResume(t -> t instanceof NotFoundException,
			t -> Mono.just(ResponseEntity.notFound().build()));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
	this.publisher = applicationEventPublisher;
    }

}
