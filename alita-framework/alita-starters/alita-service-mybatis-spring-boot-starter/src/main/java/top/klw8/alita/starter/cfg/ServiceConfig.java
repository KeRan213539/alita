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
package top.klw8.alita.starter.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.klw8.alita.config.redis.RedisRegister;

/**
 * Service 配制
 * 2018年12月21日 下午4:26:40
 */
@Configuration
@Import(RedisRegister.class)
public class ServiceConfig {

}
