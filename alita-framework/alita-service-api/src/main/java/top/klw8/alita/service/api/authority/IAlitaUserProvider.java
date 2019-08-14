package top.klw8.alita.service.api.authority;

import top.klw8.alita.entitys.user.AlitaUserAccount;

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

}
