package top.klw8.alita.service.api.authority;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.service.api.IMybatisBaseService;

/**
 * @ClassName: ISystemAuthoritysCatlogService
 * @Description: 权限的目录Service
 * @author klw
 * @date 2018年11月28日 下午3:48:33
 */
public interface ISystemAuthoritysCatlogService extends IMybatisBaseService<SystemAuthoritysCatlog> {
    /**
     * @Title: addAuthority2Catlog
     * @author klw
     * @Description: 添加权限到目录中
     * @param catlogId
     * @param au
     * @return
     */
    CompletableFuture<Integer> addAuthority2Catlog(String catlogId, SystemAuthoritys au);
    
    /**
     * @Title: removeAuthorityFromCatlog
     * @author klw
     * @Description: 从目录中删除指定权限
     * @param catlogId
     * @param au
     * @return
     */
    CompletableFuture<Integer> removeAuthorityFromCatlog(String catlogId, SystemAuthoritys au);
    
    /**
     * @Title: replaceAuthority2Catlog
     * @author klw
     * @Description: 使用传入的权限List替换目录中的权限
     * @param catlogId
     * @param auList
     * @return
     */
    CompletableFuture<Integer> replaceAuthority2Catlog(String catlogId, List<SystemAuthoritys> auList);
    
    /**
     * @Title: findByCatlogName
     * @author klw
     * @Description: 根据目录名称查找目录
     * @param catlogName
     * @return
     */
    CompletableFuture<SystemAuthoritysCatlog> findByCatlogName(String catlogName);
}
