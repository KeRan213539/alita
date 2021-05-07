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
package top.klw8.alita.entitys.authority.jsonfield;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 权限扩展数据Bean
 * 2020/5/13 9:44
 */
@Getter
@Setter
public class SystemAuthoritysAdditionalData implements java.io.Serializable {

    public enum Type{
        /**
         * 资源权限动态源
         */
        AUTHORITYS_RESOURCE_SOURCE_DYNAMIC
        ;
    }

    private Type type;

    private Map<String, Object> data;

    public void addData(String key, Object value){
        if(data == null){
            synchronized (this){
                if(data == null){
                    data = new HashMap<>(16);
                }
            }
        }
        data.put(key, value);
    }

}
