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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.klw8.alita.entitys.demo.mybatis.ExtUserInfo;
import top.klw8.alita.service.demo.service.demo.IExtUserService;
import top.klw8.alita.utils.UUIDUtil;
import top.klw8.alita.service.demo.mapper.demo.IExtUserMapper;

import java.util.List;

/**
 * 用户扩展信息服务实现
 * 2019-08-19 15:30
 */
@Slf4j
@Service
public class ExtUserServiceImpl extends ServiceImpl<IExtUserMapper, ExtUserInfo> implements IExtUserService {

    @Override
    public boolean addUserExtInfo(ExtUserInfo extUserInfo) {
        extUserInfo.setUserId(UUIDUtil.getRandomUUIDString());
        int result = this.getBaseMapper().insert(extUserInfo);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteUserExtInfo(String userId) {
        QueryWrapper<ExtUserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        int result = this.getBaseMapper().delete(wrapper);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateUserExtInfo(ExtUserInfo extUserInfo) {
        QueryWrapper<ExtUserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", extUserInfo.getUserId());
        int result = this.getBaseMapper().update(extUserInfo, wrapper);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ExtUserInfo getExtByUserId(String userId) {
        QueryWrapper<ExtUserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return this.getOne(queryWrapper);
    }

    @Override
    public ExtUserInfo getExtByEmail(String userEmail) {
        QueryWrapper<ExtUserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_email", userEmail);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<ExtUserInfo> getExtsByLevel(Integer level) {
        QueryWrapper<ExtUserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_level", level);
        return this.list(queryWrapper);
    }

}
