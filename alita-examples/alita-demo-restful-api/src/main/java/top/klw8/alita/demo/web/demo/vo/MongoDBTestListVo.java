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
package top.klw8.alita.demo.web.demo.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.entitys.demo.mongo.MongoDBTest;
import top.klw8.alita.starter.web.base.vo.ListPrarmVo;

/**
 * @author klw
 * @ClassName: MongoDBTestListVo
 * @Description: demo 获取列表的VO
 * @date 2019年1月26日 下午5:25:19
 */
@Getter
@Setter
@ToString
public class MongoDBTestListVo extends ListPrarmVo<MongoDBTest> {

    private static final long serialVersionUID = -5804689421796908265L;

    @ApiParam(value = "姓名")
    private String name;

    @ApiParam(value = "工资")
    private BigDecimal salary;

    @Override
    public MongoDBTest buildEntity() {
        MongoDBTest e = new MongoDBTest();
        e.setName(name);
        e.setSalary(salary);
        e.setSalary2(salary);
        return e;
    }

}
