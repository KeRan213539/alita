package top.klw8.alita.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemDataSecured;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.utils.redis.RedisTagEnum;
import top.klw8.alita.utils.redis.RedisUtil;

import java.util.ArrayList;
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
        // 权限放入缓存
        Map<String, String> authorityMap = new HashMap<>();
        // 数据权限入缓存   Map<权限url, List<资源标识>>
        Map<String, List<String>> dataSecuredsMap = new HashMap<>(16);
        List<SystemRole> userRoles = user.getUserRoles();
        if (!CollectionUtils.isEmpty(userRoles)) {
            for (SystemRole userRole : userRoles) {
                // 权限入缓存
                List<SystemAuthoritys> ruthorityList = userRole.getAuthorityList();
                if (!CollectionUtils.isEmpty(ruthorityList)) {
                    for (SystemAuthoritys au : ruthorityList) {
                        if (au.getAuthorityType().equals(AuthorityTypeEnum.URL)) {
                            authorityMap.put(au.getAuthorityAction(), "1");
                        }
                    }
                }
                // 数据权限入缓存
                List<SystemDataSecured> dataSecuredList = userRole.getDataSecuredList();
                if (!CollectionUtils.isEmpty(dataSecuredList)) {
                    for (SystemDataSecured dataSecured : dataSecuredList) {
                        String dataSecuredsMapKey;
                        if(StringUtils.isBlank(dataSecured.getAuthoritysId())){
                            // 全局数据权限
                            dataSecuredsMapKey = CACHE_KEY_USER_PUBLIC_DATA_SECUREDS;
                        } else {
                            // 权限中的数据权限
                            dataSecuredsMapKey = dataSecured.getAuthorityUrl();
                        }
                        List<String> dataSecuredListInAu = dataSecuredsMap.get(dataSecuredsMapKey);
                        if(null == dataSecuredListInAu){
                            dataSecuredListInAu = new ArrayList<>(16);
                            dataSecuredsMap.put(dataSecuredsMapKey, dataSecuredListInAu);
                        }
                        dataSecuredListInAu.add(dataSecured.getResource());
                    }
                }
            }
        }

        if (EnvHelper.isDev()) {
            // dev 模式下长时间缓存
            RedisUtil.set(CACHE_PREFIX_USER_AUS + user.getId(), authorityMap, USER_AUS_TIME_OUT_SECOND_DEV, RedisTagEnum.REDIS_TAG_DEFAULT);
            RedisUtil.set(CACHE_PREFIX_USER_DATA_SECUREDS + user.getId(), dataSecuredsMap, USER_AUS_TIME_OUT_SECOND_DEV, RedisTagEnum.REDIS_TAG_DEFAULT);
        } else {
            RedisUtil.set(CACHE_PREFIX_USER_AUS + user.getId(), authorityMap, USER_AUS_TIME_OUT_SECOND, RedisTagEnum.REDIS_TAG_DEFAULT);
            RedisUtil.set(CACHE_PREFIX_USER_DATA_SECUREDS + user.getId(), dataSecuredsMap, USER_AUS_TIME_OUT_SECOND, RedisTagEnum.REDIS_TAG_DEFAULT);
        }
    }

}
