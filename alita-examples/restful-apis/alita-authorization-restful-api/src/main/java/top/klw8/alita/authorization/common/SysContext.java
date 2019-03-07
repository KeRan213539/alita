package top.klw8.alita.authorization.common;

/**
 * @ClassName: SysContext
 * @Description: 认证中心通用常量
 * @author klw
 * @date 2018年11月23日 上午10:50:33
 */
public class SysContext {

    //===========================短信验证码相关===========================
    
    /**
     * @author klw
     * @Fields SMS_CODE_TIMEOUT_SECOND : 短信验证码失效时间, 秒
     */
    public static final long SMS_CODE_TIMEOUT_SECOND = 300;
    
    /**
     * @author klw
     * @Fields SMS_CODE_TIMEOUT_MSG : 短信验证码失效时间(描述)
     */
    public static final String SMS_CODE_TIMEOUT_MSG = "5分钟";
    
    /**
     * @author klw
     * @Fields SMS_CODE_CACHE_PREFIX : 短信验证码缓存前缀
     */
    public static final String SMS_CODE_CACHE_PREFIX = "SMS_CODE_";
    
    /**
     * @author klw
     * @Fields ONE_MINUTE : 一分钟
     */
    public static final long ONE_MINUTE = 60 * 1000;
    
}
