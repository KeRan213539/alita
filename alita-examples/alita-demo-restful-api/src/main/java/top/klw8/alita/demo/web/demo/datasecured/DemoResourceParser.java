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

import org.springframework.stereotype.Component;
import top.klw8.alita.starter.datasecured.IResourceParser;
import top.klw8.alita.starter.datasecured.IResourceParserData;
import top.klw8.alita.starter.datasecured.ResourceParserResult;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DemoResourceParser
 * @Description: DemoResourceParser
 * @date 2020/4/30 10:57
 */
@Component
public class DemoResourceParser implements IResourceParser {

    @Override
    public ResourceParserResult parseResource(IResourceParserData parserPojo) {
        return new ResourceParserResult(new String[]{"demo"});
    }

}
