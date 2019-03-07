package top.klw8.alita.service.admin.service.impl.user;


import org.springframework.beans.factory.annotation.Autowired;

import org.apache.dubbo.config.annotation.Service;

import lombok.extern.slf4j.Slf4j;
import top.klw8.alita.entitys.user.StaffOrg;
import top.klw8.alita.service.admin.dao.user.IStaffOrgDao;
import top.klw8.alita.service.api.user.IStaffOrgService;
import top.klw8.alita.starter.service.BaseServiceImpl;

/**
 * @ClassName: StaffOrgServiceImpl
 * @Description: 员工的部门信息 的 Service
 * @author klw
 * @date 2019-01-30 11:39:45
 */
@Slf4j
@Service(async=true)
public class StaffOrgServiceImpl extends BaseServiceImpl<StaffOrg> implements IStaffOrgService {
    
    private IStaffOrgDao dao;
    
    public StaffOrgServiceImpl(@Autowired IStaffOrgDao dao) {
	super(dao);
	this.dao = dao;
    }

}
