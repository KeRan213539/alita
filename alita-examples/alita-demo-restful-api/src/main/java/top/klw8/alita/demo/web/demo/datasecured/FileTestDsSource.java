package top.klw8.alita.demo.web.demo.datasecured;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import top.klw8.alita.starter.auscan.IDataSecuredSource;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: FileTestDsSource
 * @Description: fileTest 的 数据权限源
 * @date 2020/5/15 15:25
 */
@Component
public class FileTestDsSource implements IDataSecuredSource {
    @Override
    public List<DemoDataSecuredSourceItem> getDataSecuredSourceList() {
        return Lists.newArrayList(new DemoDataSecuredSourceItem("fileTest", "fileTest"));
    }
}
