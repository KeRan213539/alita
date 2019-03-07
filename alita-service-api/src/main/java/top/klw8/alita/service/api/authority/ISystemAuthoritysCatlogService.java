package top.klw8.alita.service.api.authority;

import java.util.List;

import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.service.base.api.IBaseService;

/**
 * @ClassName: ISystemAuthoritysCatlogService
 * @Description: 权限的目录Service
 * @author klw
 * @date 2018年11月28日 下午3:48:33
 */
public interface ISystemAuthoritysCatlogService extends IBaseService<SystemAuthoritysCatlog> {
    /**
     * @Title: addAuthority2Catlog
     * @author klw
     * @Description: 添加权限到目录中
     * @param catlogId
     * @param au
     * @return
     */
    int addAuthority2Catlog(Long catlogId, SystemAuthoritys au);
    
    /**
     * @Title: removeAuthorityFromCatlog
     * @author klw
     * @Description: 从目录中删除指定权限
     * @param catlogId
     * @param au
     * @return
     */
    int removeAuthorityFromCatlog(Long catlogId, SystemAuthoritys au);
    
    /**
     * @Title: replaceAuthority2Catlog
     * @author klw
     * @Description: 使用传入的权限List替换目录中的权限
     * @param catlogId
     * @param auList
     * @return
     */
    int replaceAuthority2Catlog(Long catlogId, List<SystemAuthoritys> auList);
    
    /**
     * @Title: findByCatlogName
     * @author klw
     * @Description: 根据目录名称查找目录
     * @param catlogName
     * @return
     */
    SystemAuthoritysCatlog findByCatlogName(String catlogName);
}
