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
package top.klw8.alita.service.demo.dao.impl.demo;

import org.springframework.stereotype.Repository;
import top.klw8.alita.entitys.demo.mongo.ExtUserInfoDemo;
import top.klw8.alita.starter.mongodb.base.MongoSpringDataBaseDao;

/**
 * @ClassName: ExtUserInfoDemoDao
 * @Description: 用户扩展信息Demo 的 DAO
 * @author klw
 * @date 2019年1月30日 上午11:29:04
 */
@Repository
public class ExtUserInfoDemoDao extends MongoSpringDataBaseDao<ExtUserInfoDemo> {
//    public class ExtUserInfoDemoDao extends MongoSpringDataBaseDao<ExtUserInfoDemo> implements IExtUserInfoDemoDao {

}
