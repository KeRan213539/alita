package top.klw8.alita.service.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.dubbo.config.annotation.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.service.admin.mapper.ISystemAuthoritysCatlogMapper;
import top.klw8.alita.service.api.authority.ISystemAuthoritysCatlogService;
import top.klw8.alita.starter.service.MybatisBaseServiceImpl;
import top.klw8.alita.starter.service.common.ServiceContext;

/**
 * @author klw
 * @ClassName: SystemAuthoritysCatlogServiceImpl
 * @Description: 权限的目录Service_实现
 * @date 2018年11月28日 下午3:52:21
 */
@Slf4j
@Service(async = true)
public class SystemAuthoritysCatlogServiceImpl extends MybatisBaseServiceImpl<ISystemAuthoritysCatlogMapper, SystemAuthoritysCatlog> implements ISystemAuthoritysCatlogService {

	@Autowired
    private ISystemAuthoritysCatlogMapper dao;

    public CompletableFuture<Integer> addAuthority2Catlog(String catlogId, SystemAuthoritys au) {
		return CompletableFuture.supplyAsync(() -> {
			if (null == catlogId || null == au  || null == au.getId()) {
				return 0;
			}
			return dao.addAuthority2Catlog(catlogId, au.getId());
		}, ServiceContext.executor);
    }

    public CompletableFuture<Integer> removeAuthorityFromCatlog(String catlogId, SystemAuthoritys au) {
		return CompletableFuture.supplyAsync(() -> {
			if (null == catlogId || null == au  || null == au.getId()) {
				return 0;
			}
			return dao.removeAuthorityFromCatlog(catlogId, au.getId());
		}, ServiceContext.executor);
    }

	@Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Integer> replaceAuthority2Catlog(String catlogId, List<SystemAuthoritys> auList) {
		return CompletableFuture.supplyAsync(() -> {
			if (null == catlogId || null == auList) {
				return 0;
			}
			dao.removeAuthoritysFromCatlog(catlogId);
			List<Map<String, String>> dataList = new ArrayList<>();
			for (SystemAuthoritys au : auList) {
				Map<String, String> item = new HashMap<>(2);
				item.put("catlogId", catlogId);
				item.put("auId", au.getId());
				dataList.add(item);
			}
			return dao.batchInsertAuthoritys4Catlog(dataList);
		}, ServiceContext.executor);
    }

    public CompletableFuture<SystemAuthoritysCatlog> findByCatlogName(String catlogName) {
    	return this.getOne(this.query().eq("catlog_name", catlogName));
    }

}
