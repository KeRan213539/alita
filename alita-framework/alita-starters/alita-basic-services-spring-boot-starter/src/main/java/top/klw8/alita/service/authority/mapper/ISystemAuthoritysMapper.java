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
     * @author klw(213539@qq.com)
     * @Description: 查询权限列表
     * @Date 2020/7/14 15:00
     * @param: page
     * @param: authorityName
     * @param: authorityType
     * @return com.baomidou.mybatisplus.core.metadata.IPage<top.klw8.alita.entitys.authority.SystemAuthoritys>
     */
    @Select("<script> " +
            "select a.*,c.catlog_name from sys_authoritys a left join sys_authoritys_catlog c on a.catlog_id=c.id " +
            " where 1 = 1 " +
            "<if test=\"authorityType != null and authorityType != '' \">" +
            "    and a.authority_type =#{authorityType}" +
            "</if>" +
            "<if test=\"appTag != null and appTag != '' \">" +
            "    and a.app_tag = #{appTag}" +
            "</if>" +
            "<if test=\"authorityName != null and authorityName != '' \">" +
            "    and a.authority_name like CONCAT('%',#{authorityName,jdbcType=VARCHAR},'%') " +
            "</if>" +
            "<if test=\"authorityAction != null and authorityAction != '' \">" +
            "    and a.authority_action like CONCAT('%',#{authorityAction,jdbcType=VARCHAR},'%') " +
            "</if>" +
            "<if test=\"catlogName != null and catlogName != '' \">" +
            "    and c.catlog_name like CONCAT('%',#{catlogName,jdbcType=VARCHAR},'%') " +
            "</if>" +
            "order by show_index asc" +
            "</script>")
    IPage<SystemAuthoritys> selectSystemAuthoritysList(Page page, String authorityName,
                                                       String authorityType, String authorityAction,
                                                       String catlogName, String appTag);

    /**
     * @author klw(213539@qq.com)
     * @Description: 查询全部权限,包含目录信息
     * @Date 2020/7/17 14:46
     * @param: appTag
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemAuthoritys>
     */
    @Select("<script> " +
            "select a.*,c.catlog_name from sys_authoritys a left join sys_authoritys_catlog c on a.catlog_id=c.id where 1 = 1" +
            "<if test=\"appTag != null and appTag != '' \">" +
            "    and a.app_tag = #{appTag} " +
            "</if>" +
            "order by show_index asc" +
            "</script>")
    List<SystemAuthoritys> selectAllSystemAuthoritysWithCatlog(String appTag);

}
