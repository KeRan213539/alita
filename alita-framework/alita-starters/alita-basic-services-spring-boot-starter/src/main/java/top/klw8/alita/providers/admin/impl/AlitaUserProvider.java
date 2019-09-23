package top.klw8.alita.providers.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.service.authority.IAlitaUserService;
import top.klw8.alita.starter.service.common.ServiceContext;

import java.util.concurrent.CompletableFuture;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AlitaUserProvider
 * @Description: 用户相关dubbo提供者
 * @date 2019/8/14 14:16
 */
@Slf4j
@Service(async = true)
public class AlitaUserProvider implements IAlitaUserProvider {

    @Autowired
    private IAlitaUserService userService;

    @Override
    public CompletableFuture<AlitaUserAccount> findUserById(String userId) {
        return CompletableFuture.supplyAsync(() -> userService.getById(userId), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<AlitaUserAccount> findUserByName(String userName) {
        QueryWrapper<AlitaUserAccount> query = new QueryWrapper();
        query.eq("user_name", userName);
        return CompletableFuture.supplyAsync(() -> userService.getOne(query), ServiceContext.executor);
    }

    @Override
    public CompletableFuture<AlitaUserAccount> findUserByPhoneNum(String userPhoneNum) {
        QueryWrapper<AlitaUserAccount> query = new QueryWrapper();
        query.eq("user_phone_num", userPhoneNum);
        return CompletableFuture.supplyAsync(() -> userService.getOne(query), ServiceContext.executor);
    }

}
