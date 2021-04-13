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
package top.klw8.alita.orderService.test.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import top.klw8.alita.DemoMybatisServiceApplication;
import top.klw8.alita.entitys.demo.mongo.PgTest;
import top.klw8.alita.service.demo.service.demo.IPgTestService;

/**
 * pgTest的JUnit测试
 * 2019/8/8 14:25
 */
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= {DemoMybatisServiceApplication.class})
public class PgTestJUnit {

    @Autowired
    private IPgTestService service;

    @Test
    public void test(){
        PgTest e = new PgTest();
        e.setName("测试1");
        service.save(e);
        System.out.println(service.count());
    }

}
