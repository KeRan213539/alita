package top.klw8.alita.starter.common;

/**
 * @ClassName: WebApiContext
 * @Description: webapi 通用常量
 * @author klw
 * @date 2018年12月6日 下午2:05:53
 */
public class WebApiContext {

    /**
     * @author klw
     * @Fields CACHE_PREFIX_USER_AU : 用户的权限缓存前缀
     */
    public static final String CACHE_PREFIX_USER_AUS = "USER_AUS_";

    /**
     * @author klw(213539@qq.com)
     * @Description: 用户的数据权限缓存前缀
     */
    public static final String CACHE_PREFIX_USER_DATA_SECUREDS = "USER_DATA_SECUREDS_";

    /**
     * @author klw(213539@qq.com)
     * @Description: 用户数据权限缓存中的全局数据权限的KEY
     */
    public static final String CACHE_KEY_USER_PUBLIC_DATA_SECUREDS = "USER_PUBLIC_DATA_SECUREDS";
    
    /**
     * @author klw
     * @Fields USER_AUS_TIME_OUT_SECOND : 用户的权限缓存时长(秒)
     */
    public static final long USER_AUS_TIME_OUT_SECOND = 60 * 60;
    
    /**
     * @author klw
     * @Fields USER_AUS_TIME_OUT_SECOND_DEV : dev模式下用户的权限缓存时长(秒)
     */
    public static final long USER_AUS_TIME_OUT_SECOND_DEV = 60 * 60 * 24 * 30;
    
}
