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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.klw8.alita.entitys.authority.AlitaAuthoritysMenu;

import java.util.List;

/**
 * @author klw
 * @ClassName: ISystemAuthoritysService
 * @Description: 系统权限Service
 * @date 2018年11月28日 下午3:48:18
 */
public interface ISystemAuthoritysService extends IService<AlitaAuthoritysMenu> {

    /**
     * @param action
     * @return
     * @Title: findByAuAction
     * @author klw
     * @Description: 根据authorityAction 查找
     */
    AlitaAuthoritysMenu findByAuActionAndAppTag(String action, String appTag);

    /**
     * @return int
     * @author klw(213539 @ qq.com)
     * @Description: 移除指定权限与所有角色的关联
     * @Date 2019/10/23 15:59
     * @param: auId
     */
    int removeAuthorityFromRole(String auId);

    /**
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemAuthoritys>
     * @author xp
     * @Description: 查询菜单权限列表
     * @Date 2019/12/26 15:55
     * @param: authorityName
     * @param: authorityType
     */
    IPage<AlitaAuthoritysMenu> selectSystemAuthoritysList(Page page, String authorityName,
                                                          String authorityType, String authorityAction,
                                                          String catlogName, String appTag);

    /**
     * @author klw(213539@qq.com)
     * @Description: 查询全部权限, 包含目录信息
     * @Date 2020/7/17 14:46
     * @param: appTag
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemAuthoritys>
     */
    List<AlitaAuthoritysMenu> selectAllSystemAuthoritysWithCatlog(String appTag);

}
