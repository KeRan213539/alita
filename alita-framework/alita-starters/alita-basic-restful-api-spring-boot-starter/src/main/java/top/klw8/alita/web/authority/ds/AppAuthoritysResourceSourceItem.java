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
package top.klw8.alita.web.authority.ds;

import top.klw8.alita.entitys.authority.AlitaAuthoritysApp;
import top.klw8.alita.entitys.authority.enums.ResourceType;
import top.klw8.alita.starter.auscan.IAuthoritysResourceSourceItem;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AppDataSecuredSourceItem
 * @Description: 数据权限来源中的 APP 元素
 * @date 2020/5/12 16:50
 */
public class AppAuthoritysResourceSourceItem implements IAuthoritysResourceSourceItem {

    private String appTag;

    private String appName;

    private ResourceType resType;

    public AppAuthoritysResourceSourceItem(AlitaAuthoritysApp app, ResourceType resType){
        this.appTag = app.getAppTag();
        this.appName = app.getAppName();
        this.resType = resType;
    }

    @Override
    public String getResource() {
        return this.appTag;
    }

    @Override
    public String getRemark() {
        return this.appName;
    }

    @Override
    public ResourceType getResType() {
        return this.resType;
    }


}
