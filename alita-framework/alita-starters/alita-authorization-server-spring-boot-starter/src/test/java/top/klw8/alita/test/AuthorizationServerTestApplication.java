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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.annotation.ComponentScan;
import top.klw8.alita.base.springctx.EnableSpringApplicationContextUtil;


/**
 * 认证服务测试
 * 2020/8/12 16:47
 *
 * code is far away from bugs with the god animal protecting
 *     I love animals. They taste delicious.
 *              ┏┓   ┏┓
 *             ┏┛┻━━━┛┻┓
 *             ┃   ☃   ┃
 *             ┃ ┳┛ ┗┳ ┃
 *             ┃   ┻   ┃
 *             ┗━┓   ┏━┛
 *               ┃   ┗━━━━━━━┓
 *               ┃  神兽保佑   ┣┓
 *               ┃  永无BUG   ┏┛
 *               ┗━┓┓┏━━━┓┓┏━┛
 *                  ┃┫┫   ┃┫┫
 *                  ┗┻┛   ┗┻┛
 */
@SpringBootApplication
@EnableSpringApplicationContextUtil
@ComponentScan(basePackages = {"top.klw8.alita.test", "top.klw8.alita.starter.authorization.cfg"})
public class AuthorizationServerTestApplication {

    public static void main(String[] args) {
        System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);  // 彻底关闭 spring boot 自带的 LoggingSystem
        SpringApplication.run(AuthorizationServerTestApplication.class, args);
    }

}
