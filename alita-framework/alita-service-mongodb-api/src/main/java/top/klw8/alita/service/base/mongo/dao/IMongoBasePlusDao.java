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
package top.klw8.alita.service.base.mongo.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import reactor.core.publisher.Mono;
import top.klw8.alita.service.base.mongo.common.MongoBaseEntity;

/**
 * @ClassName: IMongoBasePlusDao
 * @Description: IMongoBaseDao 的加强版
 * @author klw
 * @date 2018年10月1日 下午2:05:44
 */
public interface IMongoBasePlusDao<T extends MongoBaseEntity> extends IMongoBaseDao<T> {
    
    /**
     * @Title: getMongoTemplate
     * @author klw
     * @Description: 获取 MongoTemplate
     * @return
     */
    ReactiveMongoTemplate getMongoTemplate();
    
    MongoTemplate getNotReactiveMongoTemplate();

    /**
     * @Title: createQuery
     * @author klw
     * @Description: 创建查询
     * @return
     */
    Query createQuery();
    
    /**
     * @Title: createUpdateOperations
     * @author klw
     * @Description: 创建更行操作
     * @return
     */
    Update createUpdateOperations();
    
    /**
     * @Title: updateByQuery
     * @author klw
     * @Description: 自定义查询和更新,原子操作,
     * 该方法不会处理全文索引,如果使用该方法修改全文索引内容相关字段(比如会被索引的数据),需要手动处理索引字段
     * @param query
     * @param update
     * @return
     */
    Mono<Long> updateByQuery(Query query, Update operations);
    
}
