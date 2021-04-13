package top.klw8.alita.starter.auscan;

import java.util.List;

/**
 * 静态资源权限来源, 实现该接口的来源结果在权限扫描时存入数据库.
 * 2020/5/12 15:38
 */
public interface IAuthoritysResourceSource {

    /**
     * 获取数据权限List
     * 2020/5/12 15:55
     * @param:
     * @return T
     */
    <T extends IAuthoritysResourceSourceItem> List<T> getDataSecuredSourceList();
}
