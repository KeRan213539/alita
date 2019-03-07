package top.klw8.alita.utils.redis;

/**
 * @ClassName: PipelineRedisBean
 * @Description: redis 批量插入的bean
 * @author klw
 * @date 2018年6月11日 上午11:28:28
 */
public class PipelineRedisBean {
    
    public PipelineRedisBean(String key, Object value, Long seconds) {
	this.key = key;
	this.value = value;
	this.seconds = seconds;
    }

    /**
     * @author klw
     * @Fields key : 缓存的key
     */
    private String key;
    
    /**
     * @author klw
     * @Fields value : 要被缓存的对象
     */
    private Object value;
    
    /**
     * @author klw
     * @Fields seconds : 缓存时长(秒),传null则永久缓存
     */
    private Long seconds;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Long getSeconds() {
        return seconds;
    }

    public void setSeconds(Long seconds) {
        this.seconds = seconds;
    }
    
}
