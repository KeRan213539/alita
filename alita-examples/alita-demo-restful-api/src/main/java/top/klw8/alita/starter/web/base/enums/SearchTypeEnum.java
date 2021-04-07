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
package top.klw8.alita.starter.web.base.enums;

/**
 * @ClassName: SearchTypeEnum
 * @Description: 搜索类型 枚举
 * @author klw
 * @date 2019年1月25日 下午6:08:46
 */
public enum SearchTypeEnum {

    /**
     * @author klw
     * @Fields FIELD_LIKE : 指定字段like查询
     */
    FIELD_LIKE,
    
    /**
     * @author klw
     * @Fields TEXT_INDEX : 全文搜索
     */
    TEXT_INDEX;
    
}
