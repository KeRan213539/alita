package top.klw8.alita.service.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
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
     * @Description: 删除指定角色下的所有权限
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

}
