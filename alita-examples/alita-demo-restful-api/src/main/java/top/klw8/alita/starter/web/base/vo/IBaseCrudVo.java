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
package top.klw8.alita.starter.web.base.vo;


import top.klw8.alita.service.base.mongo.common.MongoBaseEntity;

/**
 * 要继承 WebapiCrudBaseController 的 Controller 对应的VO需要实现此接口
 * 2019年1月25日 下午1:34:52
 */
public interface IBaseCrudVo<E extends MongoBaseEntity> extends java.io.Serializable {

    public E buildEntity();
    
}
