package top.klw8.alita.service.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.dubbo.config.annotation.Service;

import lombok.extern.slf4j.Slf4j;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.service.admin.mapper.ISystemRoleMapper;
import top.klw8.alita.service.api.authority.ISystemRoleService;
import top.klw8.alita.starter.service.MybatisBaseServiceImpl;
import top.klw8.alita.starter.service.common.ServiceContext;

/**
 * @author klw
 * @ClassName: SystemRoleServiceImpl
 * @Description: 系统角色Service_实现
 * @date 2018年11月28日 下午3:53:01
 */
@Slf4j
@Service(async = true)
public class SystemRoleServiceImpl extends MybatisBaseServiceImpl<ISystemRoleMapper, SystemRole> implements ISystemRoleService {

    @Autowired
    private ISystemRoleMapper dao;

    public CompletableFuture<Integer> addAuthority2Role(String roleId, SystemAuthoritys au) {
        return CompletableFuture.supplyAsync(() -> {
            if (null == roleId || null == au || null == au.getId()) {
                return 0;
            }
            return dao.addAuthority2Role(au.getId(), roleId);
        }, ServiceContext.executor);
    }

    public CompletableFuture<Integer> removeAuthorityFromRole(String roleId, SystemAuthoritys au) {
        return CompletableFuture.supplyAsync(() -> {
            if (null == roleId || null == au || null == au.getId()) {
                return 0;
            }
            return dao.removeAuthorityFromRole(au.getId(), roleId);
        }, ServiceContext.executor);
    }

    public CompletableFuture<Integer> replaceAuthority2Role(String roleId, List<SystemAuthoritys> auList) {
        return CompletableFuture.supplyAsync(() -> {
            if (null == roleId || CollectionUtils.isEmpty(auList)) {
                return 0;
            }
            dao.removeAuthoritysFromRole(roleId);
            List<Map<String, String>> dataList = new ArrayList<>();
            for (SystemAuthoritys au : auList) {
                Map<String, String> item = new HashMap<>(2);
                item.put("roleId", roleId);
                item.put("auId", au.getId());
                dataList.add(item);
            }
            return dao.batchInsertAuthoritysFromRole(dataList);
        }, ServiceContext.executor);
    }
}
