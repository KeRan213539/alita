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
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.klw8.alita.entitys.authority.AlitaAuthoritysMenu;
import top.klw8.alita.entitys.authority.AlitaAuthoritysResource;
import top.klw8.alita.entitys.authority.AlitaRole;
import top.klw8.alita.service.authority.ISystemRoleService;
import top.klw8.alita.service.authority.mapper.ISystemRoleMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统角色Service_实现
 * 2018年11月28日 下午3:53:01
 */
@Slf4j
@Service
public class SystemRoleServiceImpl extends ServiceImpl<ISystemRoleMapper, AlitaRole> implements ISystemRoleService {

    @Override
    public int addAuthority2Role(String roleId, AlitaAuthoritysMenu au) {
        try {
            if (null == roleId || null == au || null == au.getId()) {
                return 0;
            }
            return this.baseMapper.addAuthority2Role(au.getId(), roleId);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int removeAuthorityFromRole(String roleId, AlitaAuthoritysMenu au) {
        if (null == roleId || null == au || null == au.getId()) {
            return 0;
        }
        return this.baseMapper.removeAuthorityFromRole(au.getId(), roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int replaceAuthority2Role(String roleId, List<String> auIds) {
        if (null == roleId || CollectionUtils.isEmpty(auIds)) {
            return 0;
        }
        this.baseMapper.removeAuthoritysFromRole(roleId);
        this.baseMapper.removeAuthoritysResourcesFromRole(roleId);
        List<Map<String, String>> auList = new ArrayList<>();
        List<Map<String, String>> dsList = new ArrayList<>();
        for (String auId : auIds) {
            if(auId.startsWith(DS_ID_PREFIX)){
                // 资源权限
                Map<String, String> dsItem = new HashMap<>(2);
                dsItem.put("roleId", roleId);
                dsItem.put("dsId", auId.replace(DS_ID_PREFIX, ""));
                dsList.add(dsItem);
            } else {
                // 权限
                Map<String, String> auItem = new HashMap<>(2);
                auItem.put("roleId", roleId);
                auItem.put("auId", auId);
                auList.add(auItem);
            }

        }
        if(CollectionUtils.isNotEmpty(dsList)) {
            this.baseMapper.batchInsertAuthoritysResourcesFromRole(dsList);
        }
        if(CollectionUtils.isNotEmpty(auList)) {
            this.baseMapper.batchInsertAuthoritysFromRole(auList);
        }
        return (auList.size() + dsList.size());
    }

    @Override
    public int cleanAuthoritysFromRole(String roleId) {
        return  this.baseMapper.removeAuthoritysFromRole(roleId);
    }

    @Override
    public List<AlitaAuthoritysMenu> getRoleAllAuthoritys(String roleId) {
        return this.baseMapper.selectRoleAuthoritys(roleId);
    }

    @Override
    public List<AlitaAuthoritysMenu> selectSystemAuthoritysWithCatlogByRoleId(String roleId) {
        return this.baseMapper.selectSystemAuthoritysWithCatlogByRoleId(roleId);
    }

    @Override
    public List<AlitaAuthoritysResource> getRoleAllAuthoritysResource(String roleId) {
        return this.baseMapper.selectRoleAuthoritysResources(roleId);
    }

    @Override
    public int addAuthoritysResource2Role(String roleId, AlitaAuthoritysResource ds) {
        try {
            if (null == roleId || null == ds || null == ds.getId()) {
                return 0;
            }
            return this.baseMapper.addAuthoritysResource2Role(ds.getId(), roleId);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int removeAuthoritysResourceFromRole(String roleId, AlitaAuthoritysResource ds) {
        if (null == roleId || null == ds || null == ds.getId()) {
            return 0;
        }
        return this.baseMapper.removeAuthoritysResourceFromRole(ds.getId(), roleId);
    }

    @Override
    public int replaceAuthoritysResource2Role(String roleId, List<String> dsIds) {
        if (null == roleId || CollectionUtils.isEmpty(dsIds)) {
            return 0;
        }
        this.baseMapper.removeAuthoritysResourcesFromRole(roleId);
        List<Map<String, String>> dataList = new ArrayList<>();
        for (String dsId : dsIds) {
            Map<String, String> item = new HashMap<>(2);
            item.put("roleId", roleId);
            item.put("dsId", dsId);
            dataList.add(item);
        }
        return this.baseMapper.batchInsertAuthoritysResourcesFromRole(dataList);
    }

    @Override
    public int cleanAuthoritysResourcesFromRole(String roleId) {
        return  this.baseMapper.removeAuthoritysResourcesFromRole(roleId);
    }

    @Override
    public int countByDsId(String dsId) {
        return this.baseMapper.countByDsId(dsId);
    }

    @Override
    public int countByAuId(String auId) {
        return this.baseMapper.countByAuId(auId);
    }
}
