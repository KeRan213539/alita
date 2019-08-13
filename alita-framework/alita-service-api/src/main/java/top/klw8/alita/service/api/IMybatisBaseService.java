package top.klw8.alita.service.api;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: IMybatisBaseService
 * @Description: mybatis 的 base service
 * @date 2019/8/10 9:52
 */
public interface IMybatisBaseService<T> {

    /**
     * 插入一条记录（选择字段，策略插入）
     *
     * @param entity 实体对象
     */
    CompletableFuture<Boolean> save(T entity);

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     */
    @Transactional(rollbackFor = Exception.class)
    default CompletableFuture<Boolean> saveBatch(Collection<T> entityList) {
        return this.saveBatch(entityList, 1000);
    }

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     * @param batchSize  插入批次数量
     */
    CompletableFuture<Boolean> saveBatch(Collection<T> entityList, int batchSize);

    /**
     * 批量修改插入
     *
     * @param entityList 实体对象集合
     */
    @Transactional(rollbackFor = Exception.class)
    default CompletableFuture<Boolean> saveOrUpdateBatch(Collection<T> entityList) {
        return this.saveOrUpdateBatch(entityList, 1000);
    }

    /**
     * 批量修改插入
     *
     * @param entityList 实体对象集合
     * @param batchSize  每次的数量
     */
    CompletableFuture<Boolean> saveOrUpdateBatch(Collection<T> entityList, int batchSize);

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    CompletableFuture<Boolean> removeById(Serializable id);

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     */
    CompletableFuture<Boolean> removeByMap(Map<String, Object> columnMap);

    /**
     * 根据 entity 条件，删除记录
     *
     * @param queryWrapper 实体包装类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    CompletableFuture<Boolean> remove(Wrapper<T> queryWrapper);

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     */
    CompletableFuture<Boolean> removeByIds(Collection<? extends Serializable> idList);

    /**
     * 根据 ID 选择修改
     *
     * @param entity 实体对象
     */
    CompletableFuture<Boolean> updateById(T entity);

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    CompletableFuture<Boolean> update(T entity, Wrapper<T> updateWrapper);

    /**
     * 根据 UpdateWrapper 条件，更新记录 需要设置sqlset
     *
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    default CompletableFuture<Boolean> update(Wrapper<T> updateWrapper) {
        return this.update(null, updateWrapper);
    }

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     */
    @Transactional(rollbackFor = Exception.class)
    default CompletableFuture<Boolean> updateBatchById(Collection<T> entityList) {
        return this.updateBatchById(entityList, 1000);
    }

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     * @param batchSize  更新批次数量
     */
    CompletableFuture<Boolean> updateBatchById(Collection<T> entityList, int batchSize);

    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     */
    CompletableFuture<Boolean> saveOrUpdate(T entity);

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    CompletableFuture<T> getById(Serializable id);

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表
     */
    CompletableFuture<Collection<T>> listByIds(Collection<? extends Serializable> idList);

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     */
    CompletableFuture<Collection<T>> listByMap(Map<String, Object> columnMap);

    /**
     * 根据 Wrapper，查询一条记录 <br/>
     * <p>结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")</p>
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default CompletableFuture<T> getOne(Wrapper<T> queryWrapper) {
        return this.getOne(queryWrapper, true);
    }

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @param throwEx      有多个 result 是否抛出异常
     */
    CompletableFuture<T> getOne(Wrapper<T> queryWrapper, boolean throwEx);

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    CompletableFuture<Map<String, Object>> getMap(Wrapper<T> queryWrapper);

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @param mapper       转换函数
     */
    <V> CompletableFuture<V> getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    CompletableFuture<Integer> count(Wrapper<T> queryWrapper);

    /**
     * 查询总记录数
     *
     * @see Wrappers#emptyWrapper()
     */
    default CompletableFuture<Integer> count() {
        return this.count(Wrappers.emptyWrapper());
    }

    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    CompletableFuture<List<T>> list(Wrapper<T> queryWrapper);

    /**
     * 查询所有
     *
     * @see Wrappers#emptyWrapper()
     */
    default CompletableFuture<List<T>> list() {
        return this.list(Wrappers.emptyWrapper());
    }

    /**
     * 翻页查询
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    CompletableFuture<IPage<T>> page(IPage<T> page, Wrapper<T> queryWrapper);

    /**
     * 无条件翻页查询
     *
     * @param page 翻页对象
     * @see Wrappers#emptyWrapper()
     */
    default CompletableFuture<IPage<T>> page(IPage<T> page) {
        return this.page(page, Wrappers.emptyWrapper());
    }

    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    CompletableFuture<List<Map<String, Object>>> listMaps(Wrapper<T> queryWrapper);

    /**
     * 查询所有列表
     *
     * @see Wrappers#emptyWrapper()
     */
    default CompletableFuture<List<Map<String, Object>>> listMaps() {
        return this.listMaps(Wrappers.emptyWrapper());
    }

    /**
     * 查询全部记录
     */
    default CompletableFuture<List<Object>> listObjs() {
        return this.listObjs(Function.identity());
    }

    /**
     * 查询全部记录
     *
     * @param mapper 转换函数
     */
    default <V> CompletableFuture<List<V>> listObjs(Function<? super Object, V> mapper) {
        return this.listObjs(Wrappers.emptyWrapper(), mapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default CompletableFuture<List<Object>> listObjs(Wrapper<T> queryWrapper) {
        return this.listObjs(queryWrapper, Function.identity());
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @param mapper       转换函数
     */
    <V> CompletableFuture<List<V>> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);

    /**
     * 翻页查询
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    CompletableFuture<IPage<Map<String, Object>>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper);

    /**
     * 无条件翻页查询
     *
     * @param page 翻页对象
     * @see Wrappers#emptyWrapper()
     */
    default CompletableFuture<IPage<Map<String, Object>>> pageMaps(IPage<T> page) {
        return this.pageMaps(page, Wrappers.emptyWrapper());
    }
}
