package top.klw8.alita.demo.web.demo.datasecured;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import top.klw8.alita.starter.auscan.IDataSecuredSource;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DemoDataSecuredSourceStatic
 * @Description: 数据权限数据源
 * @date 2020/5/12 16:48
 */
@Component
public class DemoDataSecuredSource implements IDataSecuredSource {

    @Override
    public List<DemoDataSecuredSourceEnum> getDataSecuredSourceList() {
        return Lists.newArrayList(DemoDataSecuredSourceEnum.values());
    }
}
