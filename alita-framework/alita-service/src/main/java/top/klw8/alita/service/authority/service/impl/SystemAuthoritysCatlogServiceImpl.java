package top.klw8.alita.service.authority.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    private ISystemAuthoritysCatlogMapper dao;

    @Override
    public int addAuthority2Catlog(String catlogId, SystemAuthoritys au) {
        if (null == catlogId || null == au || null == au.getId()) {
            return 0;
        }
        return dao.addAuthority2Catlog(catlogId, au.getId());
    }

    @Override
    public int removeAuthorityFromCatlog(String catlogId, SystemAuthoritys au) {
        if (null == catlogId || null == au || null == au.getId()) {
            return 0;
        }
        return dao.removeAuthorityFromCatlog(catlogId, au.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int replaceAuthority2Catlog(String catlogId, List<SystemAuthoritys> auList) {
        if (null == catlogId || null == auList) {
            return 0;
        }
        dao.removeAuthoritysFromCatlog(catlogId);
        List<Map<String, String>> dataList = new ArrayList<>();
        for (SystemAuthoritys au : auList) {
            Map<String, String> item = new HashMap<>(2);
            item.put("catlogId", catlogId);
            item.put("auId", au.getId());
            dataList.add(item);
        }
        return dao.batchInsertAuthoritys4Catlog(dataList);
    }

    @Override
    public SystemAuthoritysCatlog findByCatlogName(String catlogName) {
        return this.getOne(this.query().eq("catlog_name", catlogName));
    }

}
