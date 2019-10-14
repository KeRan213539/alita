package top.klw8.alita.service.authority;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;

/**
 * @ClassName: ISystemAuthoritysCatlogService
 * @Description: 权限的目录Service
 * @author klw
 * @date 2018年11月28日 下午3:48:33
 */
public interface ISystemAuthoritysCatlogService extends IService<SystemAuthoritysCatlog> {

    /**
     * @Title: findByCatlogName
     * @author klw
     * @Description: 根据目录名称查找目录
     * @param catlogName
     * @return
     */
    SystemAuthoritysCatlog findByCatlogName(String catlogName);

}
