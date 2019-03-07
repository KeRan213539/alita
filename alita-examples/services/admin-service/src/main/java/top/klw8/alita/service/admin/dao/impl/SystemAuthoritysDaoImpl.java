package top.klw8.alita.service.admin.dao.impl;

import org.springframework.stereotype.Repository;

import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.service.admin.dao.ISystemAuthoritysDao;
import top.klw8.alita.starter.mongodb.base.MongoSpringDataBaseDao;

/**
 * @ClassName: SystemAuthoritysDaoImpl
 * @Description: 系统权限DAO_实现
 * @author klw
 * @date 2018年11月28日 下午3:43:56
 */
@Repository
public class SystemAuthoritysDaoImpl extends MongoSpringDataBaseDao<SystemAuthoritys> implements ISystemAuthoritysDao {
    
}
