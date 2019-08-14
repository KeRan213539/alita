package top.klw8.alita.service.authority.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.service.authority.mapper.ISystemAuthoritysMapper;
import top.klw8.alita.service.authority.ISystemAuthoritysService;


/**
 * @ClassName: SystemAuthoritysServiceImpl
 * @Description: 系统权限Service_实现
 * @author klw
 * @date 2018年11月28日 下午3:51:25
 */
@Slf4j
@Service
public class SystemAuthoritysServiceImpl extends ServiceImpl<ISystemAuthoritysMapper, SystemAuthoritys> implements ISystemAuthoritysService {

    public SystemAuthoritys findByAuAction(String action) {
        return this.getOne(this.query().eq("authority_action", action));
    }
    
}
