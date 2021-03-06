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

import io.swagger.annotations.ApiModel;
import lombok.Data;
import top.klw8.alita.config.SysConstants;
import top.klw8.alita.service.base.mongo.common.MongoBaseEntity;
import top.klw8.alita.utils.BeanUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2018/7/3.
 */
/**
 * 分页bean
 * 2018年12月20日 下午2:57:16
 */
@ApiModel(value = "分页bean", description = "分页bean")
@Data
public class EntityByPage<T extends MongoBaseEntity> implements Serializable, Cloneable {
    
    
    private static final long serialVersionUID = -5450108848222011361L;

    private T baseBean;

    /**
     * keywords : 搜索关键字
     */
    private String keywords;
    
    /**
     * keywordsField : 搜索关键字所在字段
     */
    private String keywordsField;
    
    /**
     * page : 页码
     */
    private int page;
    
    /**
     * limit : 每页条数
     */
    private int limit;
    
    /**
     * total : 数据总量
     */
    private long total;
    
    /**
     * pages : 总页数
     */
    private int pages;
    
    /**
     * startPage : 从第几条开始查
     */
    private int startPage;

    public static <T extends MongoBaseEntity> EntityByPage<T> initParam(T baseBean, Integer page, Integer limit,String keywords, String keywordsField){
        EntityByPage<T> entityByPage = new EntityByPage<T>();
        entityByPage.baseBean = baseBean;
        entityByPage.page = page;
        entityByPage.startPage = null ==page?null:(page-1)*limit;
        entityByPage.limit = limit;
        entityByPage.keywords = keywords;
        entityByPage.keywordsField = keywordsField;

        return entityByPage;
    }

    public static <T extends MongoBaseEntity> EntityByPage<T> initParam(T baseBean){
        EntityByPage<T> entityByPage = new EntityByPage<T>();
        entityByPage.baseBean = baseBean;
        entityByPage.page = 1;
        entityByPage.limit = SysConstants.PAGE_LIST_SIZE;
        entityByPage.startPage = (entityByPage.getPage()-1)*entityByPage.getLimit();
        entityByPage.keywords = "";

        return entityByPage;
    }

    public long getStartPage(){
        if(this.getPage() > 0 && this.getLimit() > 0){
            this.startPage = (this.getPage()-1)*this.getLimit();

            return this.startPage;
        }

        return 0;
    }

    public Map<String,Object>createPageData(){
        return this.createPageData(0,new ArrayList<T>(0));
    }

    public Map<String,Object>createPageData(Map<String,Object> map){
        return this.createPageData(0,map);
    }

    private void init() {
        this.setPages((int) (0==this.limit?0:this.total%this.limit==0?this.total/this.limit:(this.total/this.limit)+1));
    }

    public Map<String,Object> createPageData(long total,List<?> list) {
        this.setTotal(total);
        this.init();
        Map<String,Object> map = new HashMap<String,Object>(4);
        List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
        for(Object o : list){
            try {
                Map<String,Object> beanMap = BeanUtil.beanToMap(o);
                dataList.add(beanMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(0 != page && 0 != pages){
            map.put("page",this.getPage());
            map.put("pages",this.getPages());
            map.put("total",this.getTotal());

        }
        map.put("data",dataList);
        return map;
    }

    public Map<String,Object> createPageData(Integer total,Map<?,?> map) {
        Map<String,Object> resultMap = new HashMap<>(4);
        this.setTotal(total);
        this.init();
        resultMap.put("page",this.getPage());
        resultMap.put("pages",this.getPages());
        resultMap.put("total",this.getTotal());
        resultMap.put("data",map);
        return resultMap;
    }

    public static <T extends MongoBaseEntity> Map<String,Object> createPageWithNoData(){
        EntityByPage<T> entityByPage = initParam(null);
        return entityByPage.createPageData(0,new ArrayList<T>());
    }

    public static <T extends MongoBaseEntity> Map<String,Object> createPageWithCustom(Integer page,Integer limit,Integer total,List<?>list){
        EntityByPage<T> entityByPage = initParam(null,page,limit,null, null);
        return entityByPage.createPageData(total,list);
    }
}
