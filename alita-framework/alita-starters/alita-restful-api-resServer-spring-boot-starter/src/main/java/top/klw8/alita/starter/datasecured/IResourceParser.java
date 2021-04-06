package top.klw8.alita.starter.datasecured;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: IResourceParser
 * @Description:  资源解析器接口
 * @date 2020/4/24 11:18
 */
public interface IResourceParser {

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据传入的数据解析出数据权限的资源名称, 支持多资源名称.
     * 如果返回多个资源名称,那么当前请求用户必须多个资源权限都必须拥有
     */
    default ResourceParserResult parseResource(IResourceParserData parserPojo){
        return new ResourceParserResult();
    }

}
