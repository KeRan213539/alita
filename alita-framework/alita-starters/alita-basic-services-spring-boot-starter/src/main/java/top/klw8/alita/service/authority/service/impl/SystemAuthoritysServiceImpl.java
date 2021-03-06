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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.klw8.alita.entitys.authority.AlitaAuthoritysMenu;
import top.klw8.alita.service.authority.mapper.ISystemAuthoritysMapper;
import top.klw8.alita.service.authority.ISystemAuthoritysService;

import java.util.List;


/**
 * 系统权限Service_实现
 * 2018年11月28日 下午3:51:25
 */
@Slf4j
@Service
public class SystemAuthoritysServiceImpl
        extends ServiceImpl<ISystemAuthoritysMapper, AlitaAuthoritysMenu>
        implements ISystemAuthoritysService {

    @Override
    public AlitaAuthoritysMenu findByAuActionAndAppTag(String action, String appTag){
        QueryWrapper<AlitaAuthoritysMenu> query = new QueryWrapper();
        query.eq("authority_action", action);
        query.eq("app_tag", appTag);
        return this.getOne(query);
    }

    @Override
    public int removeAuthorityFromRole(String auId) {
        return this.baseMapper.removeAuthorityFromRole(auId);
    }

    @Override
    public IPage<AlitaAuthoritysMenu> selectSystemAuthoritysList(Page page, String authorityName,
                                                                 String authorityType, String authorityAction,
                                                                 String catlogName, String appTag) {
        return this.baseMapper.selectSystemAuthoritysList(page,authorityName, authorityType,
                authorityAction, catlogName, appTag);
    }

    @Override
    public List<AlitaAuthoritysMenu> selectAllSystemAuthoritysWithCatlog(String appTag){
        return this.baseMapper.selectAllSystemAuthoritysWithCatlog(appTag);
    }


}
