package top.klw8.alita.service.admin.dao.user.impl;

import org.springframework.stereotype.Repository;

import top.klw8.alita.entitys.user.StaffOrg;
import top.klw8.alita.service.admin.dao.user.IStaffOrgDao;
import top.klw8.alita.starter.mongodb.base.MongoSpringDataBaseDao;

/**
 * @ClassName: StaffOrgDao
 * @Description: 员工的部门信息 的 DAO
 * @author klw
 * @date 2019年1月30日 上午11:29:31
 */
@Repository
public class StaffOrgDao extends MongoSpringDataBaseDao<StaffOrg> implements IStaffOrgDao {

}
