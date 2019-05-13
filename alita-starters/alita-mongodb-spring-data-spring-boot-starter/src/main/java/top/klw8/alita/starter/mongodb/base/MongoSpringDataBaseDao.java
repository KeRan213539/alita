package top.klw8.alita.starter.mongodb.base;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.klw8.alita.base.mongodb.MongoDBConstant;
import top.klw8.alita.base.mongodb.annotations.NotPersistence;
import top.klw8.alita.base.mongodb.annotations.QueryLikeField;
import top.klw8.alita.service.base.beans.EntityByPage;
import top.klw8.alita.service.base.beans.PageImpl;
import top.klw8.alita.service.base.dao.IMongoBasePlusDao;
import top.klw8.alita.service.base.dao.prarm.ForPageMode;
import top.klw8.alita.service.base.dao.prarm.ForPageMode.ComparisonMode;
import top.klw8.alita.service.base.dao.prarm.ForPageMode.Mode;
import top.klw8.alita.service.base.entitys.BaseEntity;
import top.klw8.alita.service.base.entitys.ITextIndexedCustomSupport;
import top.klw8.alita.service.base.entitys.ITextIndexedSupport;
import top.klw8.alita.service.common.DataSecurityCfg;
import top.klw8.alita.utils.AnalyzerUtil;

/**
 * @author klw
 * @ClassName: MongoSpringDataBaseDao
 * @Description: 基于spring-data-mongodb的BaseDao
 * @date 2018年12月21日 16:51:06
 */
public class MongoSpringDataBaseDao<T extends BaseEntity> implements IMongoBasePlusDao<T> {

    private static Logger log = LoggerFactory.getLogger(MongoSpringDataBaseDao.class);

    private Class<T> entityClazz;

    private String collectionName;

    private ReactiveMongoTemplate mongoTemplate;

    private MongoTemplate notReactiveMongoTemplate;

    @SuppressWarnings("unchecked")
    @Autowired
    public void initDao(ReactiveMongoTemplate mongoTemplate, MongoTemplate notReactiveMongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.notReactiveMongoTemplate = notReactiveMongoTemplate;

        Type superClassType = this.getClass().getGenericSuperclass();
        Type firstArgument;

        if (superClassType instanceof ParameterizedType) {
            firstArgument = ((ParameterizedType) superClassType).getActualTypeArguments()[0];
        } else if (superClassType instanceof Class) {
            firstArgument = ((ParameterizedType) ((Class<?>) superClassType).getGenericSuperclass()).getActualTypeArguments()[0];
        } else {
            log.error("Can not handle type construction for '" + this.getClass().getName() + "'!");
            throw new RuntimeException("Can not handle type construction for '" + this.getClass().getName() + "'!");
        }

        if (firstArgument instanceof Class) {
            this.entityClazz = (Class<T>) firstArgument;
        } else if (firstArgument instanceof ParameterizedType) {
            this.entityClazz = (Class<T>) ((ParameterizedType) firstArgument).getRawType();
        } else {
            log.error("Problem dtermining generic class for '" + this.getClass().getName() + "'! ");
            throw new RuntimeException("Problem dtermining generic class for '" + this.getClass().getName() + "'! ");
        }

        // 获取 collectionName
        org.springframework.data.mongodb.core.mapping.Document document = this.entityClazz.getAnnotation(org.springframework.data.mongodb.core.mapping.Document.class);
        Assert.notNull(document, "实体必须要有 @Document 注解!");
        this.collectionName = document.collection();
        Assert.hasText(this.collectionName, "实体的 @Document 注解必须设置 collection!!");
    }

    @Override
    public ReactiveMongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public MongoTemplate getNotReactiveMongoTemplate() {
        return notReactiveMongoTemplate;
    }

    @Override
    public Class<T> getEntityClazz() {
        return entityClazz;
    }

    @Override
    public Query createQuery() {
        return new Query();
    }

    @Override
    public Update createUpdateOperations() {
        return new Update();
    }

    @Override
    public Mono<T> save(T baseBean) {
        Assert.notNull(baseBean, "要保存的bean不能为空");
        List<T> list = new ArrayList<>(1);
        list.add(baseBean);
        processNotPersistence4Save(list);
        processTextIndexField(baseBean);
        return mongoTemplate.save(baseBean);
    }

    @Override
    public Mono<List<T>> batchSave(List<T> list) {
        Assert.notEmpty(list, "要新增的集合不能为空");
        processNotPersistence4Save(list);
        processTextIndexField(list);
        return mongoTemplate.insertAll(list).collectList();
    }


    @Override
    public Mono<Long> deleteById(ObjectId id) {
        Assert.notNull(id, "id不能为空");
        return mongoTemplate.remove(Query.query(Criteria.where(MongoDBConstant.ID_KEY).is(id)), this.entityClazz).map(result -> result.getDeletedCount());
    }

    @Override
    public Mono<List<T>> deleteByIds(ObjectId[] ids) {
        Assert.notEmpty(ids, "ids不能为空");
        return mongoTemplate.findAllAndRemove(Query.query(Criteria.where(MongoDBConstant.ID_KEY).in(Arrays.asList(ids))), this.entityClazz).collectList();
    }

    @Override
    public Mono<List<T>> findByIds(ObjectId[] ids, String... excludeFields) {
        Assert.notNull(ids, "ids不能为空");
        Query query = Query.query(Criteria.where(MongoDBConstant.ID_KEY).in((Object[]) ids));
        excludeDBRefFields(query, excludeFields);
        return mongoTemplate.find(query, this.entityClazz).collectList();
    }

    @Override
    public Mono<T> findById(ObjectId id, String... excludeFields) {
        Assert.notNull(id, "id不能为空");
        Query query = Query.query(Criteria.where(MongoDBConstant.ID_KEY).is(String.valueOf(id)));
        excludeDBRefFields(query, excludeFields);
        Flux<T> rList = mongoTemplate.find(query, this.entityClazz);
        return rList.collectList().flatMap(list -> {
            if (CollectionUtils.isEmpty(list)) {
                return Mono.empty();
            }
            return Mono.just(list.get(0));
        });

    }

    @Override
    public Mono<Long> updateById(T baseBean) {
        Assert.notNull(baseBean, "要保存的bean不能为空");
        Assert.notNull(baseBean.getId(), "要保存的bean的id不能为空");
        processTextIndexField(baseBean);
        return mongoTemplate
                .updateFirst(
                        Query.query(Criteria.where(MongoDBConstant.ID_KEY).is(baseBean.getId())),
                        this.generateUpdateByEntity(baseBean), this.entityClazz)
                .map(result -> result.getModifiedCount());
    }

    @Override
    public Mono<Integer> batchUpdate(List<T> list) {
        Assert.notEmpty(list, "要更新的集合不能为空");
        processTextIndexField(list);
        Document command = new Document();
        command.put("update", collectionName);
        List<Document> updateList = new ArrayList<>();

        list.stream().forEach(item -> {
            Assert.notNull(item.getId(), "批量更新的元素必须有ID!");
            Document update = new Document();
            update.put("q", Query.query(Criteria.where(MongoDBConstant.ID_KEY).is(item.getId())).getQueryObject());
            update.put("u", this.generateUpdateByEntity(item).getUpdateObject());
            update.put("upsert", false);  // 如果没有条件匹配的文档,是否执行插入操作
            update.put("multi", false);  // 是否更新符合条件的多个文档, false 只更新一条,这里的更新是根据唯一主键来更新的,所以这里设置成fasle
            updateList.add(update);
        });
        command.put("updates", updateList);
        command.put("ordered", true); // 某条失败时是否继续

        // 返回的内容参考 https://docs.mongodb.com/manual/reference/command/update/#update-command-output
        return mongoTemplate.executeCommand(command).map(document -> document.getInteger("nModified"));
    }

    @Override
    public Mono<List<T>> findAll(String... excludeFields) {
        Query query = new Query();
        excludeDBRefFields(query, excludeFields);
        return mongoTemplate.find(query, this.entityClazz).collectList();
    }

    @Override
    public Mono<List<T>> findByEntity(T baseBean, Sort sort, String... excludeFields) {
        return findByEntity(baseBean, sort, null, null, 0D, null, true, excludeFields);
    }

    @Override
    public Mono<List<T>> findByEntity(T baseBean, Sort sort, String pointFieldName, Point point, double rangeKm, String... excludeFields) {
        return findByEntity(baseBean, sort, pointFieldName, point, rangeKm, null, true, excludeFields);
    }

    @Override
    public Mono<List<T>> findByEntity(T baseBean, Sort sort, String matchingText, String... excludeFields) {
        return findByEntity(baseBean, sort, null, null, 0D, matchingText, true, excludeFields);
    }

    /**
     * @param baseBean       查询条件
     * @param sort           排序
     * @param pointFieldName 存放位置的字段名
     * @param point          地理位置查询的点
     * @param rangeKm        地理位置查询的范围半径(KM)
     * @param matchingText   全文搜索的关键字
     * @param isExcludeRef   是否排除关联查询字段
     * @param excludeFields  返回结果中排除的字段
     * @return
     * @Title: findByEntity
     * @author klw
     * @Description: 根据实体查询(地理位置和全文搜索不能一起查)
     */
    private Mono<List<T>> findByEntity(T baseBean, Sort sort, String pointFieldName, Point point, double rangeKm, String matchingText, boolean isExcludeRef, String... excludeFields) {
        if (StringUtils.isNotBlank(pointFieldName) && StringUtils.isNotBlank(matchingText)) {
            throw new IllegalArgumentException("地理位置和全文搜索不能一起查询");
        }
        Query query = this.generateQueryByEntity(baseBean, isExcludeRef, excludeFields);
        if (StringUtils.isNotBlank(pointFieldName)) {
            if (point == null || rangeKm <= 0D) {
                throw new IllegalArgumentException("地理位置查询的参数不全");
            }
            // 地理位置查询
            query.addCriteria(Criteria.where(pointFieldName).nearSphere(point).maxDistance(rangeKm / KM2RADIAN));
        } else if (StringUtils.isNotBlank(matchingText)) {
            // 全文搜索

            query.addCriteria(new TextCriteria().matchingAny(AnalyzerUtil.strAnalyzerReturnsWithSpaceNoSmart(matchingText)));
        }
        if (sort != null) {
            query.with(sort);
        }
        // 只有数据量小的数据才会调这个接口,出于安全考虑,这里限制返回最大数据量
        query.limit(DataSecurityCfg.QUERY_LIST_MAX_LIMIT);
        return Mono.just(notReactiveMongoTemplate.find(query, this.entityClazz));
    }

    @Override
    public Mono<Long> countByEntityForPage(EntityByPage<T> entityByPage) {
        Query query = this.generateQueryByEntity(entityByPage.getBaseBean(), true, null);
        if (StringUtils.isNotBlank(entityByPage.getKeywordsField()) && StringUtils.isNotBlank(entityByPage.getKeywords())) {
            // 模糊匹配
            String patternStr = "^.*" + entityByPage.getKeywords() + ".*$";
            Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where(entityByPage.getKeywordsField()).regex(pattern));
        }
        return mongoTemplate.count(query, this.entityClazz);
    }

    @Override
    public Mono<Page<T>> searchByEntityForPage(EntityByPage<T> entityByPage, Sort sort, Mode forPageMode, String... excludeFields) {
        return searchByEntityForPage(entityByPage, sort, null, null, 0D, null, forPageMode, true, excludeFields);
    }

    @Override
    public Mono<Page<T>> searchByEntityForPage(EntityByPage<T> entityByPage, Sort sort, String pointFieldName, Point point, double rangeKm, Mode forPageMode, String... excludeFields) {
        return searchByEntityForPage(entityByPage, sort, pointFieldName, point, rangeKm, null, forPageMode, true, excludeFields);
    }

    @Override
    public Mono<Page<T>> searchByEntityForPage(EntityByPage<T> entityByPage, Sort sort, String matchingText, Mode forPageMode, String... excludeFields) {
        return searchByEntityForPage(entityByPage, sort, null, null, 0D, matchingText, forPageMode, true, excludeFields);
    }

    /**
     * @param entityByPage   查询条件
     * @param sort           排序
     * @param pointFieldName 存放位置的字段名
     * @param point          地理位置查询的点
     * @param rangeKm        地理位置查询的范围半径(KM)
     * @param matchingText   全文搜索的关键字
     * @param forPageMode    分页方式,如果使用 COMPARATIVE_UNIQUE_FIELD 方式,那么 forPageMode中的 fieldName 和 fieldValue 必有值
     * @param isExcludeRef   是否排除关联查询字段
     * @param excludeFields  查询结果数据中不需要的字段
     * @return
     * @Title: searchByEntityForPage
     * @author klw
     * @Description: 根据对象查询数据并分页
     */
    private Mono<Page<T>> searchByEntityForPage(EntityByPage<T> entityByPage, Sort sort, String pointFieldName, Point point, double rangeKm, String matchingText, Mode forPageMode, boolean isExcludeRef, String... excludeFields) {

        if (entityByPage.getLimit() > DataSecurityCfg.PAGE_DATA_LIMIT_MAX) {
            throw new IllegalArgumentException("每页数据量不能大于50条");
        }
        if (forPageMode.getDataPage() > DataSecurityCfg.PAGE_DATA_PAGE_MAX) {
            throw new IllegalArgumentException("比较大小方式分页时,一次获取数据不能多于20页");
        }

        if (StringUtils.isNotBlank(pointFieldName) && StringUtils.isNotBlank(matchingText)) {
            throw new IllegalArgumentException("地理位置和全文搜索不能一起查询");
        }

        Query query = this.generateQueryByEntity(entityByPage.getBaseBean(), isExcludeRef, excludeFields);
        if (StringUtils.isNotBlank(entityByPage.getKeywordsField()) && StringUtils.isNotBlank(entityByPage.getKeywords())) {
            // 模糊匹配
            String patternStr = "^.*" + entityByPage.getKeywords() + ".*$";
            Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where(entityByPage.getKeywordsField()).regex(pattern));
        }

        Pageable pageable = null;
        if (sort == null) {
            pageable = PageRequest.of(processPage4Mongo(entityByPage.getPage()), entityByPage.getLimit());
        } else {
            pageable = PageRequest.of(processPage4Mongo(entityByPage.getPage()), entityByPage.getLimit(), sort);
        }

        if (StringUtils.isNotBlank(pointFieldName)) {
            if (point == null || rangeKm <= 0D) {
                throw new IllegalArgumentException("地理位置查询的参数不全");
            }
            // 地理位置查询
            query.addCriteria(Criteria.where(pointFieldName).nearSphere(point).maxDistance(rangeKm / KM2RADIAN));
        } else if (StringUtils.isNotBlank(matchingText)) {
            // 全文搜索
            query.addCriteria(new TextCriteria().matchingAny(AnalyzerUtil.strAnalyzerReturnsWithSpaceNoSmart(matchingText)));
        }

        if (forPageMode.name().equals(ForPageMode.MODE_NAME_COMPARATIVE_UNIQUE_FIELD)) {
            if (StringUtils.isNotBlank(forPageMode.getFieldName()) && StringUtils.isNotBlank(forPageMode.getFieldValue())) {
                if (forPageMode.getComparisonMode() == ComparisonMode.LESS_THAN) {
                    query.addCriteria(Criteria.where(forPageMode.getFieldName()).lt(forPageMode.getRealFieldValue()));
                } else {
                    query.addCriteria(Criteria.where(forPageMode.getFieldName()).gt(forPageMode.getRealFieldValue()));
                }
                if (isExcludeRef) {
                    Mono<Long> countMono = mongoTemplate.count(query, this.entityClazz);
                    query.with(pageable);
                    query.limit((pageable.getPageSize() * forPageMode.getDataPage()));
                    Mono<List<T>> contentMono = mongoTemplate.find(query, this.entityClazz).collectList();
                    return countMono.zipWith(contentMono, (count, content) -> {
                        return new PageImpl<T>(content, count);
                    });
                } else {
                    long count = notReactiveMongoTemplate.count(query, this.entityClazz);
                    query.with(pageable);
                    query.limit((pageable.getPageSize() * forPageMode.getDataPage()));
                    List<T> content = notReactiveMongoTemplate.find(query, this.entityClazz);
                    return Mono.just(new PageImpl<T>(content, count));
                }
            }
        }
        if (isExcludeRef) {
            Mono<Long> countMono = mongoTemplate.count(query, this.entityClazz);
            query.with(pageable);
            Mono<List<T>> contentMono = mongoTemplate.find(query, this.entityClazz).collectList();
            return countMono.zipWith(contentMono, (count, content) -> {
                return new PageImpl<T>(content, count);
            });
        } else {
            long count = notReactiveMongoTemplate.count(query, this.entityClazz);
            query.with(pageable);
            List<T> content = notReactiveMongoTemplate.find(query, this.entityClazz);
            return Mono.just(new PageImpl<T>(content, count));
        }
    }


    @Override
    public Mono<List<T>> findByIdsWithRefQuery(ObjectId[] ids, String... excludeFields) {
        Assert.notEmpty(ids, "ids不能为空");
        Query query = Query.query(Criteria.where(MongoDBConstant.ID_KEY).in((Object[]) ids));
        excludeFields(query, excludeFields);
        List<T> rList = notReactiveMongoTemplate.find(query, this.entityClazz);
        if (CollectionUtils.isEmpty(rList)) {
            return Mono.empty();
        }
        return Mono.just(rList);
    }

    @Override
    public Mono<T> findByIdWithRefQuery(ObjectId id, String... excludeFields) {
        Assert.notNull(id, "id不能为空");
        Query query = Query.query(Criteria.where(MongoDBConstant.ID_KEY).is(id));
        excludeFields(query, excludeFields);
        List<T> rList = notReactiveMongoTemplate.find(query, this.entityClazz);
        if (CollectionUtils.isEmpty(rList)) {
            return Mono.empty();
        }
        return Mono.just(rList.get(0));
    }

    @Override
    public Mono<List<T>> findAllWithRefQuery(String... excludeFields) {
        Query query = new Query();
        excludeFields(query, excludeFields);
        return Mono.just(notReactiveMongoTemplate.find(query, this.entityClazz));
    }

    @Override
    public Mono<List<T>> findByEntityWithRefQuery(T baseBean, Sort sort, String... excludeFields) {
        return findByEntity(baseBean, sort, null, null, 0D, null, false, excludeFields);
    }

    @Override
    public Mono<List<T>> findByEntityWithRefQuery(T baseBean, Sort sort, String pointFieldName, Point point, double rangeKm, String... excludeFields) {
        return findByEntity(baseBean, sort, pointFieldName, point, rangeKm, null, false, excludeFields);
    }

    @Override
    public Mono<List<T>> findByEntityWithRefQuery(T baseBean, Sort sort, String matchingText, String... excludeFields) {
        return findByEntity(baseBean, sort, null, null, 0D, matchingText, false, excludeFields);
    }

    @Override
    public Mono<Page<T>> searchByEntityForPageWithRefQuery(EntityByPage<T> entityByPage, Sort sort, Mode forPageMode, String... excludeFields) {
        return searchByEntityForPage(entityByPage, sort, null, null, 0D, null, forPageMode, false, excludeFields);
    }

    @Override
    public Mono<Page<T>> searchByEntityForPageWithRefQuery(EntityByPage<T> entityByPage, Sort sort, String pointFieldName, Point point, double rangeKm, Mode forPageMode, String... excludeFields) {
        return searchByEntityForPage(entityByPage, sort, pointFieldName, point, rangeKm, null, forPageMode, false, excludeFields);
    }

    @Override
    public Mono<Page<T>> searchByEntityForPageWithRefQuery(EntityByPage<T> entityByPage, Sort sort, String matchingText, Mode forPageMode, String... excludeFields) {
        return searchByEntityForPage(entityByPage, sort, null, null, 0D, matchingText, forPageMode, false, excludeFields);
    }

    @Override
    public Mono<Long> findAndModify(T findEntity, T updateEntity) {
        Assert.notNull(findEntity, "查询条件不能为空");
        Assert.notNull(updateEntity, "要更新的数据不能为空");
        Query query = this.generateQueryByEntity(findEntity, false, null);
        Update update = generateUpdateByEntity(updateEntity);
        Mono<T> updated = mongoTemplate.findAndModify(query, update, this.entityClazz);
        return updated.hasElement().zipWith(updated, (isHasElement, updatedEntity) -> {
            if (isHasElement) {
                return updatedEntity.getId();
            }
            return "null";
        }).flatMap(result -> {
            if (result instanceof Long) {
                return Mono.just((Long) result);
            }
            return Mono.empty();
        });
    }

    @Override
    public Mono<Long> findAndRemove(T findEntity) {
        Query query = this.generateQueryByEntity(findEntity, false, null);
        Mono<T> deleted = mongoTemplate.findAndRemove(query, this.entityClazz);
        return deleted.hasElement().zipWith(deleted, (isHasElement, deletedEntity) -> {
            if (isHasElement) {
                return deletedEntity.getId();
            }
            return "null";
        }).flatMap(result -> {
            if (result instanceof Long) {
                return Mono.just((Long) result);
            }
            return Mono.empty();
        });
    }

    @Override
    public Mono<Long> updateByQuery(Query query, Update update) {
        Assert.notNull(query, "query 不能为空");
        Assert.notNull(update, "update 不能为空");
        return this.mongoTemplate.updateFirst(query, update, this.entityClazz).map(result -> result.getModifiedCount());
    }

    /**
     * @param t
     * @Title: processTextIndexField
     * @author klw
     * @Description: 处理全文索引
     */
    private void processTextIndexField(T t) {
        if (ITextIndexedSupport.class.isAssignableFrom(this.entityClazz)) {
            ITextIndexedSupport t1 = ((ITextIndexedSupport) t);
            t1.analyzerResult2TextIndexedField(AnalyzerUtil.strAnalyzerReturnsWithSpaceNoSmart(t1.toTextIndexedText()));
        } else if (ITextIndexedCustomSupport.class.isAssignableFrom(this.entityClazz)) {
            ((ITextIndexedCustomSupport) t).buildTextIndexedField();
        }
    }

    /**
     * @param list
     * @Title: processTextIndexField
     * @author klw
     * @Description: 处理全文索引
     */
    private void processTextIndexField(List<T> list) {
        if (ITextIndexedSupport.class.isAssignableFrom(this.entityClazz)) {
            for (T t : list) {
                ITextIndexedSupport t1 = ((ITextIndexedSupport) t);
                t1.analyzerResult2TextIndexedField(AnalyzerUtil.strAnalyzerReturnsWithSpaceNoSmart(t1.toTextIndexedText()));
            }
        } else if (ITextIndexedCustomSupport.class.isAssignableFrom(this.entityClazz)) {
            for (T t : list) {
                ((ITextIndexedCustomSupport) t).buildTextIndexedField();
            }
        }
    }

    /**
     * @param page
     * @return
     * @Title: processPage4Mongo
     * @author klw
     * @Description: 把页码处理为spring data MongoDB 的页码(它的第一页是0)
     */
    private int processPage4Mongo(int page) {
        if (page <= 0) {
            return 0;
        } else {
            return (page - 1);
        }
    }

    /**
     * @Title: generateLookupOperation4RefQuery
     * @author klw
     * @Description: 为 @RefQuery 注解生成 LookupOperation
     * @return
     */
//    private List<AggregationOperation> generateLookupOperation4RefQuery() {
//	List<Field> fieldList = Arrays.asList(this.entityClazz.getDeclaredFields());
//	List<AggregationOperation> rList = new ArrayList<>();
//	for(Field field : fieldList) {
//	    RefQuery refQuery = field.getAnnotation(RefQuery.class);
//	    if(refQuery == null) {
//		continue;
//	    }
//	    String targetFieldName = refQuery.targetFieldName();
//	    if(StringUtils.isBlank(targetFieldName)) {
//		continue;
//	    }
//	    String refFieldName = refQuery.refFieldName();
//	    if(StringUtils.isBlank(refFieldName)) {
//		refFieldName = MongoDBConstant.ID_KEY;
//	    }
////	    Direction direction = refQuery.sortDirection().equals(SortDirection.ASC) ? Direction.ASC : Direction.DESC;
////	    Sort sort = Sort.by(direction, refQuery.sortFiled());
//	    if(field.getType().equals(List.class)) {
//		Type genericType = field.getGenericType();
//		if(genericType == null) {
//		    continue;  
//		}
//		if (genericType instanceof ParameterizedType) {
//		    ParameterizedType pt = (ParameterizedType) genericType;
//		    // 得到泛型里的class类型对象
//		    Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
//		    Document document = genericClazz.getAnnotation(Document.class);
//		    if(document == null) {
//			continue;
//		    }
//		    String fromName = document.collection();
//		    LookupOperation lookupOperation = LookupOperation.newLookup().
//		            from(fromName).
//		            localField(refFieldName).
//		            foreignField(targetFieldName).
//		            as(field.getName());
//		    rList.add(lookupOperation);
//		}
//	    } 
//	    
//	}
//	return rList;
//    }

//    private Criteria generateQueryByEntity4RefQuery(T entity) {
//	if(entity == null) {
//	    return null;
//	}
//	List<Field> fieldList = Arrays.asList(entity.getClass().getDeclaredFields());
//	Criteria criteria = new Criteria();
//	//由于这里只取当前类的字段,不取父类,所以取不到 id , 所以要对 id 做下处理
//	if(entity.getId() != null) {
//	    criteria.and(MongoDBConstant.ID_KEY).is(entity.getId());
//	}
//	for(Field field : fieldList) {
//	    if(field.getName().equals("serialVersionUID")) {
//		continue;
//	    }
//	    
//	    field.setAccessible(true);
//	    Object value = null;
//	    try {
//		value = field.get(entity);
//	    } catch (IllegalArgumentException | IllegalAccessException e) {
//		e.printStackTrace();
//	    }
//	    if(value != null) {
//		// 判断是否有 QueryLikeField 注解
//		QueryLikeField queryLikeField = field.getAnnotation(QueryLikeField.class);
//		if(queryLikeField != null) {
//		    //模糊匹配
//		    String patternStr = "^.*" + value + ".*$";
//		    Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
//		    criteria.and(field.getName()).regex(pattern);
//		} else {
//		    criteria.and(field.getName()).is(value);
//		}
//	    }
//	}
//	return criteria;
//    }

    /**
     * @Title: processRefQuery
     * @author klw
     * @Description: 处理 RefQuery 注解
     * @param entitys
     */
//    private void processRefQuery(List<T> entitys) {
//	List<Field> fieldList = Arrays.asList(this.entityClazz.getDeclaredFields());
//	here:
//	for(Field field : fieldList) {
//	    RefQuery refQuery = field.getAnnotation(RefQuery.class);
//	    if(refQuery == null) {
//		continue;
//	    }
//	    String targetFieldName = refQuery.targetFieldName();
//	    if(StringUtils.isBlank(targetFieldName)) {
//		continue;
//	    }
//	    String refFieldName = refQuery.refFieldName();
//	    if(StringUtils.isBlank(refFieldName)) {
//		refFieldName = "id";
//	    }
//	    Direction direction = refQuery.sortDirection().equals(SortDirection.ASC) ? Direction.ASC : Direction.DESC;
//	    Sort sort = Sort.by(direction, refQuery.sortFiled());
//	    for (T entity : entitys) {
//		try {
//		    Field refField = null;
//		    // 这里简单处理下:由于我们的实体暂定是只有一个父类的两层,可以通过判断如果是ID就从父类取的方式解决,后面如果有需要再处理
//		    if(refFieldName.equals("id")) {
//			refField = entity.getClass().getSuperclass().getDeclaredField(refFieldName);
//		    } else {
//			refField = entity.getClass().getDeclaredField(refFieldName);
//		    }
//		    if (refField == null) {
//			// 拿不到这个字段,那么说明这一堆 entitys 都拿不到,直接略过
//			// 不清楚这个 entitys 的list里是否可以装不同的符合T条件的进来(就是BaseBean的不同子类), 暂时不管这种极端情况,正常情况下不会这种用,遇到再说
//			continue here;
//		    }
//		    refField.setAccessible(true);
//		    Object value = refField.get(entity);
//		    if(value == null) {
//			continue;
//		    }
//		    Query query = Query.query(Criteria.where(targetFieldName).is(value));
//		    query.with(sort);
//		    field.setAccessible(true);
//		    if(field.getType().equals(List.class)) {
//			Type genericType = field.getGenericType();
//			if(genericType == null) {
//			    continue;  
//			}
//			if (genericType instanceof ParameterizedType) {
//			    ParameterizedType pt = (ParameterizedType) genericType;
//			    // 得到泛型里的class类型对象
//			    Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
//			    field.set(entity, this.mongoTemplate.find(query, genericClazz));
//			}
//		    } else {
//			field.set(entity, this.mongoTemplate.findOne(query, field.getType()));
//		    }
//		    
//		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
//		    e.printStackTrace();
//		}
//	    }
//	    
//	}
//	
//    }

    /**
     * @param entity
     * @return
     * @Title: generateUpdateByEntity
     * @author klw
     * @Description: 根据实体中不为null的字段生成 Update
     */
    private Update generateUpdateByEntity(T entity) {
        Assert.notNull(entity, "要更新的实体不能为空!");
        //由于这里只取当前类的字段,不取父类,所以取不到 id , 不用针对 id 做处理
        List<Field> fieldList = Arrays.asList(entity.getClass().getDeclaredFields());
        Update update = new Update();
        int count = 0;
        for (Field field : fieldList) {
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                if (value != null) {
                    if (field.getAnnotation(NotPersistence.class) != null) {
                        field.set(entity, null);
                    } else {
                        update.set(field.getName(), value);
                        count++;
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (count == 0) {
            throw new RuntimeException("要更新的实体不能为空!");
        }
        return update;
    }

    /**
     * @param entity
     * @param isExcludeRef 是否排除标注了 @DBRef 的字段
     * @return
     * @Title: generateQueryByEntity
     * @author klw
     * @Description: 根据实体中不为null的字段生成 Query, 如果字段标注了 QueryLikeField 则为模糊查询
     */
    private Query generateQueryByEntity(T entity, boolean isExcludeRef, String[] excludeFields) {
        if (entity == null) {
            return new Query();
        }
        List<Field> fieldList = Arrays.asList(entity.getClass().getDeclaredFields());
        Query query = new Query();
        //由于这里只取当前类的字段,不取父类,所以取不到 id , 所以要对 id 做下处理
        if (entity.getId() != null) {
            query.addCriteria(Criteria.where(MongoDBConstant.ID_KEY).is(entity.getId()));
        }
        org.springframework.data.mongodb.core.query.Field fields = query.fields();
        for (Field field : fieldList) {
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }
            if (isExcludeRef && field.isAnnotationPresent(DBRef.class)) {
                fields.exclude(field.getName());
            }

            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(entity);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value != null) {
                // 判断是否有 QueryLikeField 注解
                QueryLikeField queryLikeField = field.getAnnotation(QueryLikeField.class);
                if (queryLikeField != null) {
                    //模糊匹配
                    String patternStr = "^.*" + value + ".*$";
                    Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
                    query.addCriteria(Criteria.where(field.getName()).regex(pattern));
                } else {
                    query.addCriteria(Criteria.where(field.getName()).is(value));
                }
            }
        }
        if (ArrayUtils.isNotEmpty(excludeFields)) {
            for (String exclude : excludeFields) {
                fields.exclude(exclude);
            }
        }
        return query;
    }

    /**
     * @return
     * @Title: findDBRefFields
     * @author klw
     * @Description: 从query排除当前DAO对应的实体中的标注了 @DBRef 的字段
     */
    private void excludeDBRefFields(Query query, String[] excludeFields) {
        List<Field> fieldList = Arrays.asList(this.entityClazz.getDeclaredFields());
        org.springframework.data.mongodb.core.query.Field fields = query.fields();
        for (Field field : fieldList) {
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }
            if (field.isAnnotationPresent(DBRef.class)) {
                fields.exclude(field.getName());
            }
        }
        if (ArrayUtils.isNotEmpty(excludeFields)) {
            for (String exclude : excludeFields) {
                fields.exclude(exclude);
            }
        }
    }

    private void excludeFields(Query query, String[] excludeFields) {
        if (ArrayUtils.isNotEmpty(excludeFields)) {
            org.springframework.data.mongodb.core.query.Field fields = query.fields();
            for (String exclude : excludeFields) {
                fields.exclude(exclude);
            }
        }
    }

    /**
     * @param List
     * @Title: processNotPersistence4Save
     * @author klw
     * @Description: 新增保存的时候, 发现有 @NotPersistence 注解的字段设置为null
     */
    private void processNotPersistence4Save(List<T> list) {
        Assert.notEmpty(list, "要处理的的实体list不能为空!");
        //由于这里只取当前类的字段,不取父类,所以取不到 id , 不用针对 id 做处理
        List<Field> fieldList = Arrays.asList(this.entityClazz.getDeclaredFields());
        for (Field field : fieldList) {
            if (field.getAnnotation(NotPersistence.class) != null) {
                field.setAccessible(true);
                for (T entity : list) {
                    try {
                        field.set(entity, null);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


//    public List<T> testLookUp(){
//	LookupOperation lookupOperation = LookupOperation.newLookup().
//	            from("demo2").
//	            localField("managerId").
//	            foreignField(MongoDBConstant.ID_KEY).
//	            as("manager");
//	LookupOperation lookupOperation2 = LookupOperation.newLookup().
//	            from("demo2").
//	            localField(MongoDBConstant.ID_KEY).
//	            foreignField("managerId").
//	            as("directReports");
//	AggregationOperation match = Aggregation.match(Criteria.where("name").is("manager"));
//	Aggregation.sort(sort);
//	Aggregation aggregation = Aggregation.newAggregation(match, lookupOperation, lookupOperation2);
//	return this.mongoTemplate.aggregate(aggregation, "demo2", this.entityClazz).getMappedResults();
//    }

}
