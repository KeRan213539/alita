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
package top.klw8.alita.utils.redis;

import org.apache.commons.lang3.StringUtils;

/**
 * token 放redis的相关操作工具
 * 2020/8/14 15:32
 */
public class TokenRedisUtil {

    private static final String ACCESS_TOKEN_KEY_PREFIX = "AUTH_USER_ACCESS_TOKEN_";

    private static final String REFRESH_TOKEN_KEY_PREFIX = "AUTH_USER_REFRESH_TOKEN_";

    // accessToken 超时时间, 默认12小时
    private static long accessTokenTimeoutSeconds = 60 * 60 * 12;

    // refreshToken 超时时间, 默认30天
    private static long refreshTokenTimeoutSeconds = 60 * 60 * 24 * 30;

    // 是否设置过token的超时
    private static boolean tokenTimeoutSet = false;

    public static void setTokenTimeout(int accessTokenTimeoutSeconds, int refreshTokenTimeoutSeconds){
        if(!tokenTimeoutSet){
            TokenRedisUtil.accessTokenTimeoutSeconds = accessTokenTimeoutSeconds;
            TokenRedisUtil.refreshTokenTimeoutSeconds = refreshTokenTimeoutSeconds;
            TokenRedisUtil.tokenTimeoutSet = true;
        }
    }

    public static void storeAccessToken(String userId, String accessToken, String appTag, String channelTag){
        RedisUtil.set(ACCESS_TOKEN_KEY_PREFIX + "_" + appTag + "_" + channelTag
                + userId, accessToken, accessTokenTimeoutSeconds, RedisTagEnum.REDIS_TAG_DEFAULT);
    }

    public static void storeRefreshToken(String userId, String refreshToken, String appTag, String channelTag){
        RedisUtil.set(REFRESH_TOKEN_KEY_PREFIX + "_" + appTag + "_" + channelTag
                + userId, refreshToken, refreshTokenTimeoutSeconds, RedisTagEnum.REDIS_TAG_DEFAULT);
    }

    public static String getAccessToken(String userId, String appTag, String channelTag){
        return (String) RedisUtil.getAndUpdateExpire(ACCESS_TOKEN_KEY_PREFIX + "_" + appTag + "_" + channelTag
                + userId, accessTokenTimeoutSeconds, RedisTagEnum.REDIS_TAG_DEFAULT);
    }

    public static String getRefreshToken(String userId, String appTag, String channelTag){
        return (String) RedisUtil.getAndUpdateExpire(REFRESH_TOKEN_KEY_PREFIX + "_" + appTag + "_" + channelTag
                + userId, refreshTokenTimeoutSeconds, RedisTagEnum.REDIS_TAG_DEFAULT);
    }

    public static void removeAccessToken(String userId, String appTag, String channelTag){
        RedisUtil.del(ACCESS_TOKEN_KEY_PREFIX + "_" + appTag + "_" + channelTag
                + userId, RedisTagEnum.REDIS_TAG_DEFAULT);
    }

    public static void removeRefreshToken(String userId, String appTag, String channelTag){
        RedisUtil.del(REFRESH_TOKEN_KEY_PREFIX + "_" + appTag + "_" + channelTag
                + userId, RedisTagEnum.REDIS_TAG_DEFAULT);
    }

    public static boolean checkAccessTokenInRedis(String userId, String token, String appTag, String channelTag){
        String accessToken = getAccessToken(userId, appTag, channelTag);
        if(StringUtils.isNotBlank(accessToken) && accessToken.equals(token)){
            return true;
        }
        return false;
    }

    public static boolean checkRefreshTokenInRedis(String userId, String token, String appTag, String channelTag){
        String refreshToken = getRefreshToken(userId, appTag, channelTag);
        if(StringUtils.isNotBlank(refreshToken) && refreshToken.equals(token)){
            return true;
        }
        return false;
    }

}
