package top.klw8.alita.service.authority.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.authority.mapper.IAlitaUserMapper;
import top.klw8.alita.service.authority.IAlitaUserService;

/**
 * @author klw
 * @ClassName: AlitaUserServiceImpl
 * @Description: 用户服务
 * @date 2018年11月9日 下午5:32:24
 */
@Slf4j
@Service
public class AlitaUserServiceImpl extends ServiceImpl<IAlitaUserMapper, AlitaUserAccount> implements IAlitaUserService {

    @Autowired
    private IAlitaUserMapper dao;

    public int addRole2User(String userId, SystemRole role) {
        if (null == userId || null == role || null == role.getId()) {
            return 0;
        }
        return dao.addRole2User(userId, role.getId());
    }

    public int removeRoleFromUser(String userId, SystemRole role) {
        if (null == userId || null == role || null == role.getId()) {
            return 0;
        }
        return dao.removeRoleFromUser(userId, role.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public int replaceRole2User(String userId, List<SystemRole> roleList) {
        if (null == userId || CollectionUtils.isEmpty(roleList)) {
            return 0;
        }
        dao.removeRolesFromUser(userId);
        List<Map<String, String>> dataList = new ArrayList<>();
        for (SystemRole role : roleList) {
            Map<String, String> item = new HashMap<>(2);
            item.put("userId", userId);
            item.put("roleId", role.getId());
            dataList.add(item);
        }
        return dao.batchInsertRoles4User(dataList);
    }

}
