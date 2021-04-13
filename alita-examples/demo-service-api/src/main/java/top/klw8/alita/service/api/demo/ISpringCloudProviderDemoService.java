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
package top.klw8.alita.service.api.demo;

import java.util.List;

import top.klw8.alita.entitys.demo.mongo.MongoDBTest;
import top.klw8.alita.service.base.mongo.api.IMongoBaseService;


/**
 * 
 * 2018年9月13日 下午5:34:20
 */
public interface ISpringCloudProviderDemoService extends IMongoBaseService<MongoDBTest> {

    /**
     * demo 查询全部
     * @param abc 任意参数,如果为空就抛异常
     * @return
     * @throws Exception
     */
    public List<MongoDBTest> queryAll(String abc) throws Exception;

}
