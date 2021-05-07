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
package top.klw8.alita.starter.auscan;

import top.klw8.alita.entitys.authority.enums.ResourceType;

/**
 * 资源权限来源元素
 * 2020/5/12 15:47
 */
public interface IAuthoritysResourceSourceItem {

    /**
     * 获取资源标识
     */
    String getResource();

    /**
     * 获取资源 备注/名称
     */
    String getRemark();

    /**
     * 获取资源类型.
     * @param
     * @return top.klw8.alita.entitys.authority.enums.ResourceType
     */
    ResourceType getResType();

}
