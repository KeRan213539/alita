package top.klw8.alita.service.admin.service.impl.product;


import org.springframework.beans.factory.annotation.Autowired;

import org.apache.dubbo.config.annotation.Service;

import lombok.extern.slf4j.Slf4j;
import top.klw8.alita.entitys.product.ServiceType;
import top.klw8.alita.service.admin.dao.product.IServiceTypeDao;
import top.klw8.alita.service.api.product.IServiceTypeService;
import top.klw8.alita.starter.service.BaseServiceImpl;

/**
 * @ClassName: NormalUserInfoImpl
 * @Description: 服务类型定义 的 Service
 * @author klw
 * @date 2019-01-30 11:39:45
 */
@Slf4j
@Service(async=true)
public class ServiceTypeServiceImpl extends BaseServiceImpl<ServiceType> implements IServiceTypeService {
    
    private IServiceTypeDao dao;
    
    public ServiceTypeServiceImpl(@Autowired IServiceTypeDao dao) {
	super(dao);
	this.dao = dao;
    }

}
