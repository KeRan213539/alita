package top.klw8.alita.service.admin.dao.impl;

import org.springframework.stereotype.Repository;

import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.service.admin.dao.ISystemAuthoritysCatlogDao;
import top.klw8.alita.starter.mongodb.base.MongoSpringDataBaseDao;

/**
 * @ClassName: SystemAuthoritysCatlogDaoImpl
 * @Description: 权限的目录DAO_实现
 * @author klw
 * @date 2018年11月28日 下午3:43:15
 */
@Repository
public class SystemAuthoritysCatlogDaoImpl extends MongoSpringDataBaseDao<SystemAuthoritysCatlog> implements ISystemAuthoritysCatlogDao {
    
}
