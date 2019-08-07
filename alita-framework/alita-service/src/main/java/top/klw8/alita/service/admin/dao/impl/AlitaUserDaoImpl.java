package top.klw8.alita.service.admin.dao.impl;

import org.springframework.stereotype.Repository;

import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.admin.dao.IAlitaUserDao;
import top.klw8.alita.starter.mongodb.base.MongoSpringDataBaseDao;

/**
 * @ClassName: AlitaUserDaoImpl
 * @Description: 用户 dao
 * @author klw
 * @date 2018年11月9日 下午5:30:13
 */
@Repository
public class AlitaUserDaoImpl extends MongoSpringDataBaseDao<AlitaUserAccount> implements IAlitaUserDao {

}
