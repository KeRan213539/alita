package top.klw8.alita.service.base.mongo.beans;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: PagePrarmBean
 * @Description: 分页参数Bean
 * @author klw
 * @date 2018年10月10日 下午1:43:34
 */
@Getter
@Setter
public class PagePrarmBean implements Serializable, Cloneable {

    private static final long serialVersionUID = -2029324365320879551L;

    /**
     * @author klw
     * @Fields page : 页码(比较方式时该值无用,原样返回)
     */
    private Integer page; 
    
    /**
     * @author klw
     * @Fields limit : 每页数据量(默认10)
     */
    private Integer limit = 10;
    
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
     * @Fields sortDirection : 排序(正序: ASC; 倒序: DESC)
     */
    private String sortDirection;
    
    /**
     * @author klw
     * @Fields sortFiled : 排序字段
     */
    private String sortFiled;
    
    public static PagePrarmBean create(String keywords, String keywordsField, Integer limit, Integer page, String sortDirection, String sortFiled) {
	PagePrarmBean pagePrarmBean = new PagePrarmBean();
	pagePrarmBean.setKeywords(keywords);
	pagePrarmBean.setKeywordsField(keywordsField);
	pagePrarmBean.setLimit(limit);
	pagePrarmBean.setPage(page);
	pagePrarmBean.setSortDirection(sortDirection);
	pagePrarmBean.setSortFiled(sortFiled);
	return pagePrarmBean;
    }
    
}
