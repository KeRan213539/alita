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
package top.klw8.alita.starter.datasecured;

import java.lang.annotation.*;

/**
 * 数据权限注解, 数据权限与url权限相对独立,只要使用了该注解就会验证,不管在 alita.oauth2.resServer.authPath 中是否配制了url
 * 2020/4/24 14:28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface DataSecured {

    /**
     * 资源名称
     */
    String[] resource() default {};

    /**
     * 资源解析器,如果配制了该属性则忽略资源名称属性.
     */
    Class<? extends IResourceParser> parser() default IResourceParser.class;

    /**
     * 是否是文件上传,默认false
     */
    boolean fileUpload() default false;

}
