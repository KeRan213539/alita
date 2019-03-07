package top.klw8.alita.service.admin.dao.product.impl;

import org.springframework.stereotype.Repository;

import top.klw8.alita.entitys.product.ServiceType;
import top.klw8.alita.service.admin.dao.product.IServiceTypeDao;
import top.klw8.alita.starter.mongodb.base.MongoSpringDataBaseDao;

/**
 * @ClassName: ServiceTypeDao
 * @Description: 服务类型定义 的 DAO
 * @author klw
 * @date 2019年1月30日 上午11:30:10
 */
@Repository
public class ServiceTypeDao extends MongoSpringDataBaseDao<ServiceType> implements IServiceTypeDao {

}
