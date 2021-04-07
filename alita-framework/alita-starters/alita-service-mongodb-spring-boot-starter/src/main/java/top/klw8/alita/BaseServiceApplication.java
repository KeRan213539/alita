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
package top.klw8.alita;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import top.klw8.alita.base.springctx.EnableSpringApplicationContextUtil;

/**
 * @ClassName: BaseServiceApplication
 * @Description: 服务启动器基类,主要用于配制所有服务都需要的注解
 * @author klw
 * @date 2019年2月20日 下午6:05:19
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class,MongoDataAutoConfiguration.class})
@EnableSpringApplicationContextUtil
public class BaseServiceApplication {

}
