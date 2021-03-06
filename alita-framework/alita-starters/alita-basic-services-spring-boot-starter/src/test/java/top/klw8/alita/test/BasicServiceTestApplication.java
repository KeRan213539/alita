/*
 * Copyright 2018-2021, ranke (213539@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.test;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.ApplicationContext;
import top.klw8.alita.BaseServiceApplication;
import top.klw8.alita.test.spitest.SPITest;
import top.klw8.alita.test.spitest.SPITest2;
//import top.klw8.alita.utils.redis.RedisTagEnum;
//import top.klw8.alita.utils.redis.RedisUtil;

/**
 * basic service 测试
 * 2019/10/16 17:16
 */
@EnableDubbo
public class BasicServiceTestApplication extends BaseServiceApplication {

    public static void main(String[] args) {
        System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);  // 彻底关闭 spring boot 自带的 LoggingSystem
        ApplicationContext context = new SpringApplicationBuilder(BasicServiceTestApplication.class)
                .web(WebApplicationType.NONE) // 非 Web 应用
                .run(args);

        SPITest test1 = context.getBean(SPITest.class);
        test1.doSPIInterface();
        SPITest2.doSPIInterface();

        // 测试 RedisUtil
//        System.out.println(RedisUtil.incr("test2", 5L, RedisTagEnum.REDIS_TAG_DEFAULT));
//        System.out.println(RedisUtil.incr("test2", 5L, RedisTagEnum.REDIS_TAG_DEFAULT));
//        System.out.println(RedisUtil.incr("test2", 5L, RedisTagEnum.REDIS_TAG_DEFAULT));
//        ExecutorService executorService = Executors.newCachedThreadPool();
        //executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 40 * (1 + 5 / 2));
//        for(int i=0;i<100;i++) {
//            executorService.execute(() -> {
//                System.out.println(RedisUtil.setnx("1111", "222", 5L, RedisTagEnum.REDIS_TAG_DEFAULT));
//            });
//        }
    
    }

}
