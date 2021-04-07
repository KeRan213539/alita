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
package top.klw8.alita.demo.cfg;

/**
 * @ClassName ContextPathConfig
 * @Description TODO 下面被注释的部分主要提供springboot使用将配置在程序启动的时候就最先加载配置以便controller的@RequestMapping("${..}")的方式可以注入配置并解析，在spring cloud alibaba中已经替我们做过同样的事情故在apring cloud alibaba中不需要使用
 * @Author zhanglei
 * @Date 2019/8/27 17:49
 * @Version 1.0
 */
//@Configuration
//public class ContextPathConfig implements WebMvcConfigurer  {
//
//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        System.out.println("==================== init PropertySourcesPlaceholderConfigurer ==========================");
//        return new PropertySourcesPlaceholderConfigurer();
//    }
//
//}
