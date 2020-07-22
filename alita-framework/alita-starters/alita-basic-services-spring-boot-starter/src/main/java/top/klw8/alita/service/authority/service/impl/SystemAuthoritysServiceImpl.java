package top.klw8.alita.service.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.service.authority.mapper.ISystemAuthoritysMapper;
import top.klw8.alita.service.authority.ISystemAuthoritysService;

import java.util.List;


/**
 * @ClassName: SystemAuthoritysServiceImpl
 * @Description: 系统权限Service_实现
 * @author klw
 * @date 2018年11月28日 下午3:51:25
 */
@Slf4j
@Service
public class SystemAuthoritysServiceImpl
        extends ServiceImpl<ISystemAuthoritysMapper, SystemAuthoritys>
        implements ISystemAuthoritysService {

    @Override
    public SystemAuthoritys findByAuActionAndAppTag(String action, String appTag){
        QueryWrapper<SystemAuthoritys> query = new QueryWrapper();
        query.eq("authority_action", action);
        query.eq("app_tag", appTag);
        return this.getOne(query);
    }

    @Override
    public int removeAuthorityFromRole(String auId) {
        return this.baseMapper.removeAuthorityFromRole(auId);
    }

    @Override
    public IPage<SystemAuthoritys> selectSystemAuthoritysList(Page page, String authorityName,
                                                              String authorityType, String authorityAction,
                                                              String catlogName, String appTag) {
        return this.baseMapper.selectSystemAuthoritysList(page,authorityName, authorityType,
                authorityAction, catlogName, appTag);
    }

    @Override
    public List<SystemAuthoritys> selectAllSystemAuthoritysWithCatlog(String appTag){
        return this.baseMapper.selectAllSystemAuthoritysWithCatlog(appTag);
    }


}
