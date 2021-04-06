package top.klw8.alita.service.authority;

import com.baomidou.mybatisplus.extension.service.IService;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;

/**
 * @ClassName: ISystemAuthoritysCatlogService
 * @Description: 权限的目录Service
 * @author klw
 * @date 2018年11月28日 下午3:48:33
 */
public interface ISystemAuthoritysCatlogService extends IService<SystemAuthoritysCatlog> {

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据目录名称和appTag查找目录
     * @Date 2020/7/22 17:12
     * @param: catlogName
     * @param: appTag
     * @return top.klw8.alita.entitys.authority.SystemAuthoritysCatlog
     */
    SystemAuthoritysCatlog findByCatlogNameAndAppTag(String catlogName, String appTag);

}
