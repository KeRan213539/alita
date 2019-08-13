package top.klw8.alita.service.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.dubbo.config.annotation.Service;

import lombok.extern.slf4j.Slf4j;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.service.admin.mapper.ISystemAuthoritysMapper;
import top.klw8.alita.service.api.authority.ISystemAuthoritysService;
import top.klw8.alita.starter.service.MybatisBaseServiceImpl;

import java.util.concurrent.CompletableFuture;

/**
 * @ClassName: SystemAuthoritysServiceImpl
 * @Description: 系统权限Service_实现
 * @author klw
 * @date 2018年11月28日 下午3:51:25
 */
@Slf4j
@Service(async=true)
public class SystemAuthoritysServiceImpl extends MybatisBaseServiceImpl<ISystemAuthoritysMapper, SystemAuthoritys> implements ISystemAuthoritysService {

    @Autowired
    private ISystemAuthoritysMapper dao;

    public CompletableFuture<SystemAuthoritys> findByAuAction(String action) {
        return this.getOne(this.query().eq("authority_action", action));
    }
    
}
