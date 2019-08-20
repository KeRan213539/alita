package top.klw8.alita.service.api.demo;

import java.util.List;

import top.klw8.alita.entitys.demo.mongo.MongoDBTest;
import top.klw8.alita.service.base.mongo.api.IMongoBaseService;


/**
 * @ClassName: ISpringCloudProviderDemoService
 * @Description: 
 * @author klw
 * @date 2018年9月13日 下午5:34:20
 */
public interface ISpringCloudProviderDemoService extends IMongoBaseService<MongoDBTest> {

    /**
     * @Title: queryAll
     * @author klw
     * @Description: demo 查询全部
     * @param abc 任意参数,如果为空就抛异常
     * @return
     * @throws Exception
     */
    public List<MongoDBTest> queryAll(String abc) throws Exception;

}
