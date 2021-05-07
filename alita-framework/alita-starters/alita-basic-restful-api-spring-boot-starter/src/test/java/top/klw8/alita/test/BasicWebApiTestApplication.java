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
import org.springframework.boot.logging.LoggingSystem;
import top.klw8.alita.starter.BaseWebApiApplication;


/**
 * 基础服务web api服务测试用启动器
 * 2018年9月29日 下午4:12:17
 */
public class BasicWebApiTestApplication extends BaseWebApiApplication {

    public static void main(String[] args) {
	    // 彻底关闭 spring boot 自带的 LoggingSystem
	    System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);
        SpringApplication.run( BasicWebApiTestApplication.class, args );
    }
    
    
}
