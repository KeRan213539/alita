package top.klw8.alita.service.authority.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.service.authority.ISystemRoleService;
import top.klw8.alita.service.authority.mapper.ISystemRoleMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author klw
 * @ClassName: SystemRoleServiceImpl
 * @Description: 系统角色Service_实现
 * @date 2018年11月28日 下午3:53:01
 */
@Slf4j
@Service
public class SystemRoleServiceImpl extends ServiceImpl<ISystemRoleMapper, SystemRole> implements ISystemRoleService {

    @Autowired
    private ISystemRoleMapper dao;

    @Override
    public int addAuthority2Role(String roleId, SystemAuthoritys au) {
        if (null == roleId || null == au || null == au.getId()) {
            return 0;
        }
        return dao.addAuthority2Role(au.getId(), roleId);
    }

    @Override
    public int removeAuthorityFromRole(String roleId, SystemAuthoritys au) {
        if (null == roleId || null == au || null == au.getId()) {
            return 0;
        }
        return dao.removeAuthorityFromRole(au.getId(), roleId);
    }

    @Override
    public int replaceAuthority2Role(String roleId, List<SystemAuthoritys> auList) {
        if (null == roleId || CollectionUtils.isEmpty(auList)) {
            return 0;
        }
        dao.removeAuthoritysFromRole(roleId);
        List<Map<String, String>> dataList = new ArrayList<>();
        for (SystemAuthoritys au : auList) {
            Map<String, String> item = new HashMap<>(2);
            item.put("roleId", roleId);
            item.put("auId", au.getId());
            dataList.add(item);
        }
        return dao.batchInsertAuthoritysFromRole(dataList);
    }
}
