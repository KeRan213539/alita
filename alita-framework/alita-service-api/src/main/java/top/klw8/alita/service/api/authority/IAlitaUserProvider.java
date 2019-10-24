package top.klw8.alita.service.api.authority;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.result.JsonResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: IAlitaUserProvider
 * @Description: 用户相关dubbo提供者
 * @date 2019/8/14 14:15
 */
public interface IAlitaUserProvider {

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据用户ID查找用户
     * @Date 2019/8/14 14:19
     * @param: userId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.entitys.user.AlitaUserAccount>
     */
    CompletableFuture<AlitaUserAccount> findUserById(String userId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据用户名(帐号)查找用户
     * @Date 2019/8/14 16:31
     * @param: userName
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.entitys.user.AlitaUserAccount>
     */
    CompletableFuture<AlitaUserAccount> findUserByName(String userName);

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据用户手机号查找用户
     * @Date 2019/8/14 16:31
     * @param: userPhoneNum
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.entitys.user.AlitaUserAccount>
     */
    CompletableFuture<AlitaUserAccount> findUserByPhoneNum(String userPhoneNum);

    /*
     * @author klw(213539@qq.com)
     * @Description: 根据用户id获取用户的权限菜单
     * @Date 2019/10/9 15:11
     * @param: userId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.entitys.user.AlitaUserAccount>
     */
    CompletableFuture<JsonResult> findUserAuthorityMenus(String userId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据条件查询用户列表
     * @Date 2019/10/16 9:20
     * @param: user
     * @param: createDateBegin
     * @param: createDateEnd
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> userList(AlitaUserAccount user, LocalDateTime createDateBegin, LocalDateTime createDateEnd, Page<AlitaUserAccount> page);

    /**
     * @author klw(213539@qq.com)
     * @Description: 保存用户拥有的角色(替换原有角色)
     * @Date 2019/10/15 17:02
     * @param: userId
     * @param: roleIds
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> saveUserRoles(String userId, List<String> roleIds);

    /**
     * @author klw(213539@qq.com)
     * @Description: 新增用户
     * @Date 2019/10/16 10:00
     * @param: user
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> addSaveUser(AlitaUserAccount user);

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据用户ID修改指定用户的密码
     * @Date 2019/10/16 16:24
     * @param: userId
     * @param: oldPwd
     * @param: newPwd
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     */
    CompletableFuture<JsonResult> changeUserPassword(String userId, String oldPwd, String newPwd);

}
