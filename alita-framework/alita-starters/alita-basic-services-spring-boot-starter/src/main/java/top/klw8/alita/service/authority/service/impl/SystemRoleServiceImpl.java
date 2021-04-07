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
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemDataSecured;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.service.authority.ISystemRoleService;
import top.klw8.alita.service.authority.mapper.ISystemRoleMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author klw
 * @ClassName: SystemRoleServiceImpl
 * @Description: 系统角色Service_实现
 * @date 2018年11月28日 下午3:53:01
 */
@Slf4j
@Service
public class SystemRoleServiceImpl extends ServiceImpl<ISystemRoleMapper, SystemRole> implements ISystemRoleService {

    @Override
    public int addAuthority2Role(String roleId, SystemAuthoritys au) {
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
    public int removeAuthorityFromRole(String roleId, SystemAuthoritys au) {
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
        this.baseMapper.removeDataSecuredsFromRole(roleId);
        List<Map<String, String>> auList = new ArrayList<>();
        List<Map<String, String>> dsList = new ArrayList<>();
        for (String auId : auIds) {
            if(auId.startsWith(DS_ID_PREFIX)){
                // 数据权限
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
            this.baseMapper.batchInsertDataSecuredsFromRole(dsList);
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
    public List<SystemAuthoritys> getRoleAllAuthoritys(String roleId) {
        return this.baseMapper.selectRoleAuthoritys(roleId);
    }

    @Override
    public List<SystemAuthoritys> selectSystemAuthoritysWithCatlogByRoleId(String roleId) {
        return this.baseMapper.selectSystemAuthoritysWithCatlogByRoleId(roleId);
    }

    @Override
    public List<SystemDataSecured> getRoleAllDataSecureds(String roleId) {
        return this.baseMapper.selectRoleDataSecureds(roleId);
    }

    @Override
    public int addDataSecured2Role(String roleId, SystemDataSecured ds) {
        try {
            if (null == roleId || null == ds || null == ds.getId()) {
                return 0;
            }
            return this.baseMapper.addDataSecured2Role(ds.getId(), roleId);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int removeDataSecuredFromRole(String roleId, SystemDataSecured ds) {
        if (null == roleId || null == ds || null == ds.getId()) {
            return 0;
        }
        return this.baseMapper.removeDataSecuredFromRole(ds.getId(), roleId);
    }

    @Override
    public int replaceDataSecured2Role(String roleId, List<String> dsIds) {
        if (null == roleId || CollectionUtils.isEmpty(dsIds)) {
            return 0;
        }
        this.baseMapper.removeDataSecuredsFromRole(roleId);
        List<Map<String, String>> dataList = new ArrayList<>();
        for (String dsId : dsIds) {
            Map<String, String> item = new HashMap<>(2);
            item.put("roleId", roleId);
            item.put("dsId", dsId);
            dataList.add(item);
        }
        return this.baseMapper.batchInsertDataSecuredsFromRole(dataList);
    }

    @Override
    public int cleanDataSecuredsFromRole(String roleId) {
        return  this.baseMapper.removeDataSecuredsFromRole(roleId);
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
