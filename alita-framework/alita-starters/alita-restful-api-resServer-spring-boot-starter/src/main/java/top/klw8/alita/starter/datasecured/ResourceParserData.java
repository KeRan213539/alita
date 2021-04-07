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
package top.klw8.alita.starter.datasecured;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ResourceParserData
 * @Description: 资源解析器参数,不包含文件上传中的文件. 线程安全.不支持序列化传输
 * @date 2020/4/24 14:05
 */
public class ResourceParserData implements IResourceParserData {

    /**
     * @author klw(213539@qq.com)
     * @Description: 请求的url,可以根据此参数实现一个解析器多用
     */
    private String requestUrl;

    /**
     * @author klw(213539@qq.com)
     * @Description: url地址参数
     */
    private Map<String, String> pathPrarms;

    /**
     * @author klw(213539@qq.com)
     * @Description: url参数
     */
    private Map<String, List<String>> queryPrarms;

    /**
     * @author klw(213539@qq.com)
     * @Description: post 的表单参数
     */
    private Map<String, List<String>> formData;

    /**
     * @author klw(213539@qq.com)
     * @Description: post 的 json 字符串
     */
    private String jsonString;

    /**
     * @author klw(213539@qq.com)
     * @Description: post 的 xml 字符串
     */
    private String xmlString;

    public ResourceParserData(String requestUrl){
        this.requestUrl = requestUrl;
    }

    @Override
    public String getRequestUrl() {
        return this.requestUrl;
    }

    public void putQueryPrarm(String prarmName, List<String> prarmValue){
        if(this.queryPrarms == null) {
            synchronized (this) {
                if(this.queryPrarms == null) {
                    this.queryPrarms = new ConcurrentHashMap<>(16);
                }
            }
        }
        this.queryPrarms.put(prarmName, prarmValue);
    }

    @Override
    public String getPathPrarm(String prarmName){
        if(this.pathPrarms == null){
            return null;
        }
        return this.pathPrarms.get(prarmName);
    }

    public void putPathPrarm(String prarmName, String prarmValue){
        this.initPathPrarmMap();
        this.pathPrarms.put(prarmName, prarmValue);
    }

    public void putAllPathPrarms(Map<String, String> data){
        this.initPathPrarmMap();
        this.pathPrarms.putAll(data);
    }

    private void initPathPrarmMap(){
        if(this.pathPrarms == null) {
            synchronized (this) {
                if(this.pathPrarms == null) {
                    this.pathPrarms = new ConcurrentHashMap<>(16);
                }
            }
        }
    }

    @Override
    public List<String> getQueryPrarm(String prarmName){
        if(this.queryPrarms == null){
            return null;
        }
        return this.queryPrarms.get(prarmName);
    }

    public void putFormData(String prarmName, List<String> prarmValue){
        this.initFormDataMap();
        this.formData.put(prarmName, prarmValue);
    }

    public void putAllFormData(Map<String, List<String>> data){
        this.initFormDataMap();
        this.formData.putAll(data);
    }

    private void initFormDataMap(){
        if(this.formData == null) {
            synchronized (this) {
                if(this.formData == null) {
                    this.formData = new ConcurrentHashMap<>(16);
                }
            }
        }
    }

    @Override
    public List<String> getFormData(String prarmName){
        if(this.formData == null){
            return null;
        }
        return this.formData.get(prarmName);
    }

    public void setJsonString(String jsonString){
        this.jsonString = jsonString;
    }

    @Override
    public String getJsonString(){
        return this.jsonString;
    }

    public void setXmlString(String xmlString){
        this.xmlString = xmlString;
    }

    @Override
    public String getXmlString(){
        return this.xmlString;
    }

}
