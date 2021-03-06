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
package top.klw8.alita.demo.web.demo.datasecured;

import lombok.Setter;
import top.klw8.alita.entitys.authority.enums.ResourceType;
import top.klw8.alita.starter.auscan.IAuthoritysResourceSourceItem;

/**
 * 资源权限来源中的元素
 * 2020/5/12 16:50
 */
@Setter
public class DemoAuthoritysResourceSourceItem implements IAuthoritysResourceSourceItem {

    private String resource;

    private String resName;

    private ResourceType resType;

    public DemoAuthoritysResourceSourceItem(String resource, String resName, ResourceType resType){
        this.resource = resource;
        this.resName = resName;
        this.resType = resType;
    }

    @Override
    public String getResource() {
        return this.resource;
    }

    @Override
    public String getRemark() {
        return this.resName;
    }

    @Override
    public ResourceType getResType() {
        return this.resType;
    }
}
