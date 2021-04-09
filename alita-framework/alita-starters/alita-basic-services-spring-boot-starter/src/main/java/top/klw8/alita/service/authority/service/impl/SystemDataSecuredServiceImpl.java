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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.klw8.alita.entitys.authority.AlitaAuthoritysResource;
import top.klw8.alita.service.authority.ISystemDataSecuredService;
import top.klw8.alita.service.authority.mapper.ISystemDataSecuredMapper;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: SystemDataSecuredServiceImpl
 * @Description: 数据权限表 service 实现
 * @date 2020/5/13 16:46
 */
@Slf4j
@Service
public class SystemDataSecuredServiceImpl extends ServiceImpl<ISystemDataSecuredMapper,
        AlitaAuthoritysResource> implements ISystemDataSecuredService {


    @Override
    public AlitaAuthoritysResource findByResourceAndAuId(String resource, String auId) {
        QueryWrapper<AlitaAuthoritysResource> query = new QueryWrapper();
        if(StringUtils.isBlank(auId)){
            query.isNull("authoritys_id");
        } else {
            query.eq("authoritys_id", auId);
        }
        query.eq("resource", resource);
        return this.getOne(query);
    }

    @Override
    public List<AlitaAuthoritysResource> findByAuId(String auId, String appTag) {
        QueryWrapper<AlitaAuthoritysResource> query = new QueryWrapper();
        if(StringUtils.isBlank(auId)){
            query.isNull("authoritys_id");
        } else {
            query.eq("authoritys_id", auId);
        }
        if(StringUtils.isNotBlank(appTag)){
            query.eq("app_tag", appTag);
        }
        return this.list(query);
    }

    public List<AlitaAuthoritysResource> findByRoleIdAndAuId(String roleId, String auId){
        Assert.hasText(roleId, "roleId 不能为空");
        return this.baseMapper.findByRoleIdAndAuId(roleId, auId);
    }

    @Override
    public int countByAuId(String auId) {
        return this.baseMapper.countByAuId(auId);
    }
}
