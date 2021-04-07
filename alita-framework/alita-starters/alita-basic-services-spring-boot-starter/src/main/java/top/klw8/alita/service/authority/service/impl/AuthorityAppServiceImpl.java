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
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.klw8.alita.entitys.authority.*;
import top.klw8.alita.service.authority.*;
import top.klw8.alita.service.authority.mapper.IAuthorityAppMapper;
import top.klw8.alita.service.result.JsonResult;

import java.util.List;
import java.util.Objects;

/**
 * @author zhaozheng
 * @description:
 * @date: 2020-07-16
 */
@Slf4j
@Service
public class AuthorityAppServiceImpl extends ServiceImpl<IAuthorityAppMapper, SystemAuthoritysApp> implements IAuthorityAppService {

    @Autowired
    private ISystemAuthoritysService authorityService;

    @Autowired
    private ISystemAuthoritysCatlogService catLogService;

    @Autowired
    private ISystemDataSecuredService dataSecuredService;

    @Autowired
    private ISystemRoleService roleService;


    @Override
    public JsonResult addAuthorityApp(SystemAuthoritysApp authorityApp) {
        SystemAuthoritysApp app = this.getById(authorityApp.getAppTag());
        if(!Objects.isNull(app)){
            return JsonResult.failed(String.format("当前 app_tag 【%S】 已存在", authorityApp.getAppTag()));
        }
        if(this.save(authorityApp)){
            return JsonResult.successfu();
        }
        return JsonResult.failed(String.format("当前 app_tag 【%S】 保存失败", authorityApp.getAppTag()));
    }

    @Override
    public JsonResult deleteAuthorityApp(String appTag) {
        SystemAuthoritysApp app = this.getById(appTag);
        if(Objects.isNull(app)){
            return JsonResult.failed(String.format("当前 app_tag 【%S】 不存在", appTag));
        }

        //查询是否有权限关联app
        List<SystemAuthoritys> authList = authorityService.list(new QueryWrapper<SystemAuthoritys>().lambda().
                eq(StringUtils.isNotBlank(appTag), SystemAuthoritys::getAppTag, appTag));
        //查询是否有权限目录关联app
        List<SystemAuthoritysCatlog> catLogList = catLogService.list(new QueryWrapper<SystemAuthoritysCatlog>().lambda().
                eq(StringUtils.isNotBlank(appTag), SystemAuthoritysCatlog::getAppTag, appTag));
        //查询是否有数据权限目录关联app
        List<SystemDataSecured> dataSecuredList = dataSecuredService.list(new QueryWrapper<SystemDataSecured>().lambda().
                eq(StringUtils.isNotBlank(appTag), SystemDataSecured::getAppTag, appTag));
        List<SystemRole> roleList = roleService.list(new QueryWrapper<SystemRole>().lambda().
                eq(StringUtils.isNotBlank(appTag), SystemRole::getAppTag, appTag));

        if(!authList.isEmpty() || !catLogList.isEmpty() || !dataSecuredList.isEmpty() || !roleList.isEmpty()){
            return JsonResult.failed(String.format("当前 app_tag 【%S】 存在权限、角色相关关联，不可删除", appTag));
        }
        if(removeById(appTag)){
            return JsonResult.successfu();
        }
        return JsonResult.failed(String.format("当前 app_tag 【%S】 删除失败", appTag));
    }

    @Override
    public JsonResult updateAuthorityApp(SystemAuthoritysApp authorityApp) {
        SystemAuthoritysApp app = this.getById(authorityApp.getAppTag());
        if(Objects.isNull(app)){
            return JsonResult.failed(String.format("当前 app_tag 【%S】 不存在", authorityApp.getAppTag()));
        }
        if(this.updateById(authorityApp)){
            return JsonResult.successfu();
        }
        return JsonResult.failed(String.format("当前 app_tag 【%S】 修改失败", authorityApp.getAppTag()));
    }

    @Override
    public JsonResult<SystemAuthoritysApp> findAuthorityApp(SystemAuthoritysApp authorityApp) {
        return JsonResult.successfu(this.getOne(new QueryWrapper<SystemAuthoritysApp>().lambda().
                eq(StringUtils.isNotBlank(authorityApp.getAppTag()), SystemAuthoritysApp::getAppTag, authorityApp.getAppTag()).
                eq(StringUtils.isNotBlank(authorityApp.getAppName()), SystemAuthoritysApp::getAppName, authorityApp.getAppName()).
                eq(StringUtils.isNotBlank(authorityApp.getRemark()), SystemAuthoritysApp::getRemark, authorityApp.getRemark())));
    }

}
