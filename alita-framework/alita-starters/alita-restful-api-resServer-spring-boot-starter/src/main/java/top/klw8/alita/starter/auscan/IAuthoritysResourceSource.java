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

import java.util.List;

/**
 * 静态资源权限来源, 实现该接口的来源结果在权限扫描时存入数据库.
 * 2020/5/12 15:38
 */
public interface IAuthoritysResourceSource {

    /**
     * 获取资源权限List
     * 2020/5/12 15:55
     * @param:
     * @return T
     */
    <T extends IAuthoritysResourceSourceItem> List<T> getAuthoritysResourceSourceList();
}
