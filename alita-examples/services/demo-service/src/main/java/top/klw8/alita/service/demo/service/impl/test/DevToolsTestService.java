package top.klw8.alita.service.demo.service.impl.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.dubbo.config.annotation.Service;

import lombok.extern.slf4j.Slf4j;
import top.klw8.alita.entitys.demo.DevToolsTestEntity;
import top.klw8.alita.service.demo.dao.test.IDevToolsTestDao;
import top.klw8.alita.service.test.IDevToolsTestService;
import top.klw8.alita.starter.service.BaseServiceImpl;

/**
 * @ClassName: DevToolsTestService
 * @Description: 代码生成器测试用 的 Service实现
 * @author dev-tools
 * @date 2019年03月05日 11:44:29
 */
@Slf4j
@Service(async=true)
public class DevToolsTestService extends BaseServiceImpl<DevToolsTestEntity> implements IDevToolsTestService {
    
    private IDevToolsTestDao dao;
    
    public DevToolsTestService(@Autowired IDevToolsTestDao dao) {
        super(dao);
        this.dao = dao;
    }

}
