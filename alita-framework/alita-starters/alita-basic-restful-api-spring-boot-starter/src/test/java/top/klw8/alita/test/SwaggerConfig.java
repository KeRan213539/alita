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


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import top.klw8.alita.web.cfg.BasicWebApisSwaggerConfig;

/**
 * SwaggerConfig
 * 2019/11/4 15:08
 */
@Configuration
@Profile("dev")  // 仅在dev模式(application.yml中的spring.profiles.active=dev时)下才会执行该配制
public class SwaggerConfig extends BasicWebApisSwaggerConfig {

    public SwaggerConfig() {
        super();
    }

}
