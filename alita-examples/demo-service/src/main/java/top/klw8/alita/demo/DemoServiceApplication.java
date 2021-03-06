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
package top.klw8.alita.demo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.logging.LoggingSystem;
import top.klw8.alita.BaseServiceApplication;


/**
 * 订单服务启动器
 * 2018年9月29日 上午11:43:34
 */
@EnableDubbo(scanBasePackages = {"top.klw8.alita.service.mybatisdemo.providers"})
@MapperScan("top.klw8.alita.service.mybatisdemo.services.user.mapper")
public class DemoServiceApplication extends BaseServiceApplication {

    public static void main(String[] args) {
        System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);  // 彻底关闭 spring boot 自带的 LoggingSystem
        new SpringApplicationBuilder(DemoServiceApplication.class)
                .web(WebApplicationType.NONE) // 非 Web 应用
                .run(args);
    }

}
