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
        // 用APP区分权限
        Map<String, Map<String, String>> appAuthorityMap = new HashMap<>(16);
        // 用APP区分数据权限
        Map<String, Map<String, List<String>>> appDataSecuredsMap = new HashMap<>(16);

        List<SystemRole> userRoles = user.getUserRoles();
        if (!CollectionUtils.isEmpty(userRoles)) {
            for (SystemRole userRole : userRoles) {
                // 权限入缓存
                List<SystemAuthoritys> ruthorityList = userRole.getAuthorityList();
                if (!CollectionUtils.isEmpty(ruthorityList)) {
                    for (SystemAuthoritys au : ruthorityList) {
                        if (au.getAuthorityType().equals(AuthorityTypeEnum.URL)) {
                            // 获取指定APP下的权限缓存 Map<权限url, "1">
                            Map<String, String> authorityMap = appAuthorityMap.get(au.getAppTag());
                            if(null == authorityMap){
                                authorityMap = new HashMap<>(16);
                                appAuthorityMap.put(au.getAppTag(), authorityMap);
                            }
                            // 只把URL类型的放缓存,MENU的不放(因为MENU是给前端生成菜单的,不是服务中的资源)
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

                        // 获取指定APP下的数据权限缓存   Map<权限url, List<资源标识>>
                        Map<String, List<String>> dataSecuredsMap = appDataSecuredsMap.get(dataSecured.getAppTag());
                        if(null == dataSecuredsMap){
                            dataSecuredsMap = new HashMap<>(16);
                            appDataSecuredsMap.put(dataSecured.getAppTag(), dataSecuredsMap);
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

        // 缓存时长
        long userAusTimeoutSecond;
        if (EnvHelper.isDev()) {
            // dev 模式下长时间缓存
            userAusTimeoutSecond = USER_AUS_TIME_OUT_SECOND_DEV;
        } else {
            userAusTimeoutSecond = USER_AUS_TIME_OUT_SECOND;
        }

        appAuthorityMap.forEach((key, value) ->
                RedisUtil.set(CACHE_PREFIX_USER_AUS + key + "_" + user.getId(), value, userAusTimeoutSecond, RedisTagEnum.REDIS_TAG_DEFAULT)
        );

        appDataSecuredsMap.forEach((key, value) ->
                RedisUtil.set(CACHE_PREFIX_USER_DATA_SECUREDS + key + "_" + user.getId(), value, userAusTimeoutSecond, RedisTagEnum.REDIS_TAG_DEFAULT));
    }

}
