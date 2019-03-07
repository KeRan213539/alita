package ${package};

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.dubbo.config.annotation.Service;
import top.klw8.alita.starter.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
<#list importList as importStr>
import ${importStr};
</#list>

/**
 * @ClassName: ${className}
 * @Description: ${classComment} 的 Service实现
 * @author dev-tools
 * @date ${.now?string["yyyy年MM月dd日 HH:mm:ss"]}
 */
@Slf4j
@Service(async=true)
public class ${className} extends BaseServiceImpl<${entityName}> implements ${interfaceName} {
    
    private ${daoInterfaceName} dao;
    
    public ${className}(@Autowired ${daoInterfaceName} dao) {
        super(dao);
        this.dao = dao;
    }

}
