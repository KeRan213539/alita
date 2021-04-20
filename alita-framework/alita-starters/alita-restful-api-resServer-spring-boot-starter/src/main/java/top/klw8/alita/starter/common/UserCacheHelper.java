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
 * 用户缓存帮助类
 * 2019年1月10日 下午4:46:26
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

    public Map<String, List<String>> getUserAuthoritysResource(String userId, String appTag) {
        long cacheTimeout;
        if (EnvHelper.isDev()) {
            cacheTimeout = USER_AUS_TIME_OUT_SECOND_DEV;
        } else {
            cacheTimeout = USER_AUS_TIME_OUT_SECOND;
        }
        Map<String, List<String>> authoritysResourceMap  = (Map<String, List<String>>) RedisUtil.getAndUpdateExpire(CACHE_PREFIX_USER_AUTHORITYS_RESOURCE + appTag + "_"  + userId, cacheTimeout, RedisTagEnum.REDIS_TAG_DEFAULT);
        // 如果缓存中的权限是null,重新查一次
        if (authoritysResourceMap == null) {
            try {
                adminProvider.refreshUserAuthoritys(userId).get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("", e);
            }
            return (Map<String, List<String>>) RedisUtil.getAndUpdateExpire(CACHE_PREFIX_USER_AUTHORITYS_RESOURCE + appTag + "_"  + userId, cacheTimeout, RedisTagEnum.REDIS_TAG_DEFAULT);
        }
        return authoritysResourceMap;
    }

    /**
     * 移除缓存中的用户权限,包括资源权限
     * 2020/8/17 15:14
     * @param:
     * @return void
     */
    public void removeUserAuthoritysInCache(String userId, String appTag){
        RedisUtil.del(CACHE_PREFIX_USER_AUS + appTag + "_" + userId, RedisTagEnum.REDIS_TAG_DEFAULT);
        RedisUtil.del(CACHE_PREFIX_USER_AUTHORITYS_RESOURCE + appTag + "_"  + userId, RedisTagEnum.REDIS_TAG_DEFAULT);
    }

}
