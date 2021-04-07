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
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.user.AlitaUserAccount;

import java.util.List;
import java.util.Map;


/**
 * @ClassName: IAlitaUserMapper
 * @Description: 用户 dao
 * @author klw
 * @date 2018年11月9日 下午5:27:40
 */
public interface IAlitaUserMapper extends BaseMapper<AlitaUserAccount> {

//    @Select("select * from demo where full_name = #{fullName}")
//    @Results({
//            @Result(property = "fullName", column = "full_name")
//    })
//    List<DemoEntity> selectByFullName(String fullName);

    /**
     * @author klw(213539@qq.com)
     * @Description: 添加用户与角色的关联
     * @Date 2019/8/12 17:07
     * @param: userId
     * @param: roleId
     * @return int
     */
    @Insert("INSERT INTO sys_user_has_role(user_id, role_id, app_tag) " +
            "VALUES(" +
            "#{userId}, #{roleId}, " +
            "(SELECT app_tag FROM sys_role WHERE id = #{roleId})" +
            ")")
    int addRole2User(String userId, String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 移除用户与角色的关联
     * @Date 2019/8/12 17:08
     * @param: userId
     * @param: roleId
     * @return int
     */
    @Delete("DELETE FROM sys_user_has_role WHERE user_id = #{userId} AND role_id = #{roleId}")
    int removeRoleFromUser(String userId, String roleId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 删除指定用户下的所有角色
     * @Date 2019/8/13 8:49
     * @param: userId
     * @return int
     */
    @Delete("DELETE FROM sys_user_has_role WHERE user_id = #{userId}")
    int removeRolesFromUser(String userId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 批量关联多个角色到指定用户
     * @Date 2019/8/13 9:37
     * @param: list
     * @return int
     */
    @Insert("<script>" +
            "INSERT INTO sys_user_has_role(user_id, role_id, app_tag) VALUES" +
            "<foreach collection =\"list\" item=\"item\" index= \"index\" separator =\",\"> " +
            "(#{item.userId}, #{item.roleId}, #{item.appTag})" +
            "</foreach >" +
            "</script>")
    int batchInsertRoles4User(List<Map<String, String>> list);

    /**
     *
     * @Author zhanglei
     * @Description 查询用户的所有角色
     * @Date 15:27 2019-08-15
     * @param: userId
     * @return java.util.List<java.lang.String>
     **/
    @Select("<script> " +
            "select * from sys_role where id in (" +
                "SELECT role_id from sys_user_has_role WHERE user_id = #{userId}" +
                "<if test=\"appTag != null and appTag != '' \">" +
                "    and app_tag = #{appTag} " +
                "</if>" +
            ")"
            + "</script>")
    List<SystemRole> selectUserAllRoles(String userId, String appTag);

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据传入的roleId查找拥有这个角色的用户
     * @Date 2019/10/19 16:41
     * @param: roleId
     * @return java.util.List<top.klw8.alita.entitys.user.AlitaUserAccount>
     */
    @Select("select * from sys_user_account where id in (SELECT user_id from sys_user_has_role WHERE role_id = #{roleId})")
    List<AlitaUserAccount> selectUserByRoleId(String roleId);

}
