package top.klw8.alita.service.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemDataSecured;
import top.klw8.alita.entitys.authority.SystemRole;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ISystemRoleMapper
 * @Description: 系统角色DAO
 * @author klw
 * @date 2018年11月28日 下午3:40:27
 */
public interface ISystemRoleMapper extends BaseMapper<SystemRole> {

    /**
     * @author klw(213539@qq.com)
     * @Description: 添加权限与角色的关联
     * @Date 2019/8/12 17:07
     * @param: auId
     * @param: roleId
     * @return int
     */
    @Insert("INSERT INTO sys_role_has_authoritys(role_id, authoritys_id) VALUES(#{roleId}, #{auId})")
    int addAuthority2Role(String auId, String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 移除权限与角色的关联
     * @Date 2019/8/12 17:08
     * @param: auId
     * @param: roleId
     * @return int
     */
    @Delete("DELETE FROM sys_role_has_authoritys WHERE role_id = #{roleId} AND authoritys_id = #{auId}")
    int removeAuthorityFromRole(String auId, String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 清空指定角色中的所有权限
     * @Date 2019/8/13 8:49
     * @param: roleId
     * @return int
     */
    @Delete("DELETE FROM sys_role_has_authoritys WHERE role_id = #{roleId}")
    int removeAuthoritysFromRole(String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 批量关联多个权限到指定角色
     * @Date 2019/8/13 9:37
     * @param: list
     * @return int
     */
    @Insert("<script>" +
            "INSERT INTO sys_role_has_authoritys(role_id, authoritys_id) VALUES" +
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
    @Select("select * from sys_authoritys where id in (select authoritys_id from sys_role_has_authoritys where role_id = #{roleId})")
    List<SystemAuthoritys> selectRoleAuthoritys(String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 查询角色拥有的数据权限
     * @Date 2020/4/30 15:30
     * @param: roleId
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemDataSecured>
     */
    @Select("select ds.*, au.authority_action as authority_url " +
            " from sys_data_secured ds" +
            " left join sys_authoritys au" +
            " on au.id = ds.authoritys_id" +
            " where ds.id in (select data_secured_id from sys_role_has_data_secured where role_id = #{roleId})")
    List<SystemDataSecured> selectRoleDataSecureds(String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 添加数据权限与角色的关联
     * @Date 2020/5/13 15:42
     * @param: dsId
     * @param: roleId
     * @return int
     */
    @Insert("INSERT INTO sys_role_has_data_secured(role_id, data_secured_id) VALUES(#{roleId}, #{dsId})")
    int addDataSecured2Role(String dsId, String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 移除数据权限与角色的关联
     * @Date 2020-05-13 15:41:59
     * @param: dsId
     * @param: roleId
     * @return int
     */
    @Delete("DELETE FROM sys_role_has_data_secured WHERE role_id = #{roleId} AND data_secured_id = #{dsId}")
    int removeDataSecuredFromRole(String dsId, String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 清空指定角色中的所有数据权限
     * @Date 2020-05-13 15:41:59
     * @param: roleId
     * @return int
     */
    @Delete("DELETE FROM sys_role_has_data_secured WHERE role_id = #{roleId}")
    int removeDataSecuredsFromRole(String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 批量关联多个数据权限到指定角色
     * @Date 2020-05-13 15:41:59
     * @param: list
     * @return int
     */
    @Insert("<script>" +
            "INSERT INTO sys_role_has_data_secured(role_id, data_secured_id) VALUES" +
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
    @Select("select count(1) from sys_role_has_data_secured WHERE data_secured_id = #{dsId}")
    int countByDsId(String dsId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 检查指定【权限】是否被角色关联
     * @Date 2020/5/20 14:54
     * @param: roleId
     * @param: dsId
     * @return int
     */
    @Select("select count(1) from sys_role_has_authoritys WHERE authoritys_id = #{auId}")
    int countByAuId(String auId);


}
