package top.klw8.alita.utils.redis;

/**
 * @ClassName: RedisTagEnum
 * @Description: redis 标识 枚举
 * @author klw
 * @date 2017年5月16日 下午5:16:11
 */
public enum RedisTagEnum {

    /**
     * @Fields REDIS_TAG_DEFAULT : alita redis缓存(默认)
     */
    REDIS_TAG_DEFAULT("redisDefaultCacheTemplate"),
    
    REDIS_TAG_TEST("redisTest1Template")
    
    ;
    
    private String tag;
    
    private RedisTagEnum(String tag) {
	this.tag = tag;
    }
    
    public String getTag() {
	return this.tag;
    }
    
}
