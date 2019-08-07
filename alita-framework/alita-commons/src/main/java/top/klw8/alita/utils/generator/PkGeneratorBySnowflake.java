package top.klw8.alita.utils.generator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

import top.klw8.alita.base.springctx.SpringApplicationContextUtil;

/**
 * @ClassName: PkGeneratorBySnowflake
 * @Description: 使用雪花算法的数据库主键生成器
 * @author klw
 * @date 2018年12月25日 下午3:10:16
 */
public enum PkGeneratorBySnowflake {

    INSTANCE;
    
    private SnowflakeGenerator generator = null;
    
    /**
     * @author klw
     * @Fields isInitFailed : 是否初始化过并失败
     */
    private boolean isInitFailed = false;
    
    
    public long nextId() {
	if (isInitFailed) {
	    throw new IllegalArgumentException("没有找到 pkGenerator 相关配制或者配制错误,生成器将不工作!");
	}
	if(generator == null) {
	    synchronized (this) {
		if(generator == null) {
		    Environment env = SpringApplicationContextUtil.getBean(Environment.class);
		    // 雪花算法需要的 datacenterId 和 workerId
		    String strDatacenterId = env.getProperty("alita.pkGenerator.datacenterId");
		    String strWorkerId = env.getProperty("alita.pkGenerator.workerId");
		    if(StringUtils.isAnyBlank(strDatacenterId, strWorkerId)) {
			isInitFailed = true;
			throw new IllegalArgumentException("没有找到 pkGenerator 相关配制或者配制错误,生成器将不工作!");
		    }
		    long datacenterId = -1;
		    long workerId = -1;
		    try {
			datacenterId = Long.valueOf(strDatacenterId).longValue();
			workerId = Long.valueOf(strWorkerId).longValue();
		    } catch (NumberFormatException ex) {
			isInitFailed = true;
			throw new IllegalArgumentException("没有找到 pkGenerator 相关配制或者配制错误,生成器将不工作!");
		    }
		    generator = new SnowflakeGenerator(workerId, datacenterId);
		    
		}
	    }
	}
	return generator.nextId();
    }
    
}
