package top.klw8.alita.service.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.admin.mapper.IAlitaUserMapper;
import top.klw8.alita.service.api.user.IAlitaUserService;
import top.klw8.alita.starter.service.MybatisBaseServiceImpl;
import top.klw8.alita.starter.service.common.ServiceContext;

/**
 * @author klw
 * @ClassName: AlitaUserServiceImpl
 * @Description: 用户服务
 * @date 2018年11月9日 下午5:32:24
 */
@Slf4j
@Service(async = true)
public class AlitaUserServiceImpl extends MybatisBaseServiceImpl<IAlitaUserMapper, AlitaUserAccount> implements IAlitaUserService {

	@Autowired
    private IAlitaUserMapper dao;

    public CompletableFuture<Integer> addRole2User(String userId, SystemRole role) {
        return CompletableFuture.supplyAsync(() -> {
            if (null == userId || null == role || null == role.getId()) {
                return  0;
            }
            return dao.addRole2User(userId, role.getId());
        }, ServiceContext.executor);
    }

    public CompletableFuture<Integer> removeRoleFromUser(String userId, SystemRole role) {
        return CompletableFuture.supplyAsync(() -> {
            if (null == userId || null == role || null == role.getId()) {
                return 0;
            }
            return dao.removeRoleFromUser(userId, role.getId());
        }, ServiceContext.executor);
    }

    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Integer> replaceRole2User(String userId, List<SystemRole> roleList) {
        return CompletableFuture.supplyAsync(() -> {
            if (null == userId || CollectionUtils.isEmpty(roleList)) {
                return 0;
            }
            dao.removeRolesFromUser(userId);
            List<Map<String, String>> dataList = new ArrayList<>();
            for (SystemRole role : roleList) {
                Map<String, String> item = new HashMap<>(2);
                item.put("userId", userId);
                item.put("roleId", role.getId());
                dataList.add(item);
            }
            return dao.batchInsertRoles4User(dataList);
        }, ServiceContext.executor);
    }

}
