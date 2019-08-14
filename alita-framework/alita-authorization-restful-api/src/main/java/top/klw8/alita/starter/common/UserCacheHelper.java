package top.klw8.alita.starter.common;

import static top.klw8.alita.starter.common.WebApiContext.CACHE_PREFIX_USER_AUS;
import static top.klw8.alita.starter.common.WebApiContext.USER_AUS_TIME_OUT_SECOND;
import static top.klw8.alita.starter.common.WebApiContext.USER_AUS_TIME_OUT_SECOND_DEV;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.service.utils.EntityUtil;
import top.klw8.alita.utils.redis.RedisTagEnum;
import top.klw8.alita.utils.redis.RedisUtil;

/**
 * @author klw
 * @ClassName: UserCacheHelper
 * @Description: 用户缓存帮助类
 * @date 2019年1月10日 下午4:46:26
 */
@Component
public class UserCacheHelper {

    @Reference(async = true)
    private IAlitaUserProvider userService;

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

    @SuppressWarnings("unchecked")
    public Map<String, String> getUserAuthority(String userId) throws ExecutionException, InterruptedException {
        long cacheTimeout = 0;
        if (EnvHelper.isDev()) {
            cacheTimeout = USER_AUS_TIME_OUT_SECOND_DEV;
        } else {
            cacheTimeout = USER_AUS_TIME_OUT_SECOND;
        }
        Map<String, String> authorityMap = (Map<String, String>) RedisUtil.getAndUpdateExpire(CACHE_PREFIX_USER_AUS + userId, cacheTimeout, RedisTagEnum.REDIS_TAG_DEFAULT);
        // 如果缓存中的权限是null,重新查一次
        if (authorityMap == null) {
            if (userService == null) {
                return null;
            }
            AlitaUserAccount user = userService.findUserById(userId).get();
            if (EntityUtil.isEntityEmpty(user)) {
                return null;
            }
            putUserInfo2Cache(user);
            return (Map<String, String>) RedisUtil.getAndUpdateExpire(CACHE_PREFIX_USER_AUS + userId, cacheTimeout, RedisTagEnum.REDIS_TAG_DEFAULT);
        }
        return authorityMap;
    }

}
