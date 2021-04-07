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
package top.klw8.alita.service.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.service.authority.ISystemAuthoritysCatlogService;
import top.klw8.alita.service.authority.mapper.ISystemAuthoritysCatlogMapper;

/**
 * @author klw
 * @ClassName: SystemAuthoritysCatlogServiceImpl
 * @Description: 权限的目录Service_实现
 * @date 2018年11月28日 下午3:52:21
 */
@Slf4j
@Service
public class SystemAuthoritysCatlogServiceImpl extends ServiceImpl<ISystemAuthoritysCatlogMapper, SystemAuthoritysCatlog> implements ISystemAuthoritysCatlogService {

    @Override
    public SystemAuthoritysCatlog findByCatlogNameAndAppTag(String catlogName, String appTag) {
        QueryWrapper<SystemAuthoritysCatlog> query = new QueryWrapper();
        query.eq("catlog_name", catlogName);
        query.eq("app_tag", appTag);
        return this.getOne(query);
    }

}
