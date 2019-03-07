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
 * @ClassName: RedisUtil
 * @Description: Redis工具类
 * @author klw
 * @date 2016年6月6日 下午1:10:55
 */
public class RedisUtil {
    
    /**
     * @Title: getRedisTemplateSpringByRedisTag
     * @Description: 根据redis标识获取对应的redis模板的springId
     * @param redisTag
     * @return
     */
    private static String getRedisTemplateSpringByRedisTag(RedisTagEnum redisTag){
	if(redisTag == null) return null;
	return redisTag.getTag();
    }
    
    @SuppressWarnings("unchecked")
    private static ValueOperations<String, Object> getRedisOperations(String redisTemplateSpringId){
	return SpringApplicationContextUtil.getBean(redisTemplateSpringId, RedisTemplate.class).opsForValue();
    }
    
    @SuppressWarnings("unchecked")
    private static RedisTemplate<String, Object> getRedisTemplate(String redisTemplateSpringId){
	return SpringApplicationContextUtil.getBean(redisTemplateSpringId, RedisTemplate.class);
    }
    
    /**
     * @Title: set
     * @Description: 设置要缓存的数据到redis
     * @param key  缓存的key
     * @param value  要被缓存的对象
     * @param seconds  缓存时长(秒),传null则永久缓存
     */
    public static void set(String key, Object value, Long seconds, RedisTagEnum redisTag){
	String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
	if(redisTemplateSpringId == null){
	    return;
	}
	ValueOperations<String, Object> redisOperations = getRedisOperations(redisTemplateSpringId);
	if(seconds == null) {
	    redisOperations.set(key, value);
	} else {
	    redisOperations.set(key, value, seconds, TimeUnit.SECONDS);
	}
    }
    
    /**
     * @Title: setnx
     * @author klw
     * @Description: 设置要缓存的数据到redis
     * @param key  缓存的key
     * @param value  要被缓存的对象
     * @param seconds  缓存时长(秒),传null则永久缓存
     * @param redisTag redis实例标识
     * @return 如果key不存在才会设置成功并返回true,如果key存在则设置失败返回false,异常情况(例如redisTag对应的实例不存在)返回null
     */
    public static Boolean setnx(String key, Object value, Long seconds, RedisTagEnum redisTag){
	String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
	if(redisTemplateSpringId == null){
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
     * @Title: setInPipeline
     * @author klw
     * @Description: 利用 reids的pipeline批量插入数据
     * @param pipelineRedisBeanList
     * @param redisTag
     */
    public static void setInPipeline(final List<PipelineRedisBean> pipelineRedisBeanList, RedisTagEnum redisTag){
	String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
	if(redisTemplateSpringId == null){
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
                    if(bean.getSeconds() != null){
                        connection.expire(bean.getKey().getBytes(), bean.getSeconds());   //设置KEY缓存时长(秒)
                    }
        	});
        	connection.closePipeline();
		return null;
            }
        });
    }
    
    /**
     * @Title: get
     * @Description: 根据缓存key获取被缓存的对象
     * @param key  缓存的key的byte[]
     * @param redisTag 此参数指对应的Spring配置的redis缓存库
     * @return
     */
    public static Object get(String key, RedisTagEnum redisTag){
	String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
	if(redisTemplateSpringId == null){
	    return null;
	}
	return getRedisOperations(redisTemplateSpringId).get(key);
    }
    
    
    
    /**
     * @Title: getAndUpdateExpire
     * @Description: 从redis缓存取出并延迟失效时间
     * @param key
     * @return
     */
    public static Object getAndUpdateExpire(String key, Long seconds, RedisTagEnum redisTag){
	String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
	if(redisTemplateSpringId == null){
	    return null;
	}
	Object returnValue = getRedisOperations(redisTemplateSpringId).get(key);
	getRedisTemplate(redisTemplateSpringId).expire(key, seconds, TimeUnit.SECONDS);
	return returnValue;
    }
    
    /**
     * @Title: updateExpire
     * @Description: 延迟失效时间
     * @param key
     * @param seconds
     * @param redisTag
     */
    public static void updateExpire(String key, Long seconds, RedisTagEnum redisTag){
	String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
	if(redisTemplateSpringId == null){
	    return;
	}
	getRedisTemplate(redisTemplateSpringId).expire(key, seconds, TimeUnit.SECONDS);
    }
    
    /**
     * @Title: containsKey
     * @Description: 是否包含传入的key
     * @param key
     * @return
     */
    public static boolean containsKey(String key, RedisTagEnum redisTag){
	String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
	if(redisTemplateSpringId == null){
	    return false;
	}
	return getRedisTemplate(redisTemplateSpringId).hasKey(key);
    }
    
    /**
     * @Title: keys
     * @Description: 查找key,如查找以 XXX_开头的key,则传入  XXX_*
     * @param keyPattern
     * @return
     */
    public static Set<String> keys(String keyPattern, RedisTagEnum redisTag){
	String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
	if(redisTemplateSpringId == null){
	    return null;
	}
	return getRedisTemplate(redisTemplateSpringId).keys(keyPattern);
    }
    
    /**
     * @Title: del
     * @author klw
     * @Description: 根据多个缓存key删除
     * @param redisTag
     * @param keyByte
     * @return
     */
    public static Long del(RedisTagEnum redisTag, Collection<String> keys ){
	String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
	if(redisTemplateSpringId == null){
	    return null;
	}
	return getRedisTemplate(redisTemplateSpringId).delete(keys);
    }
    
    /**
     * @Title: del
     * @Description: 根据缓存key删除被缓存的对象
     * @param key  缓存的key
     */
    public static Boolean del(String key, RedisTagEnum redisTag){
	String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
	if(redisTemplateSpringId == null){
	    return null;
	}
	return getRedisTemplate(redisTemplateSpringId).delete(key);
    }
    
    /**
     * @Title: lpush
     * @Description: 将一个 value 插入到key 对应的队列列表  的表头
     * @param key  队列名 key
     * @param value  要插入的value
     * @param redisTag
     */
    public static void lpush(final String key, final Object value, RedisTagEnum redisTag) {
	String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
	if(redisTemplateSpringId == null){
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
     * @Title: rpop
     * @Description: 移除并返回key对应的队列列表的尾元素。
     * @param key
     * @param redisTag
     * @return
     */
    public static Object rpop(final String key, RedisTagEnum redisTag){
	String redisTemplateSpringId = getRedisTemplateSpringByRedisTag(redisTag);
	if(redisTemplateSpringId == null){
	    return null;
	}
	RedisTemplate<String, Object> redisTemplate = getRedisTemplate(redisTemplateSpringId);
	Object value = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
        	byte[] keyByte = key.getBytes();
                if (connection.exists(keyByte)) {
                    byte[] value = connection.rPop(keyByte);
                    if(value == null) {
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
    
}
