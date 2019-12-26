package top.klw8.alita.service.authority.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import top.klw8.alita.entitys.authority.SystemAuthoritys;

import java.util.List;

/**
 * @ClassName: ISystemAuthoritysMapper
 * @Description: 系统权限DAO
 * @author klw
 * @date 2018年11月28日 下午3:39:48
 */
public interface ISystemAuthoritysMapper extends BaseMapper<SystemAuthoritys> {

    /**
     * @author klw(213539@qq.com)
     * @Description: 移除指定权限与所有角色的关联
     * @Date 2019/10/23 15:59
     * @param: auId
     * @return int
     */
    @Delete("DELETE FROM sys_role_has_authoritys WHERE authoritys_id = #{auId}")
    int removeAuthorityFromRole(String auId);

    /**
     * @author xp
     * @Description: 查询菜单权限列表
     * @Date 2019/12/26 16:11
     * @param: authorityName
     * @param: authorityType
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemAuthoritys>
     */
    @Select("<script> " +
            "select a.*,c.catlog_name from sys_authoritys a left join sys_authoritys_catlog c on a.catlog_id=c.id " +
            " where a.authority_type =#{authorityType} " +
            " <if test=\"authorityName != null and authorityName != '' \">" +
            "       and a.authority_name like CONCAT('%',#{authorityName,jdbcType=VARCHAR},'%') " +
            "</if>" +
            "</script>")
    IPage<SystemAuthoritys> selectSystemAuthoritysMenuList(Page page, String authorityName, String authorityType);
}
