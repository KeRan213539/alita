package top.klw8.alita.demo.web.demo.datasecured;

import org.springframework.stereotype.Component;
import top.klw8.alita.starter.datasecured.IResourceParser;
import top.klw8.alita.starter.datasecured.IResourceParserData;
import top.klw8.alita.starter.datasecured.ResourceParserResult;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DemoResourceParser
 * @Description: DemoResourceParser
 * @date 2020/4/30 10:57
 */
@Component
public class DemoResourceParser implements IResourceParser {

    @Override
    public ResourceParserResult parseResource(IResourceParserData parserPojo) {
        return new ResourceParserResult(new String[]{"demo"});
    }

}
