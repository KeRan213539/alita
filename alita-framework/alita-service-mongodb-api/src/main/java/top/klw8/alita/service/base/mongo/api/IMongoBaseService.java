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
package top.klw8.alita.service.base.mongo.api;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

import top.klw8.alita.service.base.mongo.beans.PagePrarmBean;
import top.klw8.alita.service.base.mongo.common.MongoBaseEntity;
import top.klw8.alita.service.base.mongo.dao.prarm.ForPageMode.Mode;

/**
 * service 用的 有各种常用操作
 * 2018年11月15日 下午4:13:37
 */
public interface IMongoBaseService<T extends MongoBaseEntity> {

    /**
     * 新增保存,返回成功保存数据数量
     * @param baseBean 要保存的Bean
     * @return
     */
    T save(T baseBean);

    /**
     * 批量新增保存,返回成功保存数据数量
     * @param list
     * @return
     */
    List<T> batchSave(List<T> list);

    /**
     * 根据ID删除数据,返回成功删除数据数量
     * @param id
     * @return
     */
    Integer deleteById(ObjectId id);

    /**
     * 根据多个ID批量删除数据(英文逗号隔开),返回成功删除数据数量
     * @param ids
     * @return
     */
    Integer deleteByIds(String ids);
    
    /**
     * 根据多个ID批量删除数据,返回成功删除数据数量
     * @param ids
     * @return
     */
    Integer deleteByIds(ObjectId[] ids);

    /**
     * 根据ID查询数据
     * @param id
     * @return
     */
    T findById(ObjectId id, String... excludeFields);
    
    /**
     * 根据ID查询数据
     * @param id
     * @param isQueryRef  是否查询关联数据
     * @return
     */
    T findById(ObjectId id, boolean isQueryRef, String... excludeFields);

    /**
     * 根据多个ID批量查询数据(英文逗号隔开)
     * @param ids
     * @param isQueryRef  是否查询关联数据
     * @return
     */
    List<T> findByIds(String ids, boolean isQueryRef, String... excludeFields);
    
    /**
     * 根据多个ID批量查询数据(英文逗号隔开)
     * @param ids
     * @return
     */
    List<T> findByIds(String ids, String... excludeFields);
    
    /**
     * 根据多个ID批量查询数据
     * @param ids
     * @param isQueryRef  是否查询关联数据
     * @return
     */
    List<T> findByIds(ObjectId[] ids, boolean isQueryRef, String... excludeFields);
    
    /**
     * 根据多个ID批量查询数据
     * @param ids
     * @return
     */
    List<T> findByIds(ObjectId[] ids, String... excludeFields);

    /**
     * 根据ID更新数据
     * @param baseBean
     * @return
     */
    Integer updateById(T baseBean);

    /**
     * 批量更新数据
     * @param list
     * @return
     */
    Integer batchUpdate(List<T> list);

    /**
     * 查询全部数据
     * @param isQueryRef 是否查询关联数据
     * @return
     */
    List<T> findAll(boolean isQueryRef, String... excludeFields);
    
    /**
     * findAll : 查询全部数据
     */
    List<T> findAll(String... excludeFields);

    /**
     * 根据实体查询数据数量(实体有值的才查询)
     * @param baseBean
     * @param keywords  搜索关键字
     * @param keywordsField  搜索字段
     * @return
     */
    long countByEntityForPage(T baseBean, String keywords, String keywordsField);

    /**
     * 根据实体查询数据(实体有值的才查询)
     * @param baseBean
     * @param sortDirection  排序(正序: ASC; 倒序: DESC)
     * @param sortFiled  排序字段
     * @param isQueryRef  是否查询关联数据
     * @param excludeFields 返回结果中排除的字段
     * @return
     */
    List<T> findByEntity(T baseBean, String sortDirection, String sortFiled, boolean isQueryRef, String... excludeFields);
    
    
    /**
     * 根据实体和地理位置查询查询数据(实体有值的才查询), 不加排序条件时默认由近及远排序
     * @param baseBean
     * @param sortDirection  排序(正序: ASC; 倒序: DESC)
     * @param sortFiled  排序字段
     * @param isQueryRef  是否查询关联数据
     * @param pointFieldName  存放坐标的字段名
     * @param longitude  经度
     * @param latitude  纬度
     * @param rangeKm  圆形半径(KM)
     * @param excludeFields 返回结果中排除的字段
     * @return
     */
    List<T> findByEntityWithGeo(T baseBean, String sortDirection, String sortFiled, boolean isQueryRef, String pointFieldName, double longitude, double latitude, double rangeKm, String... excludeFields);
    
    /**
     * 根据实体和全文搜索查询查询数据(实体有值的才查询)
     * @param baseBean
     * @param sortDirection  排序(正序: ASC; 倒序: DESC)
     * @param sortFiled  排序字段
     * @param isQueryRef  是否查询关联数据
     * @param matchingText  全文搜索关键字
     * @param excludeFields  返回结果中排除的字段
     * @return
     */
    List<T> findByEntityWithTextMatching(T baseBean, String sortDirection, String sortFiled, boolean isQueryRef, String matchingText, String... excludeFields);

    /**
     * 根据实体分页查询数据(实体有值的才查询)
     * @param baseBean
     * @param pagePrarmBean  分页参数
     * @param isQueryRef 是否查询关联数据
     * @param forPageMode  分页方式,如果使用 COMPARATIVE_UNIQUE_FIELD 方式,那么 forPageMode中的 fieldName 和 fieldValue 必有值
     * @param excludeFields  返回结果中排除的字段
     * @return
     */
    Page<T> queryForPage(T baseBean, PagePrarmBean pagePrarmBean, boolean isQueryRef, Mode forPageMode, String... excludeFields);
    
    /**
     * 根据实体和地理位置信息分页查询数据(实体有值的才查询), 不加排序条件时默认由近及远排序
     * @param baseBean
     * @param pagePrarmBean  分页参数
     * @param isQueryRef 是否查询关联数据
     * @param forPageMode  分页方式,如果使用 COMPARATIVE_UNIQUE_FIELD 方式,那么 forPageMode中的 fieldName 和 fieldValue 必有值
     * @param pointFieldName  存放坐标的字段名
     * @param longitude  经度
     * @param latitude  纬度
     * @param rangeKm  圆形半径(KM)
     * @param excludeFields  返回结果中排除的字段
     * @return
     */
    Page<T> queryForPageWithGeo(T baseBean, PagePrarmBean pagePrarmBean, boolean isQueryRef, Mode forPageMode, String pointFieldName, double longitude, double latitude, double rangeKm, String... excludeFields);
    
    /**
     * 根据实体和全文搜索关键字分页查询数据(实体有值的才查询)
     * @param baseBean
     * @param pagePrarmBean  分页参数
     * @param isQueryRef 是否查询关联数据
     * @param forPageMode  分页方式,如果使用 COMPARATIVE_UNIQUE_FIELD 方式,那么 forPageMode中的 fieldName 和 fieldValue 必有值
     * @param matchingText  全文搜索关键字
     * @param excludeFields  返回结果中排除的字段
     * @return
     */
    Page<T> queryForPageWithTextMatching(T baseBean, PagePrarmBean pagePrarmBean, boolean isQueryRef, Mode forPageMode, String matchingText, String... excludeFields);
    

}