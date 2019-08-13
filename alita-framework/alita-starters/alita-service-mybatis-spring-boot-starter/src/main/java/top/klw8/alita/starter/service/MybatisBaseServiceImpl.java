package top.klw8.alita.starter.service;

import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.extension.service.additional.query.ChainQuery;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.ChainUpdate;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.UpdateChainWrapper;
import org.apache.ibatis.binding.MapperMethod;
import top.klw8.alita.service.api.IMybatisBaseService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.klw8.alita.starter.service.common.ServiceContext;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: MybatisBaseServiceImpl
 * @Description: mybatis 的 base service 实现（ 泛型：M 是 mapper 对象，T 是实体）
 * @date 2019/8/10 9:55
 */
public class MybatisBaseServiceImpl<M extends BaseMapper<T>, T> implements IMybatisBaseService<T> {

    protected Log log = LogFactory.getLog(this.getClass());

    @Autowired
    protected M baseMapper;

    public MybatisBaseServiceImpl() {
    }

    /**
     * 获取对应 entity 的 BaseMapper
     *
     * @return BaseMapper
     */
    protected M getBaseMapper() {
        return this.baseMapper;
    }

    /**
     * 判断数据库操作是否成功
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    protected boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }

    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), 1);
    }

    /**
     * 批量操作 SqlSession
     */
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(this.currentModelClass());
    }

    /**
     * 释放sqlSession
     *
     * @param sqlSession session
     */
    protected void closeSqlSession(SqlSession sqlSession) {
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(this.currentModelClass()));
    }

    /**
     * 获取 SqlStatement
     *
     * @param sqlMethod ignore
     * @return ignore
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(this.currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    @Override
    public CompletableFuture<Boolean> save(T entity) {
        return CompletableFuture.supplyAsync(() -> this.retBool(this.baseMapper.insert(entity)), ServiceContext.executor);
    }

    /**
     * 批量插入
     *
     * @param entityList ignore
     * @param batchSize ignore
     * @return ignore
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CompletableFuture<Boolean> saveBatch(Collection<T> entityList, int batchSize) {
        return CompletableFuture.supplyAsync(() -> {
            String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
            try (SqlSession batchSqlSession = sqlSessionBatch()) {
                int i = 0;
                for (T anEntityList : entityList) {
                    batchSqlSession.insert(sqlStatement, anEntityList);
                    if (i >= 1 && i % batchSize == 0) {
                        batchSqlSession.flushStatements();
                    }
                    i++;
                }
                batchSqlSession.flushStatements();
            }
            return true;
        }, ServiceContext.executor);
    }

    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CompletableFuture<Boolean> saveOrUpdate(T entity) {
        return CompletableFuture.supplyAsync(() -> {
            if (null != entity) {
                Class<?> cls = entity.getClass();
                TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
                Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
                String keyProperty = tableInfo.getKeyProperty();
                Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
                Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
                try {
                    return StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal)) ? save(entity).get() : updateById(entity).get();
                } catch (InterruptedException | ExecutionException e) {
                    log.error("", e);
                }
            }
            return false;
        }, ServiceContext.executor);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CompletableFuture<Boolean> saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        return CompletableFuture.supplyAsync(() -> {
            Assert.notEmpty(entityList, "error: entityList must not be empty");
            Class<?> cls = currentModelClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
            String keyProperty = tableInfo.getKeyProperty();
            Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
            try (SqlSession batchSqlSession = sqlSessionBatch()) {
                int i = 0;
                for (T entity : entityList) {
                    Object idVal = ReflectionKit.getMethodValue(cls, entity, keyProperty);
                    if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
                        batchSqlSession.insert(sqlStatement(SqlMethod.INSERT_ONE), entity);
                    } else {
                        MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                        param.put(Constants.ENTITY, entity);
                        batchSqlSession.update(sqlStatement(SqlMethod.UPDATE_BY_ID), param);
                    }
                    // 不知道以后会不会有人说更新失败了还要执行插入 😂😂😂
                    if (i >= 1 && i % batchSize == 0) {
                        batchSqlSession.flushStatements();
                    }
                    i++;
                }
                batchSqlSession.flushStatements();
            }
            return true;
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<Boolean> removeById(Serializable id) {
        return CompletableFuture.supplyAsync(() -> SqlHelper.retBool(baseMapper.deleteById(id)), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<Boolean> removeByMap(Map<String, Object> columnMap) {
        return CompletableFuture.supplyAsync(() -> {
            Assert.notEmpty(columnMap, "error: columnMap must not be empty");
            return SqlHelper.retBool(this.baseMapper.deleteByMap(columnMap));
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<Boolean> remove(Wrapper<T> wrapper) {
        return CompletableFuture.supplyAsync(() -> SqlHelper.retBool(this.baseMapper.delete(wrapper)), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<Boolean> removeByIds(Collection<? extends Serializable> idList) {
        return CompletableFuture.supplyAsync(() -> SqlHelper.retBool(this.baseMapper.deleteBatchIds(idList)), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<Boolean> updateById(T entity) {
        return CompletableFuture.supplyAsync(() -> this.retBool(this.baseMapper.updateById(entity)), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<Boolean> update(T entity, Wrapper<T> updateWrapper) {
        return CompletableFuture.supplyAsync(() -> this.retBool(this.baseMapper.update(entity, updateWrapper)), ServiceContext.executor);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CompletableFuture<Boolean> updateBatchById(Collection<T> entityList, int batchSize) {
        return CompletableFuture.supplyAsync(() -> {
            Assert.notEmpty(entityList, "error: entityList must not be empty");
            String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
            try (SqlSession batchSqlSession = sqlSessionBatch()) {
                int i = 0;
                for (T anEntityList : entityList) {
                    MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                    param.put(Constants.ENTITY, anEntityList);
                    batchSqlSession.update(sqlStatement, param);
                    if (i >= 1 && i % batchSize == 0) {
                        batchSqlSession.flushStatements();
                    }
                    i++;
                }
                batchSqlSession.flushStatements();
            }
            return true;
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<T> getById(Serializable id) {
        return CompletableFuture.supplyAsync(() -> this.baseMapper.selectById(id), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<Collection<T>> listByIds(Collection<? extends Serializable> idList) {
        return CompletableFuture.supplyAsync(() -> this.baseMapper.selectBatchIds(idList), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<Collection<T>> listByMap(Map<String, Object> columnMap) {
        return CompletableFuture.supplyAsync(() -> this.baseMapper.selectByMap(columnMap), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<T> getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        return CompletableFuture.supplyAsync(() -> throwEx ? this.baseMapper.selectOne(queryWrapper) : SqlHelper.getObject(this.log, this.baseMapper.selectList(queryWrapper)), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<Map<String, Object>> getMap(Wrapper<T> queryWrapper) {
        return CompletableFuture.supplyAsync(() -> SqlHelper.getObject(this.log, this.baseMapper.selectMaps(queryWrapper)), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<Integer> count(Wrapper<T> queryWrapper) {
        return CompletableFuture.supplyAsync(() -> SqlHelper.retCount(this.baseMapper.selectCount(queryWrapper)), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<List<T>> list(Wrapper<T> queryWrapper) {
        return CompletableFuture.supplyAsync(() -> this.baseMapper.selectList(queryWrapper), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<IPage<T>> page(IPage<T> page, Wrapper<T> queryWrapper) {
        return CompletableFuture.supplyAsync(() -> this.baseMapper.selectPage(page, queryWrapper), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<List<Map<String, Object>>> listMaps(Wrapper<T> queryWrapper) {
        return CompletableFuture.supplyAsync(() -> this.baseMapper.selectMaps(queryWrapper), ServiceContext.executor);
    }

    @Override
    public <V> CompletableFuture<List<V>> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return CompletableFuture.supplyAsync(() -> (List) this.baseMapper.selectObjs(queryWrapper).stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList()), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<IPage<Map<String, Object>>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper) {
        return CompletableFuture.supplyAsync(() -> this.baseMapper.selectMapsPage(page, queryWrapper), ServiceContext.executor);
    }

    @Override
    public <V> CompletableFuture<V> getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return SqlHelper.getObject(this.log, this.listObjs(queryWrapper, mapper).get());
            } catch (InterruptedException | ExecutionException e) {
                log.error("", e);
            }
            return null;
        }, ServiceContext.executor);
    }

    /**
     * 以下的方法使用介绍:
     *
     * 一. 名称介绍
     * 1. 方法名带有 query 的为对数据的查询操作, 方法名带有 update 的为对数据的修改操作
     * 2. 方法名带有 lambda 的为内部方法入参 column 支持函数式的
     *
     * 二. 支持介绍
     * 1. 方法名带有 query 的支持以 {@link ChainQuery} 内部的方法名结尾进行数据查询操作
     * 2. 方法名带有 update 的支持以 {@link ChainUpdate} 内部的方法名为结尾进行数据修改操作
     *
     * 三. 使用示例,只用不带 lambda 的方法各展示一个例子,其他类推
     * 1. 根据条件获取一条数据: `query().eq("column", value).one()`
     * 2. 根据条件删除一条数据: `update().eq("column", value).remove()`
     *
     */

    /**
     * 链式查询 普通
     *
     * @return QueryWrapper 的包装类
     */
    protected QueryChainWrapper<T> query() {
        return new QueryChainWrapper(this.getBaseMapper());
    }

    /**
     * 链式查询 lambda 式
     * <p>注意：不支持 Kotlin </p>
     *
     * @return LambdaQueryWrapper 的包装类
     */
    protected LambdaQueryChainWrapper<T> lambdaQuery() {
        return new LambdaQueryChainWrapper(this.getBaseMapper());
    }

    /**
     * 链式更改 普通
     *
     * @return UpdateWrapper 的包装类
     */
    protected UpdateChainWrapper<T> update() {
        return new UpdateChainWrapper(this.getBaseMapper());
    }

    /**
     * 链式更改 lambda 式
     * <p>注意：不支持 Kotlin </p>
     *
     * @return LambdaUpdateWrapper 的包装类
     */
    protected LambdaUpdateChainWrapper<T> lambdaUpdate() {
        return new LambdaUpdateChainWrapper(this.getBaseMapper());
    }

}
