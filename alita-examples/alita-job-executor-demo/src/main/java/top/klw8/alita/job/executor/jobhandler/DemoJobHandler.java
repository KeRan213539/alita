package top.klw8.alita.job.executor.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;


import java.math.BigDecimal;
import java.util.concurrent.Future;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Component;
import top.klw8.alita.entitys.demo.mongo.MongoDBTest;
import top.klw8.alita.service.api.demo.ISpringCloudProviderDemoService;


/**
 * @ClassName: DemoJobHandler
 * @Description: 开发步骤： 
 * 1、继承"IJobHandler"：“com.xxl.job.core.handler.IJobHandler”； 
 * 2、注册到Spring容器：添加“@Component”注解，被Spring容器扫描为Bean实例； 
 * 3、注册到执行器工厂：添加“@JobHandler(value="自定义jobhandler名称")”注解，注解value值对应的是调度中心新建任务的JobHandler属性的值。 
 * 4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 * @author klw
 * @date 2018年10月23日 下午3:27:20
 */
@JobHandler(value = "demoJobHandler")
@Component
public class DemoJobHandler extends IJobHandler {
    
    @Reference(async=true)
    private ISpringCloudProviderDemoService service;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
	XxlJobLogger.log("XXL-JOB, Hello World.");
	MongoDBTest test = new MongoDBTest();
	test.setName("定时任务测试");
	test.setSalary(BigDecimal.valueOf(200));
	service.save(test);
	Future<MongoDBTest> task = RpcContext.getContext().getFuture();
	task.get();
	System.out.println("=================执行了");
	return SUCCESS;
    }

    // @Override
    // public ReturnT<String> execute(String param) throws Exception {
    // XxlJobLogger.log("XXL-JOB, Hello World.");
    //
    // for (int i = 0; i < 5; i++) {
    // XxlJobLogger.log("beat at:" + i);
    // TimeUnit.SECONDS.sleep(2);
    // }
    // System.out.println("=================执行了");
    // return SUCCESS;
    // }

}
