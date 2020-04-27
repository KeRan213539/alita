package top.klw8.alita.starter.datasecured;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DefaultResourceParser
 * @Description: 默认资源解析器, 什么都不解析, 用于注解的默认值
 * @date 2020/4/24 14:35
 */
public class DefaultResourceParser implements IResourceParser {
    @Override
    public String[] parseResource(IResourceParserData parserPojo) {
        return null;
    }
}
