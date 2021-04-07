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
package top.klw8.alita.service.api.mybatis;


import top.klw8.alita.entitys.demo.mongo.ExtUserInfoDemo;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.base.mongo.api.IMongoBaseService;

/**
 * @ClassName: IExtUserInfoDemoService
 * @Description: 用户扩展信息demo 的 Service
 * @author klw
 * @date 2019年1月30日 上午11:20:01
 */
public interface IExtUserInfoDemoService extends IMongoBaseService<ExtUserInfoDemo> {

    /**
     * @Title: findByAccountId
     * @author klw
     * @Description: 根据帐号表ID查找扩展用户信息
     * @param accountId
     * @return
     */
    public ExtUserInfoDemo findByAccountId(String accountId);
    
    /**
     * @Title: addExtUserInfoDemo
     * @author klw
     * @Description: 新增用户(账户和扩展信息)
     * @param account
     * @param user
     * @return
     */
    public ExtUserInfoDemo addUser(AlitaUserAccount account, ExtUserInfoDemo user);
    
}
