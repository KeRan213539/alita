package top.klw8.alita.service.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import top.klw8.alita.entitys.authority.SystemDataSecured;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ISystemDataSecuredMapper
 * @Description: 数据权限表 mapper
 * @date 2020/5/13 16:45
 */
public interface ISystemDataSecuredMapper extends BaseMapper<SystemDataSecured> {

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据权限ID查询数量
     * @Date 2020/5/20 15:33
     * @param: auId
     * @return int
     */
    @Select("select count(1) from sys_data_secured WHERE authoritys_id = #{auId}")
    int countByAuId(String auId);

    @Select("<script> " +
            "SELECT * FROM sys_role_has_data_secured rhds " +
            "LEFT JOIN sys_data_secured ds " +
            "on ds.id = rhds.data_secured_id " +
            "where rhds.role_id = #{roleId} " +
            "<choose>" +
            "<when test=\"auId != null and auId != '' \">" +
                "and ds.authoritys_id = #{auId} " +
            "</when>" +
            "<otherwise>" +
                "and ds.authoritys_id IS NULL " +
            "</otherwise>" +
            "</choose>" +
            "</script>")
    List<SystemDataSecured> findByRoleIdAndAuId(String roleId, String auId);

}
