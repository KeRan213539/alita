package top.klw8.alita.service.demo.providers.user;

import top.klw8.alita.service.result.JsonResult;

import java.util.concurrent.CompletableFuture;

/**
 * @ClassName IExtUserProvider
 * @Description 用户扩展信息dubbo提供者定义
 * @Author ZhanglLei
 * @Date 2019-08-19 14:40
 * @Version 1.0
 */
public interface IExtUserProvider {

    /**
     *
     * @Author zhanglei
     * @Description 用于获取用户的扩展信息
     * @Date 14:42 2019-08-19
     * @param: userId
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.entitys.demo.mybatis.ExtUserInfo>
     **/
    CompletableFuture<JsonResult> findExtByUserId(String userId);

    /**
     *
     * @Author zhanglei
     * @Description 根据emial获取用户扩展信息
     * @Date 14:46 2019-08-19
     * @param: userEmail
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     **/
    CompletableFuture<JsonResult> findExtByEmail(String userEmail);

    /**
     *
     * @Author zhanglei
     * @Description 根据等级获取用户扩展信息
     * @Date 14:47 2019-08-19
     * @param: userLevel
     * @return java.util.concurrent.CompletableFuture<top.klw8.alita.service.result.JsonResult>
     **/
    CompletableFuture<JsonResult> findExtsByLevel(Integer userLevel);

}
