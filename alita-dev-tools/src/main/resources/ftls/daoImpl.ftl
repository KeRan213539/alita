package ${package};

import org.springframework.stereotype.Repository;
import top.klw8.alita.starter.mongodb.base.MongoSpringDataBaseDao;
<#list importList as importStr>
import ${importStr};
</#list>

 /**
 * @ClassName: ${className}
 * @Description: ${classComment} 的 DAO实现
 * @author dev-tools
 * @date ${.now?string["yyyy年MM月dd日 HH:mm:ss"]}
 */
@Repository
public class ${className} extends MongoSpringDataBaseDao<${entityName}> implements ${interfaceName} {

}
