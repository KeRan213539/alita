package top.klw8.alita.service.authority.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.authority.IAlitaUserService;
import top.klw8.alita.service.authority.mapper.IAlitaUserMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author klw
 * @ClassName: AlitaUserServiceImpl
 * @Description: 用户服务
 * @date 2018年11月9日 下午5:32:24
 */
@Slf4j
public class AlitaUserServiceImpl extends ServiceImpl<IAlitaUserMapper, AlitaUserAccount> implements IAlitaUserService {

    @Override
    public int addRole2User(String userId, SystemRole role) {
        try {
            if (null == userId || null == role || null == role.getId()) {
                return 0;
            }
            return this.getBaseMapper().addRole2User(userId, role.getId());
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int removeRoleFromUser(String userId, SystemRole role) {
        if (null == userId || null == role || null == role.getId()) {
            return 0;
        }
        return this.getBaseMapper().removeRoleFromUser(userId, role.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int replaceRole2User(String userId, List<SystemRole> roleList) {
        if (null == userId || CollectionUtils.isEmpty(roleList)) {
            return 0;
        }
        this.getBaseMapper().removeRolesFromUser(userId);
        List<Map<String, String>> dataList = new ArrayList<>();
        for (SystemRole role : roleList) {
            Map<String, String> item = new HashMap<>(2);
            item.put("userId", userId);
            item.put("roleId", role.getId());
            dataList.add(item);
        }
        return this.getBaseMapper().batchInsertRoles4User(dataList);
    }

    @Override
    public List<SystemRole> getUserAllRoles(String userId) {
        return this.getBaseMapper().selectUserAllRoles(userId);
    }

    @Override
    public List<SystemAuthoritys> getRoleAllAuthoritys(String roleId) {
        return this.getBaseMapper().selectRoleAuthoritys(roleId);
    }

}
