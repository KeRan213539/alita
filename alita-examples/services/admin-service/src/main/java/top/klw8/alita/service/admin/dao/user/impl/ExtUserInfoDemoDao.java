package top.klw8.alita.service.admin.dao.user.impl;

import org.springframework.stereotype.Repository;

import top.klw8.alita.entitys.user.ExtUserInfoDemo;
import top.klw8.alita.service.admin.dao.user.IExtUserInfoDemoDao;
import top.klw8.alita.starter.mongodb.base.MongoSpringDataBaseDao;

/**
 * @ClassName: ExtUserInfoDemoDao
 * @Description: 用户扩展信息Demo 的 DAO
 * @author klw
 * @date 2019年1月30日 上午11:29:04
 */
@Repository
public class ExtUserInfoDemoDao extends MongoSpringDataBaseDao<ExtUserInfoDemo> implements IExtUserInfoDemoDao {

}
