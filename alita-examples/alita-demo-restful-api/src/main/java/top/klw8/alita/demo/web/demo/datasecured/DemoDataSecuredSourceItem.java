package top.klw8.alita.demo.web.demo.datasecured;

import lombok.Setter;
import top.klw8.alita.starter.auscan.IDataSecuredSourceItem;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DemoDataSecuredSourceItem
 * @Description: 数据权限来源中的元素
 * @date 2020/5/12 16:50
 */
@Setter
public class DemoDataSecuredSourceItem implements IDataSecuredSourceItem {

    private String resource;

    private String resName;

    public DemoDataSecuredSourceItem(String resource, String resName){
        this.resource = resource;
        this.resName = resName;
    }

    @Override
    public String getResource() {
        return this.resource;
    }

    @Override
    public String getRemark() {
        return this.resName;
    }
}
