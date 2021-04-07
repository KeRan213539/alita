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
package top.klw8.alita.service.base.mongo.common;

/**
 * @ClassName: ITextIndexedCustomSupport
 * @Description: 全文索引支持,要全文索引的实体(自定义处理分词)需要实现此接口
 * @author klw
 * @date 2019年1月21日 下午4:33:36
 */
public interface ITextIndexedCustomSupport {

    /**
     * @Title: buildTextIndexedField
     * @author klw
     * @Description: 自定义索引字段中内容
     */
    public void buildTextIndexedField();
    
}
