package top.klw8.alita.starter.auscan;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: IDataSecuredSourceItem
 * @Description: 数据权限来源元素
 * @date 2020/5/12 15:47
 */
public interface IDataSecuredSourceItem {

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取数据权限资源标识
     */
    String getResource();

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取数据权限资源 备注/名称
     */
    String getRemark();

}
