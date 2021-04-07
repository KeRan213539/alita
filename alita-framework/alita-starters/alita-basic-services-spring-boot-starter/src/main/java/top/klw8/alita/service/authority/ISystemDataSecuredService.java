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
import top.klw8.alita.entitys.authority.SystemDataSecured;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ISystemDataSecuredService
 * @Description: 数据权限表 Service
 * @date 2020/5/13 16:45
 */
public interface ISystemDataSecuredService extends IService<SystemDataSecured> {

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据资源标识和所属权限ID查找数据权限
     * @Date 2020/5/13 17:24
     * @param: resource
     * @param: auId
     * @return top.klw8.alita.entitys.authority.SystemDataSecured
     */
    SystemDataSecured findByResourceAndAuId(String resource, String auId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据所属权限ID查找数据权限, 传空查全局数据权限
     * @Date 2020/8/4 16:01
     * @param: auId
     * @param: appTag
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemDataSecured>
     */
    List<SystemDataSecured> findByAuId(String auId, String appTag);

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据角色ID和所属权限ID查找数据权限, 权限ID传空查全局数据权限
     * @Date 2020/8/4 16:31
     * @param: roleId 必传
     * @param: auId 可选
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemDataSecured>
     */
    List<SystemDataSecured> findByRoleIdAndAuId(String roleId, String auId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 检查数据权限中是否有属于指定权限的
     */
    int countByAuId(String auId);


}
