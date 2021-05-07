/*
 * Copyright 2018-2021, ranke (213539@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import top.klw8.alita.entitys.authority.AlitaAuthoritysMenu;
import top.klw8.alita.entitys.authority.AlitaAuthoritysResource;
import top.klw8.alita.entitys.authority.AlitaRole;
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
 * 用户缓存帮助类
 * 2019-08-14 16:51
 */
@Slf4j
@Component
public class UserCacheHelper {

    /**
     * @param user
     * 把必要的用户相关数据放入缓存(目前只放入了权限)
     */
    public void putUserInfo2Cache(AlitaUserAccount user) {
        // 用APP区分权限
        Map<String, Map<String, String>> appAuthorityMap = new HashMap<>(16);
        // 用APP区分资源权限
        Map<String, Map<String, List<String>>> appAuthoritysResourceMap = new HashMap<>(16);

        List<AlitaRole> userRoles = user.getUserRoles();
        if (!CollectionUtils.isEmpty(userRoles)) {
            for (AlitaRole userRole : userRoles) {
                // 权限入缓存
                List<AlitaAuthoritysMenu> ruthorityList = userRole.getAuthorityList();
                if (!CollectionUtils.isEmpty(ruthorityList)) {
                    for (AlitaAuthoritysMenu au : ruthorityList) {
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

                // 资源权限入缓存
                List<AlitaAuthoritysResource> authoritysResourceList = userRole.getAuthoritysResourceList();
                if (!CollectionUtils.isEmpty(authoritysResourceList)) {
                    for (AlitaAuthoritysResource authoritysResource : authoritysResourceList) {
                        String authoritysResourceMapKey;
                        if(StringUtils.isBlank(authoritysResource.getAuthoritysId())){
                            // 全局资源权限
                            authoritysResourceMapKey = CACHE_KEY_USER_PUBLIC_AUTHORITYS_RESOURCE;
                        } else {
                            // 权限中的资源权限
                            authoritysResourceMapKey = authoritysResource.getAuthorityUrl();
                        }

                        // 获取指定APP下的资源权限缓存   Map<权限url, List<资源标识>>
                        Map<String, List<String>> authoritysResourceMap = appAuthoritysResourceMap.get(authoritysResource.getAppTag());
                        if(null == authoritysResourceMap){
                            authoritysResourceMap = new HashMap<>(16);
                            appAuthoritysResourceMap.put(authoritysResource.getAppTag(), authoritysResourceMap);
                        }

                        List<String> authoritysResourceListInAu = authoritysResourceMap.get(authoritysResourceMapKey);
                        if(null == authoritysResourceListInAu){
                            authoritysResourceListInAu = new ArrayList<>(16);
                            authoritysResourceMap.put(authoritysResourceMapKey, authoritysResourceListInAu);
                        }
                        authoritysResourceListInAu.add(authoritysResource.getResource());
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

        appAuthoritysResourceMap.forEach((key, value) ->
                RedisUtil.set(CACHE_PREFIX_USER_AUTHORITYS_RESOURCE + key + "_" + user.getId(), value, userAusTimeoutSecond, RedisTagEnum.REDIS_TAG_DEFAULT));
    }

}
