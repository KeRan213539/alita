package top.klw8.alita.service.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.apache.dubbo.config.annotation.Service;

import lombok.extern.slf4j.Slf4j;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.service.admin.dao.ISystemAuthoritysDao;
import top.klw8.alita.service.api.authority.ISystemAuthoritysService;
import top.klw8.alita.starter.service.BaseServiceImpl;

/**
 * @ClassName: SystemAuthoritysServiceImpl
 * @Description: 系统权限Service_实现
 * @author klw
 * @date 2018年11月28日 下午3:51:25
 */
@Slf4j
@Service(async=true)
public class SystemAuthoritysServiceImpl extends BaseServiceImpl<SystemAuthoritys> implements ISystemAuthoritysService {

    private ISystemAuthoritysDao dao;
    
    public SystemAuthoritysServiceImpl(@Autowired ISystemAuthoritysDao dao) {
	super(dao);
	this.dao = dao;
    }
    
    public SystemAuthoritys findByAuAction(String action) {
	Query query = dao.createQuery().addCriteria(Criteria.where("authorityAction").is(action));
	return asyncSendData(dao.getMongoTemplate().findOne(query, SystemAuthoritys.class));
    }
    
}
