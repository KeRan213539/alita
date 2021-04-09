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
package top.klw8.alita.demo.web.demo.datasecured;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import top.klw8.alita.entitys.authority.enums.ResourceType;
import top.klw8.alita.starter.auscan.IAuthoritysResourceSource;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: FileTestDsSource
 * @Description: fileTest 的 数据权限源
 * @date 2020/5/15 15:25
 */
@Component
public class FileTestDsSource implements IAuthoritysResourceSource {
    @Override
    public List<DemoAuthoritysResourceSourceItem> getDataSecuredSourceList() {
        return Lists.newArrayList(new DemoAuthoritysResourceSourceItem("fileTest", "fileTest", ResourceType.DATA));
    }
}
