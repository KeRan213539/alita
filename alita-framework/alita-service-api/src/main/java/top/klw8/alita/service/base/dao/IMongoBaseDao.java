package top.klw8.alita.service.base.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;

import reactor.core.publisher.Mono;
import top.klw8.alita.service.base.beans.EntityByPage;
import top.klw8.alita.service.base.dao.prarm.ForPageMode.Mode;
import top.klw8.alita.service.base.entitys.BaseEntity;

/**
 * @ClassName: IMongoBaseDao
 * @Description: IMongoBaseDao
 * @author klw
 * @date 2018年10月1日 下午2:05:44
 */
public interface IMongoBaseDao<T extends BaseEntity> {
    
    /**
     * @author klw
     * @Fields RADIAN2KM : 公里转换为弧度要除以的数,地理位置查询的时候用
     */
    public static final double KM2RADIAN = 6378.137;
    
    /**
     * @Title: getEntityClazz
     * @author klw
     * @Description: 获取DAO对应的实体的 Clazz
     * @return
     */
    Class<T> getEntityClazz();
    
    /**
     * 保存信息
     * 
     * @param baseBean
     * @return
     */
    Mono<T> save(T baseBean);

    /**
     * 批量保存信息
     * 
     * @param list
     * @return
     */
    Mono<List<T>> batchSave(List<T> list);

    /**
     * 根据ID删除数据
     * 
     * @param id
     * @return
     */
    Mono<Long> deleteById(ObjectId id);

    /**
     * 根据多个ID删除数据
     * 
     * @param ids
     * 
     * @return
     */
    Mono<List<T>> deleteByIds(ObjectId[] ids);

    /**
     * 根据多个ID查询数据
     * 
     * @param ids
     * @return
     */
    Mono<List<T>> findByIds(ObjectId[] ids, String... excludeFields);

    /**
     * 根据ID查询一条数据
     * 
     * @param id
     * @return
     */
    Mono<T> findById(ObjectId id, String... excludeFields);

    /**
     * 根据ID修改一条数据
     * 
     * @param
     * @return
     */
    Mono<Long> updateById(T baseBean);

    /**
     * 批量修改
     * 
     * @param list
     * @return 成功更新的数量
     */
    Mono<Integer> batchUpdate(List<T> list);

    /**
     * 查询所有数据
     * 
     * @return
     */
    Mono<List<T>> findAll(String... excludeFields);

    /**
     * 根据条件查询数据
     * 
     * @param
     * @return
     */
    Mono<List<T>> findByEntity(T baseBean, Sort sort, String... excludeFields);
    
    /**
     * @Title: findByEntity
     * @author klw
     * @Description: 根据条件和地理位置查询圆形范围内的
     * @param baseBean
     * @param sort
     * @param pointFieldName
     * @param point
     * @param rangeKm  圆形半径(KM)
     * @param excludeFields
     * @return
     */
    Mono<List<T>> findByEntity(T baseBean, Sort sort, String pointFieldName, Point point, double rangeKm, String... excludeFields);
    
    /**
     * @Title: findByEntity
     * @author klw
     * @Description: 根据条件和全文搜索关键字查询
     * @param baseBean
     * @param sort
     * @param matchingText  全文搜索关键字
     * @param excludeFields
     * @return
     */
    Mono<List<T>> findByEntity(T baseBean, Sort sort, String matchingText, String... excludeFields);

    /**
     * 根据对象查询所有条数
     *
     * @param
     * @return
     */
    Mono<Long> countByEntityForPage(EntityByPage<T> entityByPage);

    /**
     * @Title: searchByEntityForPage
     * @author klw
     * @Description: 根据对象查询数据并分页
     * @param entityByPage  查询条件
     * @param sort 排序
     * @param forPageMode  分页方式,如果使用 COMPARATIVE_UNIQUE_FIELD 方式,那么 forPageMode中的 fieldName 和 fieldValue 必有值
     * @param excludeFields  查询结果数据中不需要的字段
     * @return
     */
    Mono<Page<T>> searchByEntityForPage(EntityByPage<T> entityByPage, Sort sort,Mode forPageMode, String... excludeFields);
    
    /**
     * @Title: searchByEntityForPage
     * @author klw
     * @Description: 根据条件和地理位置查询圆形范围内的并分页
     * @param entityByPage  查询条件
     * @param sort 排序
     * @param pointFieldName  存放位置的字段名
     * @param point  地理位置查询的点
     * @param rangeKm  地理位置查询的范围半径(KM)
     * @param forPageMode  分页方式,如果使用 COMPARATIVE_UNIQUE_FIELD 方式,那么 forPageMode中的 fieldName 和 fieldValue 必有值
     * @param excludeFields  查询结果数据中不需要的字段
     * @return
     */
    Mono<Page<T>> searchByEntityForPage(EntityByPage<T> entityByPage, Sort sort, String pointFieldName, Point point, double rangeKm, Mode forPageMode, String... excludeFields);
    
    /**
     * @Title: searchByEntityForPage
     * @author klw
     * @Description: 根据条件和全文搜索关键字查询圆形范围内的并分页
     * @param entityByPage  查询条件
     * @param sort 排序
     * @param matchingText  全文搜索的关键字
     * @param forPageMode  分页方式,如果使用 COMPARATIVE_UNIQUE_FIELD 方式,那么 forPageMode中的 fieldName 和 fieldValue 必有值
     * @param excludeFields  查询结果数据中不需要的字段
     * @return
     */
    Mono<Page<T>> searchByEntityForPage(EntityByPage<T> entityByPage, Sort sort, String matchingText, Mode forPageMode, String... excludeFields);
    
    /**
     * @Title: findByIdsWithRefQuery
     * @author klw
     * @Description: 根据多个ID查询数据并处理 @DBRef (因为 @DBRef 效率低,所以单独一个方法)
     * @param ids
     * @return
     */
    Mono<List<T>> findByIdsWithRefQuery(ObjectId[] ids, String... excludeFields);
    
    /**
     * @Title: findByIdWithRefQuery
     * @author klw
     * @Description: 根据ID查询一条数据并处理 @DBRef (因为 @DBRef 效率低,所以单独一个方法)
     * @param id
     * @return
     */
    Mono<T> findByIdWithRefQuery(ObjectId id, String... excludeFields);
    
    /**
     * @Title: findAllWithRefQuery
     * @author klw
     * @Description: 查询所有数据并处理 @DBRef (因为 @DBRef 效率低,所以单独一个方法)
     * @return
     */
    Mono<List<T>> findAllWithRefQuery(String... excludeFields);
    
    /**
     * @Title: findByEntityWithRefQuery
     * @author klw
     * @Description: 根据条件查询数据并处理 @DBRef (因为 @DBRef 效率低,所以单独一个方法)
     * @param baseBean
     * @return
     */
    Mono<List<T>> findByEntityWithRefQuery(T baseBean, Sort sort, String... excludeFields);
    
    /**
     * @Title: findByEntity
     * @author klw
     * @Description: 根据条件和地理位置查询圆形范围内的并处理 @DBRef (因为 @DBRef 效率低,所以单独一个方法)
     * @param baseBean
     * @param sort
     * @param pointFieldName
     * @param point
     * @param rangeKm  圆形半径(KM)
     * @param excludeFields
     * @return
     */
    Mono<List<T>> findByEntityWithRefQuery(T baseBean, Sort sort, String pointFieldName, Point point, double rangeKm, String... excludeFields);
    
    /**
     * @Title: findByEntity
     * @author klw
     * @Description: 根据条件和全文搜索查询并处理 @DBRef (因为 @DBRef 效率低,所以单独一个方法)
     * @param baseBean
     * @param sort
     * @param matchingText
     * @param excludeFields
     * @return
     */
    Mono<List<T>> findByEntityWithRefQuery(T baseBean, Sort sort, String matchingText, String... excludeFields);
    
    /**
     * @Title: searchByEntityForPageWithRefQuery
     * @author klw
     * @Description: 根据对象查询数据并分页 @DBRef (因为 @DBRef 效率低,所以单独一个方法)
     * @param entityByPage  查询条件
     * @param sort 排序
     * @param forPageMode  分页方式,如果使用 COMPARATIVE_UNIQUE_FIELD 方式,那么 forPageMode中的 fieldName , fieldValue 必有值,否则将会走skip方式
     * @param excludeFields  查询结果数据中不需要的字段
     * @return
     */
    Mono<Page<T>> searchByEntityForPageWithRefQuery(EntityByPage<T> entityByPage, Sort sort, Mode forPageMode, String... excludeFields);
    
    /**
     * @Title: searchByEntityForPageWithRefQuery
     * @author klw
     * @Description: 根据条件和地理位置查询圆形范围内的并分页 @DBRef (因为 @DBRef 效率低,所以单独一个方法)
     * @param entityByPage  查询条件
     * @param sort 排序
     * @param pointFieldName  存放位置的字段名
     * @param point  地理位置查询的点
     * @param rangeKm  地理位置查询的范围半径(KM)
     * @param forPageMode  分页方式,如果使用 COMPARATIVE_UNIQUE_FIELD 方式,那么 forPageMode中的 fieldName 和 fieldValue 必有值
     * @param excludeFields  查询结果数据中不需要的字段
     * @return
     */
    Mono<Page<T>> searchByEntityForPageWithRefQuery(EntityByPage<T> entityByPage, Sort sort, String pointFieldName, Point point, double rangeKm, Mode forPageMode, String... excludeFields);
    
    /**
     * @Title: searchByEntityForPageWithRefQuery
     * @author klw
     * @Description: 根据条件和全文索引关键字查询并分页 @DBRef (因为 @DBRef 效率低,所以单独一个方法)
     * @param entityByPage  查询条件
     * @param sort 排序
     * @param matchingText  全文搜索的关键字
     * @param forPageMode  分页方式,如果使用 COMPARATIVE_UNIQUE_FIELD 方式,那么 forPageMode中的 fieldName 和 fieldValue 必有值
     * @param excludeFields  查询结果数据中不需要的字段
     * @return
     */
    Mono<Page<T>> searchByEntityForPageWithRefQuery(EntityByPage<T> entityByPage, Sort sort, String matchingText, Mode forPageMode, String... excludeFields);
    
    /**
     * @Title: findAndModify
     * @author klw
     * @Description: 原子操作,不会有并发问题: 查找并更新,只会更新一条数据(如果查询条件结果为多个数据,也只会更新一个,建议查询条件尽量精确到一个结果)
     * @param findEntity
     * @param updateEntity
     * @return 被更新的数据的ID
     */
    Mono<Long> findAndModify(T findEntity, T updateEntity);
    
    /**
     * @Title: findAndRemove
     * @author klw
     * @Description: 原子操作,不会有并发问题: 查找并删除,只会删除一条数据(如果查询条件结果为多个数据,也只会删除一个,建议查询条件尽量精确到一个结果)
     * @param findEntity
     * @return 被删除的数据的ID
     */
    Mono<Long> findAndRemove(T findEntity);
    
}
