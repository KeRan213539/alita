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
package top.klw8.alita.starter.aures;

/**
 *  资源解析器接口
 * 2020/4/24 11:18
 */
public interface IResourceParser {

    /**
     * 根据传入的数据解析出资源权限的资源名称, 支持多资源名称.
     * 如果返回多个资源名称,那么当前请求用户必须多个资源权限都必须拥有
     */
    default ResourceParserResult parseResource(IResourceParserData parserPojo){
        return new ResourceParserResult();
    }

}
