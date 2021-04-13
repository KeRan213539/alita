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
package top.klw8.alita.service.authority;

import com.baomidou.mybatisplus.extension.service.IService;
import top.klw8.alita.entitys.authority.AlitaAuthoritysResource;

import java.util.List;

/**
 * 数据权限表 Service
 * 2020/5/13 16:45
 */
public interface ISystemDataSecuredService extends IService<AlitaAuthoritysResource> {

    /**
     * 根据资源标识和所属权限ID查找数据权限
     * 2020/5/13 17:24
     * @param: resource
     * @param: auId
     * @return top.klw8.alita.entitys.authority.SystemDataSecured
     */
    AlitaAuthoritysResource findByResourceAndAuId(String resource, String auId);

    /**
     * 根据所属权限ID查找数据权限, 传空查全局数据权限
     * 2020/8/4 16:01
     * @param: auId
     * @param: appTag
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemDataSecured>
     */
    List<AlitaAuthoritysResource> findByAuId(String auId, String appTag);

    /**
     * 根据角色ID和所属权限ID查找数据权限, 权限ID传空查全局数据权限
     * 2020/8/4 16:31
     * @param: roleId 必传
     * @param: auId 可选
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemDataSecured>
     */
    List<AlitaAuthoritysResource> findByRoleIdAndAuId(String roleId, String auId);

    /**
     * 检查数据权限中是否有属于指定权限的
     */
    int countByAuId(String auId);


}
