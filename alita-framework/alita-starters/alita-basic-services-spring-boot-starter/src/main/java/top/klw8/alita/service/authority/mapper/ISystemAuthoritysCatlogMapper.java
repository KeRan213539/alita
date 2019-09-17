package top.klw8.alita.service.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ISystemAuthoritysCatlogMapper
 * @Description: 权限的目录DAO
 * @author klw
 * @date 2018年11月28日 下午3:40:05
 */
public interface ISystemAuthoritysCatlogMapper extends BaseMapper<SystemAuthoritysCatlog> {

    /**
     * @author klw(213539@qq.com)
     * @Description: 添加权限到指定目录
     * @Date 2019/8/13 10:03
     * @param: catlogId
     * @param: auId
     * @return int
     */
    @Insert("INSERT INTO sys_catlog_has_authoritys(catlog_id, authoritys_id) VALUES(#{catlogId}, #{auId})")
    int addAuthority2Catlog(String catlogId, String auId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 移除权限与目录的关联
     * @Date 2019/8/12 17:08
     * @param: catlogId
     * @param: auId
     * @return int
     */
    @Delete("DELETE FROM sys_catlog_has_authoritys WHERE catlog_id = #{catlogId} AND authoritys_id = #{auId}")
    int removeAuthorityFromCatlog(String catlogId, String auId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 删除指定目录下的所有权限
     * @Date 2019/8/13 8:49
     * @param: userId
     * @return int
     */
    @Delete("DELETE FROM sys_catlog_has_authoritys WHERE catlog_id = #{catlogId}")
    int removeAuthoritysFromCatlog(String catlogId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 批量关联多个角色到指定用户
     * @Date 2019/8/13 9:37
     * @param: list
     * @return int
     */
    @Insert("<script>" +
            "INSERT INTO sys_catlog_has_authoritys(catlog_id, authoritys_id) VALUES" +
            "<foreach collection =\"list\" item=\"item\" index= \"index\" separator =\",\"> " +
            "(#{item.catlogId}, #{item.auId})" +
            "</foreach >" +
            "</script>")
    int batchInsertAuthoritys4Catlog(List<Map<String, String>> list);

}
