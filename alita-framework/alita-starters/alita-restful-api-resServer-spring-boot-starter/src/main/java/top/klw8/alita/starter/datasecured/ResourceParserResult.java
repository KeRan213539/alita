package top.klw8.alita.starter.datasecured;

import lombok.Getter;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ResourceParserResult
 * @Description: 资源解析器的返回参数 Bean
 * @date 2020/7/23 16:51
 */
@Getter
public class ResourceParserResult {

    /**
     * @author klw(213539@qq.com)
     * @Description: 资源解析器解析出的资源
     */
    private final String[] parsedResources;

    /**
     * @author klw(213539@qq.com)
     * @Description: 是否万能钥匙,如果是则直接通过数据权限验证
     */
    private final boolean masterKey;

    public ResourceParserResult(){
        this.parsedResources = new String[0];
        this.masterKey = false;
    }

    public ResourceParserResult(String[] parsedResources){
        this.parsedResources = parsedResources;
        this.masterKey = false;
    }

    public ResourceParserResult(String[] parsedResources, boolean isMasterKey){
        this.parsedResources = parsedResources;
        this.masterKey = isMasterKey;
    }



}
