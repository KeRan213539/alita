/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.job.executor.jobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;


import java.math.BigDecimal;
import java.util.concurrent.Future;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;

import org.springframework.stereotype.Component;
import top.klw8.alita.entitys.demo.mongo.MongoDBTest;
import top.klw8.alita.service.api.demo.ISpringCloudProviderDemoService;


/**
 * @author klw
 * @ClassName: DemoJobHandler
 * @Description: 开发步骤：
 * 1、继承"IJobHandler"：“com.xxl.job.core.handler.IJobHandler”；
 * 2、注册到Spring容器：添加“@Component”注解，被Spring容器扫描为Bean实例；
 * 3、注册到执行器工厂：添加“@JobHandler(value="自定义jobhandler名称")”注解，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 * @date 2018年10月23日 下午3:27:20
 */
@Component
public class DemoJobHandler extends IJobHandler {

    @DubboReference(async = true)
    private ISpringCloudProviderDemoService service;

    @Override
    public void execute() throws Exception {
        String param = XxlJobHelper.getJobParam();
        XxlJobHelper.log("XXL-JOB, Hello World.");
        MongoDBTest test = new MongoDBTest();
        test.setName("定时任务测试");
        test.setSalary(BigDecimal.valueOf(200));
        service.save(test);
        Future<MongoDBTest> task = RpcContext.getContext().getFuture();
        task.get();
        System.out.println("=================执行了");
        XxlJobHelper.handleSuccess();
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
