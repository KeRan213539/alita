package top.klw8.alita.starter.common;

import lombok.extern.slf4j.Slf4j;
import top.klw8.alita.helper.EnvHelper;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;
import top.klw8.alita.utils.redis.RedisTagEnum;
import top.klw8.alita.utils.redis.RedisUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static top.klw8.alita.starter.common.WebApiContext.*;

/**
 * @author klw
 * @ClassName: UserCacheHelper
 * @Description: 用户缓存帮助类
 * @date 2019年1月10日 下午4:46:26
 */
@Slf4j
public class UserCacheHelper {

    private IAuthorityAdminProvider adminProvider;

    public UserCacheHelper(IAuthorityAdminProvider adminProvider){
        this.adminProvider = adminProvider;
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getUserAuthority(String userId, String appTag) {
        long cacheTimeout;
        if (EnvHelper.isDev()) {
            cacheTimeout = USER_AUS_TIME_OUT_SECOND_DEV;
        } else {
            cacheTimeout = USER_AUS_TIME_OUT_SECOND;
        }
        Map<String, String> authorityMap = (Map<String, String>) RedisUtil.getAndUpdateExpire(CACHE_PREFIX_USER_AUS + appTag + "_" + userId, cacheTimeout, RedisTagEnum.REDIS_TAG_DEFAULT);
        // 如果缓存中的权限是null,重新查一次
        if (authorityMap == null) {
            try {
                adminProvider.refreshUserAuthoritys(userId).get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("", e);
            }
            return (Map<String, String>) RedisUtil.getAndUpdateExpire(CACHE_PREFIX_USER_AUS + appTag + "_"  + userId, cacheTimeout, RedisTagEnum.REDIS_TAG_DEFAULT);
        }
        return authorityMap;
    }

    public  Map<String, List<String>> getUserDataSecured(String userId, String appTag) {
        long cacheTimeout;
        if (EnvHelper.isDev()) {
            cacheTimeout = USER_AUS_TIME_OUT_SECOND_DEV;
        } else {
            cacheTimeout = USER_AUS_TIME_OUT_SECOND;
        }
        Map<String, List<String>> dataSecuredsMap  = (Map<String, List<String>>) RedisUtil.getAndUpdateExpire(CACHE_PREFIX_USER_DATA_SECUREDS + appTag + "_"  + userId, cacheTimeout, RedisTagEnum.REDIS_TAG_DEFAULT);
        // 如果缓存中的权限是null,重新查一次
        if (dataSecuredsMap == null) {
            try {
                adminProvider.refreshUserAuthoritys(userId).get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("", e);
            }
            return (Map<String, List<String>>) RedisUtil.getAndUpdateExpire(CACHE_PREFIX_USER_DATA_SECUREDS + appTag + "_"  + userId, cacheTimeout, RedisTagEnum.REDIS_TAG_DEFAULT);
        }
        return dataSecuredsMap;
    }

}
