/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.utils.redis;

/**
 * @ClassName: RedisTagEnum
 * @Description: redis 标识 枚举
 * @author klw
 * @date 2017年5月16日 下午5:16:11
 */
public enum RedisTagEnum implements IRedisTag {

    /**
     * @Fields REDIS_TAG_DEFAULT : alita redis缓存(默认)
     */
    REDIS_TAG_DEFAULT("redisDefaultCacheTemplate"),
    
    REDIS_TAG_TEST("redisTest1Template")
    
    ;
    
    private String tag;
    
    RedisTagEnum(String tag) {
	this.tag = tag;
    }
    
    public String getTag() {
	return this.tag;
    }
    
}
