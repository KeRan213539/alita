package ${package};

import IMongoBasePlusDao;
<#list importList as importStr>
import ${importStr};
</#list>

/**
 * @ClassName: ${className}
 * @Description: ${classComment} 的 DAO
 * @author dev-tools
 * @date ${.now?string["yyyy年MM月dd日 HH:mm:ss"]}
 */
public interface ${className} extends IMongoBasePlusDao<${entityName}> {

}
