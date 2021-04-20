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

import java.util.List;

/**
 * 资源解析器参数
 * 2020/4/24 14:21
 */
public interface IResourceParserData {

    /**
     * 获取请求的url,可以根据此参数实现一个解析器多用
     */
    String getRequestUrl();

    /***
     * 获取url地址参数(通过 @PathVariable 参数)
     * 2020/5/11 14:27
     * @param: prarmName
     * @return java.lang.String
     */
    String getPathPrarm(String prarmName);

    /**
     * 获取请求的url参数(?号之后的key=value)
     */
    List<String> getQueryPrarm(String prarmName);

    /**
     * 获取请求的表单参数
     */
    List<String> getFormData(String prarmName);

    /**
     * 获取body中的json字符串
     */
    String getJsonString();

    /**
     * 获取body中的xml字符串
     */
    String getXmlString();

}
