package top.klw8.alita.demo.web.demo.datasecured;

import top.klw8.alita.starter.auscan.IDataSecuredSourceItem;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DataSecuredSourceEnum
 * @Description: 数据权限来源枚举
 * @date 2020/5/12 17:02
 */
public enum DemoDataSecuredSourceEnum implements IDataSecuredSourceItem {
    SECURED_SOURCE_1("securedSource1", "数据权限来源枚举1"),
    SECURED_SOURCE_2("securedSource2", "数据权限来源枚举2"),
    SECURED_SOURCE_3("securedSource3", "数据权限来源枚举3"),
    SECURED_SOURCE_4("securedSource4", "数据权限来源枚举4"),
    SECURED_SOURCE_5("securedSource5", "数据权限来源枚举5"),
    ;

    DemoDataSecuredSourceEnum(String resource, String resName){
        this.resource = resource;
        this.resName = resName;
    }

    private String resource;

    private String resName;

    @Override
    public String getResource() {
        return this.resource;
    }

    @Override
    public String getRemark() {
        return this.resName;
    }
}