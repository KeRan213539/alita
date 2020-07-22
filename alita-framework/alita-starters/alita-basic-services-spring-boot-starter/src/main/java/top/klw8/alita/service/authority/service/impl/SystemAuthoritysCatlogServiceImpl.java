package top.klw8.alita.service.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.service.authority.ISystemAuthoritysCatlogService;
import top.klw8.alita.service.authority.mapper.ISystemAuthoritysCatlogMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author klw
 * @ClassName: SystemAuthoritysCatlogServiceImpl
 * @Description: 权限的目录Service_实现
 * @date 2018年11月28日 下午3:52:21
 */
@Slf4j
@Service
public class SystemAuthoritysCatlogServiceImpl extends ServiceImpl<ISystemAuthoritysCatlogMapper, SystemAuthoritysCatlog> implements ISystemAuthoritysCatlogService {

    @Override
    public SystemAuthoritysCatlog findByCatlogNameAndAppTag(String catlogName, String appTag) {
        QueryWrapper<SystemAuthoritysCatlog> query = new QueryWrapper();
        query.eq("catlog_name", catlogName);
        query.eq("app_tag", appTag);
        return this.getOne(query);
    }

}
