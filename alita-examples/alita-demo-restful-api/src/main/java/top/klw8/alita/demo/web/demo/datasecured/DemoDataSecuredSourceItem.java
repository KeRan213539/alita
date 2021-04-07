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
package top.klw8.alita.demo.web.demo.datasecured;

import lombok.Setter;
import top.klw8.alita.starter.auscan.IDataSecuredSourceItem;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DemoDataSecuredSourceItem
 * @Description: 数据权限来源中的元素
 * @date 2020/5/12 16:50
 */
@Setter
public class DemoDataSecuredSourceItem implements IDataSecuredSourceItem {

    private String resource;

    private String resName;

    public DemoDataSecuredSourceItem(String resource, String resName){
        this.resource = resource;
        this.resName = resName;
    }

    @Override
    public String getResource() {
        return this.resource;
    }

    @Override
    public String getRemark() {
        return this.resName;
    }
}
