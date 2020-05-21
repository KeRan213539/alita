package top.klw8.alita.starter.auscan;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: IDataSecuredSourceStatic
 * @Description: 静态数据权限来源, 实现该接口的来源结果在权限扫描时存入数据库.
 * @date 2020/5/12 15:38
 */
public interface IDataSecuredSource {

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取数据权限List
     * @Date 2020/5/12 15:55
     * @param:
     * @return T
     */
    <T extends IDataSecuredSourceItem> List<T> getDataSecuredSourceList();
}
