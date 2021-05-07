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
package top.klw8.alita.service.base.mongo.beans;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页参数Bean
 * 2018年10月10日 下午1:43:34
 */
@Getter
@Setter
public class PagePrarmBean implements Serializable, Cloneable {

    private static final long serialVersionUID = -2029324365320879551L;

    /**
     * page : 页码(比较方式时该值无用,原样返回)
     */
    private Integer page; 
    
    /**
     * limit : 每页数据量(默认10)
     */
    private Integer limit = 10;
    
    /**
     * keywords : 搜索关键字
     */
    private String keywords;
    
    /**
     * keywordsField : 搜索关键字所在字段
     */
    private String keywordsField;
    
    /**
     * sortDirection : 排序(正序: ASC; 倒序: DESC)
     */
    private String sortDirection;
    
    /**
     * sortFiled : 排序字段
     */
    private String sortFiled;
    
    public static PagePrarmBean create(String keywords, String keywordsField, Integer limit, Integer page, String sortDirection, String sortFiled) {
	PagePrarmBean pagePrarmBean = new PagePrarmBean();
	pagePrarmBean.setKeywords(keywords);
	pagePrarmBean.setKeywordsField(keywordsField);
	pagePrarmBean.setLimit(limit);
	pagePrarmBean.setPage(page);
	pagePrarmBean.setSortDirection(sortDirection);
	pagePrarmBean.setSortFiled(sortFiled);
	return pagePrarmBean;
    }
    
}
