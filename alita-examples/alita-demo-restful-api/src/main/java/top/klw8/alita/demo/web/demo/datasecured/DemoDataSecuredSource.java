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
package top.klw8.alita.demo.web.demo.datasecured;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import top.klw8.alita.starter.auscan.IDataSecuredSource;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DemoDataSecuredSourceStatic
 * @Description: 数据权限数据源
 * @date 2020/5/12 16:48
 */
@Component
public class DemoDataSecuredSource implements IDataSecuredSource {

    @Override
    public List<DemoDataSecuredSourceEnum> getDataSecuredSourceList() {
        return Lists.newArrayList(DemoDataSecuredSourceEnum.values());
    }
}
