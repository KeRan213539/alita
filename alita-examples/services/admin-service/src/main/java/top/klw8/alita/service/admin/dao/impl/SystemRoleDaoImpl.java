package top.klw8.alita.service.admin.dao.impl;

import org.springframework.stereotype.Repository;

import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.service.admin.dao.ISystemRoleDao;
import top.klw8.alita.starter.mongodb.base.MongoSpringDataBaseDao;

/**
 * @ClassName: SystemRoleDaoImpl
 * @Description: 系统角色DAO_实现
 * @author klw
 * @date 2018年11月28日 下午3:44:17
 */
@Repository
public class SystemRoleDaoImpl extends MongoSpringDataBaseDao<SystemRole> implements ISystemRoleDao {

    
}
