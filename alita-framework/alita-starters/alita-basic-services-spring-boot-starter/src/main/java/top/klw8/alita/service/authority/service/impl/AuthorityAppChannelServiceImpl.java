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
package top.klw8.alita.service.authority.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.klw8.alita.entitys.authority.AlitaAuthoritysAppChannel;
import top.klw8.alita.service.authority.IAuthorityAppChannelService;
import top.klw8.alita.service.authority.mapper.IAuthorityAppChannelMapper;

/**
 * 应用渠道表 service 实现.
 *
 * @author klw(213539 @ qq.com)
 * @ClassName: AuthorityAppChannelServiceImpl
 * @date 2020/9/9 16:58
 */
@Slf4j
@Service
public class AuthorityAppChannelServiceImpl extends ServiceImpl<IAuthorityAppChannelMapper, AlitaAuthoritysAppChannel>
        implements IAuthorityAppChannelService {

}
