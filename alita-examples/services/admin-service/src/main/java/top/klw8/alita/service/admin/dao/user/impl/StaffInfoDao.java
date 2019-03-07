package top.klw8.alita.service.admin.dao.user.impl;

import org.springframework.stereotype.Repository;

import top.klw8.alita.entitys.user.StaffInfo;
import top.klw8.alita.service.admin.dao.user.IStaffInfoDao;
import top.klw8.alita.starter.mongodb.base.MongoSpringDataBaseDao;

/**
 * @ClassName: StaffInfoDao
 * @Description: 员工信息 的 DAO
 * @author klw
 * @date 2019年1月30日 上午11:29:19
 */
@Repository
public class StaffInfoDao extends MongoSpringDataBaseDao<StaffInfo> implements IStaffInfoDao {

}
