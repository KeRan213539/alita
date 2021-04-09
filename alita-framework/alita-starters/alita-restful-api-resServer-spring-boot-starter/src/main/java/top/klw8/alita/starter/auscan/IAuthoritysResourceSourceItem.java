package top.klw8.alita.starter.auscan;

import top.klw8.alita.entitys.authority.enums.ResourceType;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: IDataSecuredSourceItem
 * @Description: 数据权限来源元素
 * @date 2020/5/12 15:47
 */
public interface IAuthoritysResourceSourceItem {

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取资源标识
     */
    String getResource();

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取资源 备注/名称
     */
    String getRemark();

    /**
     * 获取资源类型.
     * @param
     * @return top.klw8.alita.entitys.authority.enums.ResourceType
     */
    ResourceType getResType();

}
