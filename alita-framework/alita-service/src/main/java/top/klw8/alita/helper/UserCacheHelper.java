package top.klw8.alita.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.utils.redis.RedisTagEnum;
import top.klw8.alita.utils.redis.RedisUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static top.klw8.alita.helper.ServiceApiContext.*;

/**
 * @author freedom
 * @version 1.0
 * @ClassName UserCacheHelper
 * @Description 用户缓存帮助类
 * @date 2019-08-14 16:51
 */
@Slf4j
@Component
public class UserCacheHelper {

    /**
     * @param user
     * @Title: putUserInfo2Cache
     * @author klw
     * @Description: 把必要的用户相关数据放入缓存(目前只放入了权限)
     */
    public void putUserInfo2Cache(AlitaUserAccount user) {
        //把权限放入缓存
        Map<String, String> authorityMap = new HashMap<>();
        List<SystemRole> userRoles = user.getUserRoles();
        if (!CollectionUtils.isEmpty(userRoles)) {
            for (SystemRole userRole : userRoles) {
                List<SystemAuthoritys> ruthorityList = userRole.getAuthorityList();
                if (!CollectionUtils.isEmpty(ruthorityList)) {
                    for (SystemAuthoritys au : ruthorityList) {
                        if (au.getAuthorityType().equals(AuthorityTypeEnum.URL)) {
                            authorityMap.put(au.getAuthorityAction(), "1");
                        }
                    }
                }
            }
        }

        if (EnvHelper.isDev()) {
            // dev 模式下长时间缓存
            RedisUtil.set(CACHE_PREFIX_USER_AUS + user.getId(), authorityMap, USER_AUS_TIME_OUT_SECOND_DEV, RedisTagEnum.REDIS_TAG_DEFAULT);
        } else {
            RedisUtil.set(CACHE_PREFIX_USER_AUS + user.getId(), authorityMap, USER_AUS_TIME_OUT_SECOND, RedisTagEnum.REDIS_TAG_DEFAULT);
        }
    }

}
