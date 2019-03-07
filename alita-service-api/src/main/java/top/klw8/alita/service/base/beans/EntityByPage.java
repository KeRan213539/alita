package top.klw8.alita.service.base.beans;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import top.klw8.alita.config.SysConstants;
import top.klw8.alita.service.base.entitys.BaseEntity;
import top.klw8.alita.utils.BeanUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2018/7/3.
 */
/**
 * @ClassName: EntityByPage
 * @Description: 分页bean
 * @author klw
 * @date 2018年12月20日 下午2:57:16
 */
@ApiModel(value = "分页bean", description = "分页bean")
@Data
public class EntityByPage<T extends BaseEntity> implements Serializable, Cloneable {
    
    
    private static final long serialVersionUID = -5450108848222011361L;

    private T baseBean;

    /**
     * @author klw
     * @Fields keywords : 搜索关键字
     */
    private String keywords;
    
    /**
     * @author klw
     * @Fields keywordsField : 搜索关键字所在字段
     */
    private String keywordsField;
    
    /**
     * @author klw
     * @Fields page : 页码
     */
    private int page;
    
    /**
     * @author klw
     * @Fields limit : 每页条数
     */
    private int limit;
    
    /**
     * @author klw
     * @Fields total : 数据总量
     */
    private long total;
    
    /**
     * @author klw
     * @Fields pages : 总页数
     */
    private int pages;
    
    /**
     * @author klw
     * @Fields startPage : 从第几条开始查
     */
    private int startPage;

    public static <T extends BaseEntity> EntityByPage<T> initParam(T baseBean, Integer page, Integer limit,String keywords, String keywordsField){
        EntityByPage<T> entityByPage = new EntityByPage<T>();
        entityByPage.baseBean = baseBean;
        entityByPage.page = page;
        entityByPage.startPage = null ==page?null:(page-1)*limit;
        entityByPage.limit = limit;
        entityByPage.keywords = keywords;
        entityByPage.keywordsField = keywordsField;

        return entityByPage;
    }

    public static <T extends BaseEntity> EntityByPage<T> initParam(T baseBean){
        EntityByPage<T> entityByPage = new EntityByPage<T>();
        entityByPage.baseBean = baseBean;
        entityByPage.page = 1;
        entityByPage.limit = SysConstants.PAGE_LIST_SIZE;
        entityByPage.startPage = (entityByPage.getPage()-1)*entityByPage.getLimit();
        entityByPage.keywords = "";

        return entityByPage;
    }

    public long getStartPage(){
        if(this.getPage() > 0 && this.getLimit() > 0){
            this.startPage = (this.getPage()-1)*this.getLimit();

            return this.startPage;
        }

        return 0;
    }

    public Map<String,Object>createPageData(){
        return this.createPageData(0,new ArrayList<T>(0));
    }

    public Map<String,Object>createPageData(Map<String,Object> map){
        return this.createPageData(0,map);
    }

    private void init() {
        this.setPages((int) (0==this.limit?0:this.total%this.limit==0?this.total/this.limit:(this.total/this.limit)+1));
    }

    public Map<String,Object> createPageData(long total,List<?> list) {
        this.setTotal(total);
        this.init();
        Map<String,Object> map = new HashMap<String,Object>(4);
        List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
        for(Object o : list){
            try {
                Map<String,Object> beanMap = BeanUtil.objectToMap(o, BeanUtil.RETAIN_NULL);
                dataList.add(beanMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(0 != page && 0 != pages){
            map.put("page",this.getPage());
            map.put("pages",this.getPages());
            map.put("total",this.getTotal());

        }
        map.put("data",dataList);
        return map;
    }

    public Map<String,Object> createPageData(Integer total,Map<?,?> map) {
        Map<String,Object> resultMap = new HashMap<>(4);
        this.setTotal(total);
        this.init();
        resultMap.put("page",this.getPage());
        resultMap.put("pages",this.getPages());
        resultMap.put("total",this.getTotal());
        resultMap.put("data",map);
        return resultMap;
    }

    public static <T extends BaseEntity> Map<String,Object> createPageWithNoData(){
        EntityByPage<T> entityByPage = initParam(null);
        return entityByPage.createPageData(0,new ArrayList<T>());
    }

    public static <T extends BaseEntity> Map<String,Object> createPageWithCustom(Integer page,Integer limit,Integer total,List<?>list){
        EntityByPage<T> entityByPage = initParam(null,page,limit,null, null);
        return entityByPage.createPageData(total,list);
    }
}
