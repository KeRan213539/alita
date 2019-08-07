package top.klw8.alita.service.demo.service.impl.demo;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.dubbo.config.annotation.Service;

import lombok.extern.slf4j.Slf4j;
import top.klw8.alita.entitys.demo.MongoDBTest;
import top.klw8.alita.service.api.demo.ISpringCloudProviderDemoService;
import top.klw8.alita.service.demo.dao.demo.IMongoDBTestDao;
import top.klw8.alita.starter.service.BaseServiceImpl;

/**
 * @author klw
 * @ClassName: SpringCloudProviderDemoController
 * @Description: SpringCloud提供者演示
 * @date 2018年9月13日 下午5:13:33
 */
@Slf4j
@Service(async = true)
public class SpringCloudProviderDemoServiceImpl extends BaseServiceImpl<MongoDBTest> implements ISpringCloudProviderDemoService {

    private IMongoDBTestDao dao;

    public SpringCloudProviderDemoServiceImpl(@Autowired IMongoDBTestDao dao) {
        super(dao);
        this.dao = dao;
    }

    public List<MongoDBTest> queryAll(String abc) throws Exception {
        if (StringUtils.isBlank(abc)) throw new Exception("抛个异常玩玩");
        log.error("queryAll 被调用了888888888888888888");
        return asyncSendData(dao.findAll());
    }
}
