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
package top.klw8.alita.base.mongodb.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标注需要从其他表查询的数据(该数据的类型必须为 BaseBean 的子类)
 * 【注意】:该注解只查询第一层,即: 
 * 	A实体里有个属性为B实体,并标注了此注解
 * 	同时B实体里也有个属性为C实体,也标注了此注解
 * 	查询A实体(或者A实体的集合),这时查询出来的A的结果中只包含B的数据,而B中的C的数据则没有
 * 	此时需要查询B,才会包含C的数据
 * 如果将来有需要多层次都查出来的需求,再做改造:
 * 	这种关联查询性能低下,原理是通过先查询出A(集合),再根据引用字段查B
 * 	为什么不用mongodb的 Ref 方式? 因为ref方式是把关系维护在A这边,如果A里装的是B的集合,那么每新增一条B数据,都需要在A的引用字段中增加一个,否则新增的B数据在A的结果中是没有的,同理删除B的时候也需要从A中删除
 * 2018年10月2日 下午4:55:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@Deprecated
public @interface RefQuery {
    
    public enum SortDirection {

	ASC, DESC;
    }

    /**
     * 对应实体中哪个字段保存了当前实体中某个标识
     * @return
     */
    String targetFieldName();
    
    /**
     * 保存的是当前实体的哪个字段的值,如果为空则用ID
     * @return
     */
    String refFieldName() default "";
    
    /**
     * 排序方向: 默认倒序
     * @return
     */
    SortDirection sortDirection() default SortDirection.DESC;
    
    /**
     * 根据目标实体的哪个字段排序: 默认 id
     * @return
     */
    String[] sortFiled() default "_id";
    
}
