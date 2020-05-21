package top.klw8.alita.service.authority;

import com.baomidou.mybatisplus.extension.service.IService;
import top.klw8.alita.entitys.authority.SystemDataSecured;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ISystemDataSecuredService
 * @Description: 数据权限表 Service
 * @date 2020/5/13 16:45
 */
public interface ISystemDataSecuredService extends IService<SystemDataSecured> {

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据资源标识和所属权限ID查找数据权限
     * @Date 2020/5/13 17:24
     * @param: resource
     * @param: auId
     * @return top.klw8.alita.entitys.authority.SystemDataSecured
     */
    SystemDataSecured findByResourceAndAuId(String resource, String auId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据所属权限ID查找数据权限, 传空查全局数据权限
     * @Date 2020/5/18 16:07
     * @param: auId
     * @return java.util.List<top.klw8.alita.entitys.authority.SystemDataSecured>
     */
    List<SystemDataSecured> findByAuId(String auId);

    /**
     * @author klw(213539@qq.com)
     * @Description: 检查数据权限中是否有属于指定权限的
     */
    int countByAuId(String auId);


}
