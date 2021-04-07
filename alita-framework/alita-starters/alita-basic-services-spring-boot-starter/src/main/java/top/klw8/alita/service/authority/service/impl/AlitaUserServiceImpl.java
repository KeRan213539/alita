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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.authority.IAlitaUserService;
import top.klw8.alita.service.authority.mapper.IAlitaUserMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author klw
 * @ClassName: AlitaUserServiceImpl
 * @Description: 用户服务
 * @date 2018年11月9日 下午5:32:24
 */
@Slf4j
@Service
public class AlitaUserServiceImpl extends ServiceImpl<IAlitaUserMapper, AlitaUserAccount> implements IAlitaUserService {

    @Override
    public int addRole2User(String userId, String roleId) {
        try {
            if (StringUtils.isBlank(userId) || StringUtils.isBlank(roleId)) {
                return 0;
            }
            return this.getBaseMapper().addRole2User(userId, roleId);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int removeRoleFromUser(String userId, SystemRole role) {
        if (null == userId || null == role || null == role.getId()) {
            return 0;
        }
        return this.getBaseMapper().removeRoleFromUser(userId, role.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int replaceRole2User(String userId, List<SystemRole> roleList) {
        if (null == userId || CollectionUtils.isEmpty(roleList)) {
            return 0;
        }
        this.getBaseMapper().removeRolesFromUser(userId);
        List<Map<String, String>> dataList = new ArrayList<>();
        for (SystemRole role : roleList) {
            Map<String, String> item = new HashMap<>(2);
            item.put("userId", userId);
            item.put("roleId", role.getId());
            item.put("appTag", role.getAppTag());
            dataList.add(item);
        }
        return this.getBaseMapper().batchInsertRoles4User(dataList);
    }

    @Override
    public List<SystemRole> getUserAllRoles(String userId, String appTag) {
        return this.getBaseMapper().selectUserAllRoles(userId, appTag);
    }

    @Override
    public List<AlitaUserAccount> getUserByRoleId(String roleId) {
        return this.getBaseMapper().selectUserByRoleId(roleId);
    }

}
