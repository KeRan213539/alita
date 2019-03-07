package top.klw8.alita.service.admin.service.impl.user;


import org.springframework.beans.factory.annotation.Autowired;

import org.apache.dubbo.config.annotation.Service;

import lombok.extern.slf4j.Slf4j;
import top.klw8.alita.entitys.user.StaffRegion;
import top.klw8.alita.service.admin.dao.user.IStaffRegionDao;
import top.klw8.alita.service.api.user.IStaffRegionService;
import top.klw8.alita.starter.service.BaseServiceImpl;

/**
 * @ClassName: NormalUserInfoImpl
 * @Description: 员工(管家)的负责区域 的 Service
 * @author klw
 * @date 2019-01-30 11:39:45
 */
@Slf4j
@Service(async=true)
public class StaffRegionServiceImpl extends BaseServiceImpl<StaffRegion> implements IStaffRegionService {
    
    private IStaffRegionDao dao;
    
    public StaffRegionServiceImpl(@Autowired IStaffRegionDao dao) {
	super(dao);
	this.dao = dao;
    }

}
