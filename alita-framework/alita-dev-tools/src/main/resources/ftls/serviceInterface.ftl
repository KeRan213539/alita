package ${package};

import top.klw8.alita.service.base.api.IBaseService;
<#list importList as importStr>
import ${importStr};
</#list>

/**
 * @ClassName: ${className}
 * @Description: ${classComment} 的 Service
 * @author dev-tools
 * @date ${.now?string["yyyy年MM月dd日 HH:mm:ss"]}
 */
public interface ${className} extends IBaseService<${entityName}> {

}
