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

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import top.klw8.alita.base.springctx.SpringApplicationContextUtil;
import top.klw8.alita.utils.SerializableUitl;

/**
 * @author klw
 * @ClassName: RedisUtil
 * @Description: Redis工具类
 * @date 2016年6月6日 下午1:10:55
 */
public class RedisUtil {

    /**
     * @param redisTag
     * @return
     * @Title: getRedisTemplateSpringByRedisTag
     * @Description: 根据redis标识获取对应的redis模板的springId
     */
    private static <T extends Enum & IRedisTag> String getRedisTemplateSpringByRedisTag(T redisTag) {
        if (redisTag == null) return null;
        return redisTag.getTag();
    }

    @SuppressWarnings("unchecked")
    private static ValueOperations<String, Object> getRedisOperations(String redisTemplateSpringId) {
        return SpringApplicationContextUtil.getBean(redisTemplateSpringId, RedisTemplate.class).opsForValue();
    }

    @SuppressWarnings("unchecked")
    private static RedisTemplate<String, Object> getRedisTemplate(String redisTemplateSpringId) {
        return SpringApplicationContextUtil.getBean(redisTemplateSpringId, RedisTemplate.class);
    }

    /**
     * @param key     缓存的key
     * @param value   要被缓存的对象
     * @param seconds 缓存时长(秒),传null则永久缓存
     * @Title: set
     * @Description: 设置要缓存的数据到redis
     */
    public static <T extends Enum & IRedisTag> void set(String key, Object value, Long seconds, T redisTag) {
        String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
        if (redisTemplateSpringId == null) {
            return;
        }
        ValueOperations<String, Object> redisOperations = getRedisOperations(redisTemplateSpringId);
        if (seconds == null) {
            redisOperations.set(key, value);
        } else {
            redisOperations.set(key, value, seconds, TimeUnit.SECONDS);
        }
    }

    /**
     * @param key      缓存的key
     * @param value    要被缓存的对象
     * @param seconds  缓存时长(秒),传null则永久缓存
     * @param redisTag redis实例标识
     * @return 如果key不存在才会设置成功并返回true, 如果key存在则设置失败返回false, 异常情况(例如redisTag对应的实例不存在)返回null
     * @Title: setnx
     * @author klw
     * @Description: 设置要缓存的数据到redis, 只在键 key 不存在的情况下， 将键 key 的值设置为 value 。若键 key 已经存在， 则 SETNX 命令不做任何动作。
     */
    public static <T extends Enum & IRedisTag> Boolean setnx(String key, Object value, Long seconds, T redisTag) {
        String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
        if (redisTemplateSpringId == null) {
            return null;
        }
        RedisTemplate<String, Object> redisTemplate = getRedisTemplate(redisTemplateSpringId);
        return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyByte = key.getBytes();
                byte[] objByte = SerializableUitl.ObjectToByte(value);
                Boolean isSetSucc = connection.setNX(keyByte, objByte);
                if (isSetSucc != null && isSetSucc.booleanValue() && seconds != null) {
                    connection.expire(keyByte, seconds); // 设置KEY缓存时长(秒)
                }
                return isSetSucc;
            }
        });
    }

    /**
     * @param pipelineRedisBeanList
     * @param redisTag
     * @Title: setInPipeline
     * @author klw
     * @Description: 利用 reids的pipeline批量插入数据
     */
    public static <T extends Enum & IRedisTag> void setInPipeline(final List<PipelineRedisBean> pipelineRedisBeanList, T redisTag) {
        String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
        if (redisTemplateSpringId == null) {
            return;
        }
        RedisTemplate<String, Object> redisTemplate = getRedisTemplate(redisTemplateSpringId);
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                pipelineRedisBeanList.forEach(bean -> {
                    byte[] objByte = SerializableUitl.ObjectToByte(bean.getValue());
                    connection.set(bean.getKey().getBytes(), objByte);
                    if (bean.getSeconds() != null) {
                        connection.expire(bean.getKey().getBytes(), bean.getSeconds());   //设置KEY缓存时长(秒)
                    }
                });
                connection.closePipeline();
                return null;
            }
        });
    }

    /**
     * @param key      缓存的key的byte[]
     * @param redisTag 此参数指对应的Spring配置的redis缓存库
     * @return
     * @Title: get
     * @Description: 根据缓存key获取被缓存的对象
     */
    public static <T extends Enum & IRedisTag> Object get(String key, T redisTag) {
        String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
        if (redisTemplateSpringId == null) {
            return null;
        }
        return getRedisOperations(redisTemplateSpringId).get(key);
    }


    /**
     * @param key
     * @return
     * @Title: getAndUpdateExpire
     * @Description: 从redis缓存取出并延迟失效时间
     */
    public static <T extends Enum & IRedisTag> Object getAndUpdateExpire(String key, Long seconds, T redisTag) {
        String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
        if (redisTemplateSpringId == null) {
            return null;
        }
        Object returnValue = getRedisOperations(redisTemplateSpringId).get(key);
        getRedisTemplate(redisTemplateSpringId).expire(key, seconds, TimeUnit.SECONDS);
        return returnValue;
    }

    /**
     * @param key
     * @param seconds
     * @param redisTag
     * @Title: updateExpire
     * @Description: 延迟失效时间
     */
    public static <T extends Enum & IRedisTag> void updateExpire(String key, Long seconds, T redisTag) {
        String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
        if (redisTemplateSpringId == null) {
            return;
        }
        getRedisTemplate(redisTemplateSpringId).expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * @param key
     * @return
     * @Title: containsKey
     * @Description: 是否包含传入的key
     */
    public static <T extends Enum & IRedisTag> boolean containsKey(String key, T redisTag) {
        String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
        if (redisTemplateSpringId == null) {
            return false;
        }
        return getRedisTemplate(redisTemplateSpringId).hasKey(key);
    }

    /**
     * @param keyPattern
     * @return
     * @Title: keys
     * @Description: 查找key, 如查找以 XXX_开头的key,则传入  XXX_*
     */
    public static <T extends Enum & IRedisTag> Set<String> keys(String keyPattern, T redisTag) {
        String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
        if (redisTemplateSpringId == null) {
            return null;
        }
        return getRedisTemplate(redisTemplateSpringId).keys(keyPattern);
    }

    /**
     * @param redisTag
     * @return
     * @Title: del
     * @author klw
     * @Description: 根据多个缓存key删除
     */
    public static <T extends Enum & IRedisTag> Long del(T redisTag, Collection<String> keys) {
        String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
        if (redisTemplateSpringId == null) {
            return null;
        }
        return getRedisTemplate(redisTemplateSpringId).delete(keys);
    }

    /**
     * @param key 缓存的key
     * @Title: del
     * @Description: 根据缓存key删除被缓存的对象
     */
    public static <T extends Enum & IRedisTag> Boolean del(String key, T redisTag) {
        String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
        if (redisTemplateSpringId == null) {
            return null;
        }
        return getRedisTemplate(redisTemplateSpringId).delete(key);
    }

    /**
     * @param key      队列名 key
     * @param value    要插入的value
     * @param redisTag
     * @Title: lpush
     * @Description: 将一个 value 插入到key 对应的队列列表  的表头
     */
    public static <T extends Enum & IRedisTag> void lpush(final String key, final Object value, T redisTag) {
        String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
        if (redisTemplateSpringId == null) {
            return;
        }
        RedisTemplate<String, Object> redisTemplate = getRedisTemplate(redisTemplateSpringId);
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] objByte = SerializableUitl.ObjectToByte(value);
                connection.lPush(key.getBytes(), objByte);
                return null;
            }
        });
    }

    /**
     * @param key
     * @param redisTag
     * @return
     * @Title: rpop
     * @Description: 移除并返回key对应的队列列表的尾元素。
     */
    public static <T extends Enum & IRedisTag> Object rpop(final String key, T redisTag) {
        String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
        if (redisTemplateSpringId == null) {
            return null;
        }
        RedisTemplate<String, Object> redisTemplate = getRedisTemplate(redisTemplateSpringId);
        Object value = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyByte = key.getBytes();
                if (connection.exists(keyByte)) {
                    byte[] value = connection.rPop(keyByte);
                    if (value == null) {
                        return null;
                    }
                    Object objValue = SerializableUitl.ByteToObject(value);
                    return objValue;
                }
                return null;
            }
        });
        return value;
    }

    public static <T extends Enum & IRedisTag> Long incr (String key, Long seconds, T redisTag) {
        String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
        if (redisTemplateSpringId == null) {
            throw new IllegalArgumentException("redisTag 对应的 redisTemplateSpringId 未配制");
        }
        RedisTemplate<String, Object> redisTemplate = getRedisTemplate(redisTemplateSpringId);
        return (Long) redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] keyByte = key.getBytes();
            Long result = connection.incr(keyByte);
            if (result != null && seconds != null) {
                connection.expire(keyByte, seconds); // 设置KEY缓存时长(秒)
            }
            return result;
        });
    }

}
