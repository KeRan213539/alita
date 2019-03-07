package top.klw8.alita.service.admin.dao.user.impl;

import org.springframework.stereotype.Repository;

import top.klw8.alita.entitys.user.StaffRegion;
import top.klw8.alita.service.admin.dao.user.IStaffRegionDao;
import top.klw8.alita.starter.mongodb.base.MongoSpringDataBaseDao;

/**
 * @ClassName: StaffRegionDao
 * @Description: 员工(管家)的负责区域 的 DAO
 * @author klw
 * @date 2019年1月30日 上午11:29:42
 */
@Repository
public class StaffRegionDao extends MongoSpringDataBaseDao<StaffRegion> implements IStaffRegionDao {

}
