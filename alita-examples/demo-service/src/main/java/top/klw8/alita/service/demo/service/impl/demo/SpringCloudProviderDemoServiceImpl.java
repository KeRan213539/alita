/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.service.demo.service.impl.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import top.klw8.alita.entitys.demo.mongo.MongoDBTest;
import top.klw8.alita.service.api.demo.ISpringCloudProviderDemoService;
import top.klw8.alita.service.demo.mapper.demo.IMongoDBTestDao;
import top.klw8.alita.starter.service.BaseServiceImpl;

import java.util.List;

/**
 * @author klw
 * @ClassName: SpringCloudProviderDemoController
 * @Description: SpringCloud提供者演示
 * @date 2018年9月13日 下午5:13:33
 */
@Slf4j
@DubboService(async = true)
public class SpringCloudProviderDemoServiceImpl extends BaseServiceImpl<MongoDBTest> implements ISpringCloudProviderDemoService {

    private IMongoDBTestDao dao;

    public SpringCloudProviderDemoServiceImpl(@Autowired IMongoDBTestDao dao) {
        super(dao);
        this.dao = dao;
    }

    @Override
    public List<MongoDBTest> queryAll(String abc) throws Exception {
        if (StringUtils.isBlank(abc)) throw new Exception("抛个异常玩玩");
        log.error("queryAll 被调用了888888888888888888");
        return asyncSendData(dao.findAll());
    }
}
