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
package top.klw8.alita.starter.utils;

import java.util.List;
import java.util.Map;

import org.springframework.http.server.reactive.ServerHttpRequest;

import lombok.extern.slf4j.Slf4j;
import top.klw8.alita.utils.TokenUtil;

/**
 * token 工具
 * 2018年11月30日 上午11:58:27
 */
@Slf4j
public class ResServerTokenUtil extends TokenUtil {

    /**
     * @param request
     * @return
     * 获取token中的userId
     */
    public static String getUserId(ServerHttpRequest request) {
        Object userId = getTokenAdditionalData(request, "userId");
        if (userId instanceof Integer) {
            return String.valueOf(userId);
        }
        return (String) userId;
    }

    /**
     * @param request
     * @param key
     * @return
     * 根据key获取token中的额外数据
     */
    public static Object getTokenAdditionalData(ServerHttpRequest request, String key) {
        Map<String, Object> allTokenData = getAllTokenData(request);
        if (allTokenData == null) {
            return null;
        }
        return allTokenData.get(key);
    }

    /**
     * @param request
     * @return
     * 获取token中的所有数据
     */
    public static Map<String, Object> getAllTokenData(ServerHttpRequest request) {
        List<String> tokenList = request.getHeaders().get("Authorization");
        if (tokenList == null || tokenList.isEmpty()) {
            return null;
        }
        String jwtToken = tokenList.get(0);
        return getAllTokenData(jwtToken);
    }
    
    public static String getToken(ServerHttpRequest request){
        List<String> tokenList = request.getHeaders().get("Authorization");
        if (tokenList == null || tokenList.isEmpty()) {
            return null;
        }
        return tokenList.get(0);
    }

}
