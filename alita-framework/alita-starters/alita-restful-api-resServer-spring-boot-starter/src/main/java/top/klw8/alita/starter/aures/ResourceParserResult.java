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

import lombok.Getter;

/**
 * 资源解析器的返回参数 Bean
 * 2020/7/23 16:51
 */
@Getter
public class ResourceParserResult {

    /**
     * 资源解析器解析出的资源
     */
    private final String[] parsedResources;

    /**
     * 是否万能钥匙,如果是则直接通过资源权限验证
     */
    private final boolean masterKey;

    public ResourceParserResult(){
        this.parsedResources = new String[0];
        this.masterKey = false;
    }

    public ResourceParserResult(String[] parsedResources){
        this.parsedResources = parsedResources;
        this.masterKey = false;
    }

    public ResourceParserResult(boolean isMasterKey){
        this.parsedResources = new String[0];
        this.masterKey = isMasterKey;
    }



}
