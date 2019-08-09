package top.klw8.alita.service.demo.dao.impl.demo;

import org.springframework.stereotype.Repository;

import top.klw8.alita.entitys.demo.MongoDBTest;
import top.klw8.alita.service.demo.mapper.demo.IMongoDBTestDao;
import top.klw8.alita.starter.mongodb.base.MongoSpringDataBaseDao;

/**
 * @ClassName: MongoDBTestDaoImpl
 * @Description: MongoDBTestDaoImpl
 * @author klw
 * @date 2018年10月4日 下午8:29:35
 */
@Repository
public class MongoDBTestDaoImpl extends MongoSpringDataBaseDao<MongoDBTest> implements IMongoDBTestDao {

}
