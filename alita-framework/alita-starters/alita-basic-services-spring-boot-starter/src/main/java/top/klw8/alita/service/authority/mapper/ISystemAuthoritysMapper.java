package top.klw8.alita.service.authority.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import top.klw8.alita.entitys.authority.SystemAuthoritys;

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

}
