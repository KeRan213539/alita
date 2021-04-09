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
package top.klw8.alita.service.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import top.klw8.alita.entitys.authority.AlitaAuthoritysMenu;
import top.klw8.alita.entitys.authority.AlitaAuthoritysResource;
import top.klw8.alita.entitys.authority.AlitaRole;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ISystemRoleMapper
 * @Description: 系统角色DAO
 * @author klw
 * @date 2018年11月28日 下午3:40:27
 */
public interface ISystemRoleMapper extends BaseMapper<AlitaRole> {

    /**
     * @author klw(213539@qq.com)
     * @Description: 添加权限与角色的关联
     * @Date 2019/8/12 17:07
     * @param: auId
     * @param: roleId
     * @return int
     */
    @Insert("INSERT INTO alita_role_has_authoritys(role_id, authoritys_id) VALUES(#{roleId}, #{auId})")
    int addAuthority2Role(String auId, String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 移除权限与角色的关联
     * @Date 2019/8/12 17:08
     * @param: auId
     * @param: roleId
     * @return int
     */
    @Delete("DELETE FROM alita_role_has_authoritys WHERE role_id = #{roleId} AND authoritys_id = #{auId}")
    int removeAuthorityFromRole(String auId, String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 清空指定角色中的所有权限
     * @Date 2019/8/13 8:49
     * @param: roleId
     * @return int
     */
    @Delete("DELETE FROM alita_role_has_authoritys WHERE role_id = #{roleId}")
    int removeAuthoritysFromRole(String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 批量关联多个权限到指定角色
     * @Date 2019/8/13 9:37
     * @param: list
     * @return int
     */
    @Insert("<script>" +
            "INSERT INTO alita_role_has_authoritys(role_id, authoritys_id) VALUES" +
            "<foreach collection =\"list\" item=\"item\" index= \"index\" separator =\",\"> " +
            "(#{item.roleId}, #{item.auId})" +
            "</foreach >" +
            "</script>")
    int batchInsertAuthoritysFromRole(List<Map<String, String>> list);

    /**
     *
     * @Author zhanglei
     * @Description 查询角色拥有的权限信息
     * @Date 16:07 2019-08-15
     * @param: roleId
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemAuthoritys>
     **/
    @Select("select * from alita_authoritys_menu where id in (select authoritys_id from alita_role_has_authoritys where role_id = #{roleId})")
    List<AlitaAuthoritysMenu> selectRoleAuthoritys(String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 查询角色拥有的数据权限
     * @Date 2020/4/30 15:30
     * @param: roleId
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemDataSecured>
     */
    @Select("select ds.*, au.authority_action as authority_url " +
            " from alita_authoritys_resource ds" +
            " left join alita_authoritys_menu au" +
            " on au.id = ds.authoritys_id" +
            " where ds.id in (select data_secured_id from alita_role_has_resource_secured where role_id = #{roleId})")
    List<AlitaAuthoritysResource> selectRoleDataSecureds(String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 添加数据权限与角色的关联
     * @Date 2020/5/13 15:42
     * @param: dsId
     * @param: roleId
     * @return int
     */
    @Insert("INSERT INTO alita_role_has_resource_secured(role_id, data_secured_id) VALUES(#{roleId}, #{dsId})")
    int addDataSecured2Role(String dsId, String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 移除数据权限与角色的关联
     * @Date 2020-05-13 15:41:59
     * @param: dsId
     * @param: roleId
     * @return int
     */
    @Delete("DELETE FROM alita_role_has_resource_secured WHERE role_id = #{roleId} AND data_secured_id = #{dsId}")
    int removeDataSecuredFromRole(String dsId, String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 清空指定角色中的所有数据权限
     * @Date 2020-05-13 15:41:59
     * @param: roleId
     * @return int
     */
    @Delete("DELETE FROM alita_role_has_resource_secured WHERE role_id = #{roleId}")
    int removeDataSecuredsFromRole(String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 批量关联多个数据权限到指定角色
     * @Date 2020-05-13 15:41:59
     * @param: list
     * @return int
     */
    @Insert("<script>" +
            "INSERT INTO alita_role_has_resource_secured(role_id, data_secured_id) VALUES" +
            "<foreach collection =\"list\" item=\"item\" index= \"index\" separator =\",\"> " +
            "(#{item.roleId}, #{item.dsId})" +
            "</foreach >" +
            "</script>")
    int batchInsertDataSecuredsFromRole(List<Map<String, String>> list);

    /**
     * @author klw(213539@qq.com)
     * @Description: 检查指定【数据权限】是否被角色关联
     * @Date 2020/5/20 14:54
     * @param: roleId
     * @param: dsId
     * @return int
     */
    @Select("select count(1) from alita_role_has_resource_secured WHERE data_secured_id = #{dsId}")
    int countByDsId(String dsId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 检查指定【权限】是否被角色关联
     * @Date 2020/5/20 14:54
     * @param: roleId
     * @param: dsId
     * @return int
     */
    @Select("select count(1) from alita_role_has_authoritys WHERE authoritys_id = #{auId}")
    int countByAuId(String auId);


    /**
     * @author klw(213539@qq.com)
     * @Description: 查询指定角色权限,包含目录信息
     * @Date 2020/5/19 15:28
     * @param:
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemAuthoritys>
     */
    @Select("select a.*,c.catlog_name from alita_authoritys_menu a left join alita_authoritys_catlog c on a.catlog_id=c.id " +
            "where a.id in (select authoritys_id from alita_role_has_authoritys where role_id=#{roleId})")
    List<AlitaAuthoritysMenu> selectSystemAuthoritysWithCatlogByRoleId(String roleId);

}
