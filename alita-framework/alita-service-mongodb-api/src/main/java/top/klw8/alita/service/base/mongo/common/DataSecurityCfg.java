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
package top.klw8.alita.service.base.mongo.common;

/**
 * 数据安全配制相关
 * 2019年1月25日 下午6:43:48
 */
public class DataSecurityCfg {

    /**
     * PAGE_DATA_LIMIT_MAX : 分页时的单页最大数据量上限
     */
    public static final int PAGE_DATA_LIMIT_MAX = 50;
    
    /**
     * PAGE_DATA_PAGE_MAX : 比较大小方式分页时,一次获取多少页的最大限制
     */
    public static final int PAGE_DATA_PAGE_MAX = 20;
    
    
    /**
     * QUERY_LIST_MAX_LIMIT : 列表查询时(不分页查询,数据量少才会使用该方式查询), 获取的最大数据量
     */
    public static final int QUERY_LIST_MAX_LIMIT = 1000;
    
}
