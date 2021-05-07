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
package top.klw8.alita.starter.service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import top.klw8.alita.service.base.mongo.api.IMongoBaseService;
import top.klw8.alita.service.base.mongo.beans.EntityByPage;
import top.klw8.alita.service.base.mongo.beans.PagePrarmBean;
import top.klw8.alita.service.base.mongo.common.MongoBaseEntity;
import top.klw8.alita.service.base.mongo.dao.IMongoBaseDao;
import top.klw8.alita.service.base.mongo.dao.prarm.ForPageMode.Mode;
import top.klw8.alita.utils.ValidateUtil;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.geo.Point;
import org.springframework.util.Assert;

import java.util.List;

/**
 * service 用的 有各种常用操作
 * 2018年10月9日 下午1:23:40
 */
@Slf4j
public class BaseServiceImpl<T extends MongoBaseEntity> implements IMongoBaseService<T> {

    private IMongoBaseDao<T> dao;

    public BaseServiceImpl(IMongoBaseDao<T> dao) {
        this.dao = dao;
    }

    @SuppressWarnings("unchecked")
    protected <MonoData extends Object, ResultData extends Object> ResultData asyncSendData(Mono<MonoData> mono) {
        final AsyncContext asyncContext = RpcContext.startAsync();
//	mono.log().checkpoint().subscribe(obj -> {
        mono.defaultIfEmpty((MonoData) new Integer(-1)).subscribe(obj -> {
            asyncContext.signalContextSwitch();
            if (obj instanceof Integer) {
                if (((Integer) obj).intValue() == -1) {
                    asyncContext.write(null);
                    return;
                }
            }
            asyncContext.write(obj);
        }, error -> {
            error.printStackTrace();
        });
        return null;
    }

    @Override
    public T save(T baseBean) {
        if (baseBean == null) {
            return null;
        }
        return asyncSendData(dao.save(baseBean));
    }

    @Override
    public List<T> batchSave(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return asyncSendData(dao.batchSave(list));
    }

    @Override
    public Integer deleteById(ObjectId id) {
        if (id == null) {
            return null;
        }
        return asyncSendData(dao.deleteById(id));
    }

    @Override
    public Integer deleteByIds(String ids) {
        if (StringUtils.isBlank(ids)) {
            return null;
        }
        return asyncSendData(dao.deleteByIds(convertIdsString2PKArray(ids)));
    }

    @Override
    public Integer deleteByIds(ObjectId[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return null;
        }
        return asyncSendData(dao.deleteByIds(ids));
    }

    @Override
    public T findById(ObjectId id, String... excludeFields) {
        return this.findById(id, false, excludeFields);
    }

    @Override
    public T findById(ObjectId id, boolean isQueryRef, String... excludeFields) {
        if (id == null) {
            return null;
        }
        if (isQueryRef) {
            return asyncSendData(dao.findByIdWithRefQuery(id, excludeFields));
        }
        return asyncSendData(dao.findById(id, excludeFields));
    }

    @Override
    public List<T> findByIds(String ids, boolean isQueryRef, String... excludeFields) {
        if (StringUtils.isBlank(ids)) {
            return null;
        }
        if (isQueryRef) {
            return asyncSendData(dao.findByIdsWithRefQuery(convertIdsString2PKArray(ids), excludeFields));
        }
        return asyncSendData(dao.findByIds(convertIdsString2PKArray(ids), excludeFields));
    }

    @Override
    public List<T> findByIds(String ids, String... excludeFields) {
        return findByIds(ids, false, excludeFields);
    }

    @Override
    public List<T> findByIds(ObjectId[] ids, boolean isQueryRef, String... excludeFields) {
        if (ArrayUtils.isEmpty(ids)) {
            return null;
        }
        if (isQueryRef) {
            return asyncSendData(dao.findByIdsWithRefQuery(ids, excludeFields));
        }
        return asyncSendData(dao.findByIds(ids, excludeFields));
    }

    @Override
    public List<T> findByIds(ObjectId[] ids, String... excludeFields) {
        return findByIds(ids, false, excludeFields);
    }

    @Override
    public Integer updateById(T baseBean) {
        if (baseBean == null) {
            return null;
        }
        return asyncSendData(dao.updateById(baseBean));
    }

    @Override
    public Integer batchUpdate(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return asyncSendData(dao.batchUpdate(list));
    }

    @Override
    public List<T> findAll(boolean isQueryRef, String... excludeFields) {
        if (isQueryRef) {
            return asyncSendData(dao.findAllWithRefQuery(excludeFields));
        }
        return asyncSendData(dao.findAll(excludeFields));
    }

    @Override
    public List<T> findAll(String... excludeFields) {
        return findAll(false, excludeFields);
    }

    @Override
    public long countByEntityForPage(T baseBean, String keywords, String keywordsField) {
        if ((StringUtils.isNotBlank(keywords) && StringUtils.isBlank(keywordsField)) || (StringUtils.isBlank(keywords) && StringUtils.isNotBlank(keywordsField))) {
            throw new IllegalArgumentException("搜索关键字和搜索字段要不都没值要不都有值,不能一个有值一个没值");
        }
        EntityByPage<T> entityByPage = EntityByPage.initParam(baseBean, 0, 0, keywords, keywordsField);
        return asyncSendData(dao.countByEntityForPage(entityByPage));
    }

    @Override
    public List<T> findByEntity(T baseBean, String sortDirection, String sortFiled, boolean isQueryRef, String... excludeFields) {
        Sort sort = processFindByEntity(sortDirection, sortFiled);
        if (isQueryRef) {
            return asyncSendData(dao.findByEntityWithRefQuery(baseBean, sort, excludeFields));
        }
        return asyncSendData(dao.findByEntity(baseBean, sort, excludeFields));
    }

    @Override
    public List<T> findByEntityWithGeo(T baseBean, String sortDirection, String sortFiled,
                                       boolean isQueryRef, String pointFieldName, double longitude, double latitude,
                                       double rangeKm, String... excludeFields) {
        Sort sort = processFindByEntity(sortDirection, sortFiled);
        Point point = null;
        if (longitude > 0D && latitude > 0D) {
            if (!ValidateUtil.checkLatLngRange(latitude, longitude)) {
                throw new IllegalArgumentException("经纬度超过范围(经度: -90~90, 纬度: -180~180)");
            }
            point = new Point(latitude, longitude);
        }
        if (isQueryRef) {
            return asyncSendData(dao.findByEntityWithRefQuery(baseBean, sort, pointFieldName, point, rangeKm, excludeFields));
        }
        return asyncSendData(dao.findByEntity(baseBean, sort, pointFieldName, point, rangeKm, excludeFields));
    }

    @Override
    public List<T> findByEntityWithTextMatching(T baseBean, String sortDirection, String sortFiled,
                                                boolean isQueryRef, String matchingText, String... excludeFields) {
        Sort sort = processFindByEntity(sortDirection, sortFiled);
        if (isQueryRef) {
            return asyncSendData(dao.findByEntityWithRefQuery(baseBean, sort, matchingText, excludeFields));
        }
        return asyncSendData(dao.findByEntity(baseBean, sort, matchingText, excludeFields));
    }

    private Sort processFindByEntity(String sortDirection, String sortFiled) {
//	if (StringUtils.isBlank(sortDirection)) {
//	    sortDirection = "DESC";
//	}
//	if (StringUtils.isBlank(sortFiled)) {
//	    sortFiled = MongoDBConstant.ID_KEY;
//	}
        if (StringUtils.isBlank(sortDirection) || StringUtils.isBlank(sortFiled)) {
            return null;
        }
        Direction direction = sortDirection.equals("ASC") ? Direction.ASC : Direction.DESC;
        return Sort.by(direction, sortFiled);
    }

    @Override
    public Page<T> queryForPage(T baseBean, PagePrarmBean pagePrarmBean, boolean isQueryRef, Mode forPageMode, String... excludeFields) {
        Sort sort = null;
        EntityByPage<T> entityByPage = processQueryForPage(baseBean, pagePrarmBean, sort);
        if (isQueryRef) {
            return asyncSendData(dao.searchByEntityForPageWithRefQuery(entityByPage, sort, forPageMode, excludeFields));
        }
        return asyncSendData(dao.searchByEntityForPage(entityByPage, sort, forPageMode, excludeFields));
    }

    @Override
    public Page<T> queryForPageWithGeo(T baseBean, PagePrarmBean pagePrarmBean, boolean isQueryRef,
                                       Mode forPageMode, String pointFieldName, double longitude, double latitude,
                                       double rangeKm, String... excludeFields) {
        Sort sort = null;
        Point point = null;
        if (longitude > 0D && latitude > 0D) {
            if (!ValidateUtil.checkLatLngRange(latitude, longitude)) {
                throw new IllegalArgumentException("经纬度超过范围(经度: -90~90, 纬度: -180~180)");
            }
            point = new Point(latitude, longitude);
        }
        EntityByPage<T> entityByPage = processQueryForPage(baseBean, pagePrarmBean, sort);
        if (isQueryRef) {
            return asyncSendData(dao.searchByEntityForPageWithRefQuery(entityByPage, sort, pointFieldName, point, rangeKm, forPageMode, excludeFields));
        }
        return asyncSendData(dao.searchByEntityForPage(entityByPage, sort, pointFieldName, point, rangeKm, forPageMode, excludeFields));
    }

    @Override
    public Page<T> queryForPageWithTextMatching(T baseBean, PagePrarmBean pagePrarmBean,
                                                boolean isQueryRef, Mode forPageMode, String matchingText, String... excludeFields) {
        Sort sort = null;
        EntityByPage<T> entityByPage = processQueryForPage(baseBean, pagePrarmBean, sort);
        if (isQueryRef) {
            return asyncSendData(dao.searchByEntityForPageWithRefQuery(entityByPage, sort, matchingText, forPageMode, excludeFields));
        }
        return asyncSendData(dao.searchByEntityForPage(entityByPage, sort, matchingText, forPageMode, excludeFields));
    }

    private EntityByPage<T> processQueryForPage(T baseBean, PagePrarmBean pagePrarmBean, Sort sort) {
        Assert.notNull(pagePrarmBean, "分页参数不能为空");
        Assert.notNull(pagePrarmBean.getPage(), "页码不能为空");


        if (pagePrarmBean.getLimit() == null) {
            pagePrarmBean.setLimit(10);
        }
        if (StringUtils.isNotBlank(pagePrarmBean.getSortDirection()) && StringUtils.isNotBlank(pagePrarmBean.getSortFiled())) {
            Direction direction = pagePrarmBean.getSortDirection().equals("ASC") ? Direction.ASC : Direction.DESC;
            sort = Sort.by(direction, pagePrarmBean.getSortFiled());
        }

        return EntityByPage.initParam(baseBean, pagePrarmBean.getPage(), pagePrarmBean.getLimit(), pagePrarmBean.getKeywords(), pagePrarmBean.getKeywordsField());
    }


    /**
     * @param ids
     * @return
     * 将使用逗号(, )隔开的多个ID的字符串转换为PK类型的数组
     */
    private ObjectId[] convertIdsString2PKArray(String ids) {
        Assert.hasText(ids, "ids不能为空!");
        String[] strIds = ids.split(",");
        Assert.notEmpty(strIds, "ids不能为空!");
        ObjectId[] objectIds = new ObjectId[strIds.length];
        for (int i = 0; i < strIds.length; i++) {
            String strId = strIds[i];
            objectIds[i] = new ObjectId(strId);
        }
        return objectIds;
    }

}
