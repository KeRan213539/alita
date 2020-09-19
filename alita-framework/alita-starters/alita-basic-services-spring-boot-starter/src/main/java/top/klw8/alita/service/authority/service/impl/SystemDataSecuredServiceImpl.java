package top.klw8.alita.service.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.klw8.alita.entitys.authority.SystemDataSecured;
import top.klw8.alita.service.authority.ISystemDataSecuredService;
import top.klw8.alita.service.authority.mapper.ISystemDataSecuredMapper;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: SystemDataSecuredServiceImpl
 * @Description: 数据权限表 service 实现
 * @date 2020/5/13 16:46
 */
@Slf4j
@Service
public class SystemDataSecuredServiceImpl extends ServiceImpl<ISystemDataSecuredMapper,
        SystemDataSecured> implements ISystemDataSecuredService {


    @Override
    public SystemDataSecured findByResourceAndAuId(String resource, String auId) {
        QueryWrapper<SystemDataSecured> query = new QueryWrapper();
        if(StringUtils.isBlank(auId)){
            query.isNull("authoritys_id");
        } else {
            query.eq("authoritys_id", auId);
        }
        query.eq("resource", resource);
        return this.getOne(query);
    }

    @Override
    public List<SystemDataSecured> findByAuId(String auId, String appTag) {
        QueryWrapper<SystemDataSecured> query = new QueryWrapper();
        if(StringUtils.isBlank(auId)){
            query.isNull("authoritys_id");
        } else {
            query.eq("authoritys_id", auId);
        }
        if(StringUtils.isNotBlank(appTag)){
            query.eq("app_tag", appTag);
        }
        return this.list(query);
    }

    public List<SystemDataSecured> findByRoleIdAndAuId(String roleId, String auId){
        Assert.hasText(roleId, "roleId 不能为空");
        return this.baseMapper.findByRoleIdAndAuId(roleId, auId);
    }

    @Override
    public int countByAuId(String auId) {
        return this.baseMapper.countByAuId(auId);
    }
}