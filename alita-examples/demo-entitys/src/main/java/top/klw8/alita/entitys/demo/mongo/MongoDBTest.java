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
package top.klw8.alita.entitys.demo.mongo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.base.mongodb.annotations.NotPersistence;
import top.klw8.alita.base.mongodb.annotations.QueryLikeField;
import top.klw8.alita.service.base.mongo.common.IGeoSearchSupport;
import top.klw8.alita.service.base.mongo.common.ITextIndexedCustomSupport;
import top.klw8.alita.service.base.mongo.common.MongoBaseEntity;
import top.klw8.alita.utils.AnalyzerUtil;


/**
 * MongoDBTest
 * 2017年10月27日 下午4:11:52
 */
@Document(collection = "demo")  // spring data mongodb 的注解
@Getter
@Setter
@ToString
//@EqualsAndHashCode(callSuper = false)
//@ApiModel(value = "MongoDBTest", description = "demo用户")
public class MongoDBTest extends MongoBaseEntity implements ITextIndexedCustomSupport, IGeoSearchSupport {

    private static final long serialVersionUID = 3002834053850347862L;

    /**
     * name : 姓名
     */
    @QueryLikeField
    private String name;

    /**
     * salary : 工资
     */
    private BigDecimal salary;
    
    @NotPersistence // spring data mongodb 的注解
    private BigDecimal salary2;
    
    /**
     * location : 位置(纬度/经度)
     */
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoPoint location;
    
    private LocalDateTime createDataTime;
    
    /**
     * textIndexedField : 全文搜索字段
     */
    @TextIndexed
    private String textIndexedField;

    /*
     * <p>Title: buildTextIndexedField</p>
     * <p>Description: </p>
     * @see top.klw8.alita.service.base.entitys.ITextIndexedCustomSupport#buildTextIndexedField()
     */
    @Override
    public void buildTextIndexedField() {
	this.textIndexedField = AnalyzerUtil.strAnalyzerReturnsWithSpaceNoSmart(name);
    }

}
