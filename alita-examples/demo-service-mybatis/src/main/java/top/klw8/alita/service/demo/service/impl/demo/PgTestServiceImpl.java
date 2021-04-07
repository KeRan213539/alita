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
package top.klw8.alita.service.demo.service.impl.demo;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.klw8.alita.entitys.demo.mongo.PgTest;
import top.klw8.alita.service.demo.mapper.demo.PgTestMapper;
import top.klw8.alita.service.demo.service.demo.IPgTestService;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: postgresql demo service 实现
 * @Description:
 * @date 2019/8/8 14:12
 */
@Slf4j
@Service
public class PgTestServiceImpl extends ServiceImpl<PgTestMapper, PgTest> implements IPgTestService {
}
