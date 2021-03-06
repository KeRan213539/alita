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
package top.klw8.alita.service.base.mongo.common;

/**
 * 全文索引支持,要全文索引的实体(底层处理分词)需要实现此接口
 * 2019年1月18日 下午4:00:45
 */
public interface ITextIndexedSupport {

    /**
     * 获取将要被全文索引的文本,如果多个字段的值需要被索引,则返回这些字段内容的拼接后的字符串,字段内容之间用空格隔开
     * @return
     */
    public String toTextIndexedText();
    
    /**
     * 将分词结果放到实体对应字段中
     * @param analyzerResult
     */
    public void analyzerResult2TextIndexedField(String analyzerResult);
    
}
