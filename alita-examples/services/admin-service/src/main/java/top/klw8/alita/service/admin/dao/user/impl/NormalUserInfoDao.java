package top.klw8.alita.service.admin.dao.user.impl;

import org.springframework.stereotype.Repository;

import top.klw8.alita.entitys.user.NormalUserInfo;
import top.klw8.alita.service.admin.dao.user.INormalUserInfoDao;
import top.klw8.alita.starter.mongodb.base.MongoSpringDataBaseDao;

/**
 * @ClassName: NormalUserInfoDao
 * @Description: 前台用户(客户)信息 的 DAO
 * @author klw
 * @date 2019年1月30日 上午11:29:04
 */
@Repository
public class NormalUserInfoDao extends MongoSpringDataBaseDao<NormalUserInfo> implements INormalUserInfoDao {

}
