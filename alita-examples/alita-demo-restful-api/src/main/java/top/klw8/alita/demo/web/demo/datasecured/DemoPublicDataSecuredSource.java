package top.klw8.alita.demo.web.demo.datasecured;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import top.klw8.alita.starter.annotations.PublicDataSecuredRegister;
import top.klw8.alita.starter.auscan.IDataSecuredSource;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DemoPublicDataSecuredSourceStatic
 * @Description: 数据权限全局数据源
 * @date 2020/5/14 11:22
 */
@Component
@PublicDataSecuredRegister
public class DemoPublicDataSecuredSource implements IDataSecuredSource {

    @Override
    public List<DemoDataSecuredSourceEnum> getDataSecuredSourceList() {
        return Lists.newArrayList(DemoDataSecuredSourceEnum.values());
    }

}
