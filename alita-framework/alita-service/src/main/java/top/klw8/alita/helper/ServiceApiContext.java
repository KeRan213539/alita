package top.klw8.alita.helper;

/**
 * @author freedom
 * @version 1.0
 * @ClassName ServiceApiContext
 * @Description 通用常量
 * @date 2019-08-14 16:59
 */
public class ServiceApiContext {

    /**
     * @author klw
     * @Fields CACHE_PREFIX_USER_AU : 用户的权限缓存前缀
     */
    public static final String CACHE_PREFIX_USER_AUS = "USER_AUS_";

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
