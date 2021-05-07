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
package top.klw8.alita.providers.admin.impl;

import lombok.extern.slf4j.Slf4j;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import top.klw8.alita.entitys.authority.AlitaAuthoritysAppChannel;
import top.klw8.alita.service.api.authority.IAuthorityAppChannelProvider;
import top.klw8.alita.service.authority.IAuthorityAppChannelService;

import java.util.List;

/**
 * 应用渠道表 provider 实现.
 *
 * 2020/9/9 17:43
 */
@Slf4j
@DubboService
public class AuthorityAppChannelProviderImpl implements IAuthorityAppChannelProvider {
    
    @Autowired
    private IAuthorityAppChannelService channelService;
    
    public List<AlitaAuthoritysAppChannel> allChannel() {
        return channelService.list();
    }

}
