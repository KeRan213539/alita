package top.klw8.alita.service.api.authority;

import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.service.api.IMybatisBaseService;

import java.util.concurrent.CompletableFuture;

/**
 * @ClassName: ISystemAuthoritysService
 * @Description: 系统权限Service
 * @author klw
 * @date 2018年11月28日 下午3:48:18
 */
public interface ISystemAuthoritysService extends IMybatisBaseService<SystemAuthoritys> {

    /**
     * @Title: findByAuAction
     * @author klw
     * @Description: 根据authorityAction 查找
     * @param action
     * @return
     */
    CompletableFuture<SystemAuthoritys> findByAuAction(String action);
    
}
