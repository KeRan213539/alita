package top.klw8.alita.service.base.mongo.common;

/**
 * @ClassName: DataSecurityCfg
 * @Description: 数据安全配制相关
 * @author klw
 * @date 2019年1月25日 下午6:43:48
 */
public class DataSecurityCfg {

    /**
     * @author klw
     * @Fields PAGE_DATA_LIMIT_MAX : 分页时的单页最大数据量上限
     */
    public static final int PAGE_DATA_LIMIT_MAX = 50;
    
    /**
     * @author klw
     * @Fields PAGE_DATA_PAGE_MAX : 比较大小方式分页时,一次获取多少页的最大限制
     */
    public static final int PAGE_DATA_PAGE_MAX = 20;
    
    
    /**
     * @author klw
     * @Fields QUERY_LIST_MAX_LIMIT : 列表查询时(不分页查询,数据量少才会使用该方式查询), 获取的最大数据量
     */
    public static final int QUERY_LIST_MAX_LIMIT = 1000;
    
}
