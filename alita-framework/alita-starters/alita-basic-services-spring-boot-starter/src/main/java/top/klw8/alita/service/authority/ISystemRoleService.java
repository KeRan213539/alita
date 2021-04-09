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

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import top.klw8.alita.entitys.authority.AlitaAuthoritysMenu;
import top.klw8.alita.entitys.authority.AlitaAuthoritysResource;
import top.klw8.alita.entitys.authority.AlitaRole;

/**
 * @ClassName: ISystemRoleService
 * @Description: 系统角色Service
 * @author klw
 * @date 2018年11月28日 下午3:48:49
 */
public interface ISystemRoleService extends IService<AlitaRole> {

    /**
     * @author klw(213539@qq.com)
     * @Description: 数据权限ID的前缀,用于保存权限时区分权限和数据权限
     */
    String DS_ID_PREFIX = "@THIS_IS_DS@";

    
    /**
     * @Title: addAuthority2Role
     * @author klw
     * @Description: 添加权限到角色中
     * @param roleId
     * @param au
     * @return
     */
    int addAuthority2Role(String roleId, AlitaAuthoritysMenu au);
    
    /**
     * @Title: removeAuthorityFromRole
     * @author klw
     * @Description: 从角色中删除指定权限
     * @param roleId
     * @param au
     * @return
     */
    int removeAuthorityFromRole(String roleId, AlitaAuthoritysMenu au);
    
    /**
     * @Title: updateAuthority2Role
     * @author klw
     * @Description: 使用传入的权限List替换角色中的权限
     * @param roleId
     * @param auIds
     * @return
     */
    int replaceAuthority2Role(String roleId, List<String> auIds);

    /**
     * @author klw(213539@qq.com)
     * @Description: 清空指定角色中的权限
     * @Date 2019/10/19 17:15
     * @param: roleId
     * @return int
     */
    int cleanAuthoritysFromRole(String roleId);

    /**
     *
     * @Author zhanglei
     * @Description 查询角色拥有的权限信息
     * @Date 16:08 2019-08-15
     * @param: roleId
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemAuthoritys>
     **/
    List<AlitaAuthoritysMenu> getRoleAllAuthoritys(String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 查询指定角色权限,包含目录信息
     * @Date 2020/7/15 8:41
     * @param: roleId
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemAuthoritys>
     */
    List<AlitaAuthoritysMenu> selectSystemAuthoritysWithCatlogByRoleId(String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 查询角色拥有的数据权限
     */
    List<AlitaAuthoritysResource> getRoleAllDataSecureds(String roleId);

    /**
     * @author klw
     * @Description: 添加数据权限到角色中
     * @param roleId
     * @param ds
     * @return
     */
    int addDataSecured2Role(String roleId, AlitaAuthoritysResource ds);

    /**
     * @author klw
     * @Description: 从角色中删除指定数据权限
     * @param roleId
     * @param ds
     * @return
     */
    int removeDataSecuredFromRole(String roleId, AlitaAuthoritysResource ds);

    /**
     * @author klw
     * @Description: 使用传入的数据权限List替换角色中的权限
     * @param roleId
     * @param dsIds
     * @return
     */
    int replaceDataSecured2Role(String roleId, List<String> dsIds);

    /**
     * @author klw(213539@qq.com)
     * @Description: 清空指定角色中的数据权限
     * @Date 2019/10/19 17:15
     * @param: roleId
     * @return int
     */
    int cleanDataSecuredsFromRole(String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 检查指定【数据权限】是否被角色关联
     * @Date 2020/5/20 14:54
     * @param: roleId
     * @param: dsId
     * @return int
     */
    int countByDsId(String dsId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 检查指定【权限】是否被角色关联
     * @Date 2020/5/20 14:54
     * @param: roleId
     * @param: dsId
     * @return int
     */
    int countByAuId(String auId);
}
