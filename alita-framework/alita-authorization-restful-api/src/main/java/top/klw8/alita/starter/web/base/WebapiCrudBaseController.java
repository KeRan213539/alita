package top.klw8.alita.starter.web.base;

import top.klw8.alita.entitys.base.BaseEntity;
import top.klw8.alita.entitys.base.mongo.IGeoSearchSupport;
import top.klw8.alita.entitys.base.mongo.ITextIndexedCustomSupport;
import top.klw8.alita.entitys.base.mongo.ITextIndexedSupport;
import top.klw8.alita.service.base.mongo.api.IBaseService;
import top.klw8.alita.service.base.mongo.beans.EntityByPage;
import top.klw8.alita.service.base.mongo.beans.PagePrarmBean;
import top.klw8.alita.service.base.mongo.dao.prarm.ForPageMode;
import top.klw8.alita.service.base.mongo.dao.prarm.ForPageMode.FieldDataType;
import top.klw8.alita.service.utils.EntityUtil;
import top.klw8.alita.starter.web.base.enums.SearchTypeEnum;
import top.klw8.alita.starter.web.base.vo.ComparativePagePrarmVo;
import top.klw8.alita.starter.web.base.vo.IBaseCrudVo;
import top.klw8.alita.starter.web.base.vo.ListPrarmVo;
import top.klw8.alita.starter.web.base.vo.PagePrarmVo;
import top.klw8.alita.starter.web.common.CallBackMessage;
import top.klw8.alita.starter.web.common.JsonResult;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;


/**
 * @ClassName: WebapiCrudBaseController
 * @Description: web api 用的 带常用操作的BaseController
 * @author klw
 * @date 2018年10月26日 下午3:32:04
 */
public abstract class WebapiCrudBaseController<E extends BaseEntity> extends WebapiBaseController {
    
    /**
     * @Title: getService
     * @author klw
     * @Description: 该控制器对应的数据服务
     * @return
     */
    protected abstract IBaseService<E> service();
    
    /**
     * @Title: searchType
     * @author klw
     * @Description: 搜索方式
     * @return
     */
    protected abstract SearchTypeEnum searchType();
    
    /**
     * @Title: searchField
     * @author klw
     * @Description: 如果搜索方式是指定字段,搜索的字段名(全文搜索不需要该参数)
     * @return
     */
    protected abstract String searchField();
    
    /**
     * @Title: sortFiled
     * @author klw
     * @Description: 排序字段
     * @return
     */
    protected abstract String sortFiled();
    
    /**
     * @Title: comparativeFieldname
     * @author klw
     * @Description: 比较分页方式时比较的字段名
     * @return
     */
    protected abstract String comparativeFieldname();
    
    /**
     * @Title: comparativeFieldDataType
     * @author klw
     * @Description: 比较分页方式时的字段类型
     * @return
     */
    protected abstract FieldDataType comparativeFieldDataType();
    
    /**
     * @Title: geoPointFieldName
     * @author klw
     * @Description: 地理位置查询的 位置所在字段名称
     * @return
     */
    protected abstract String geoPointFieldName();
    
    /**
     * @Title: geoRangeKm
     * @author klw
     * @Description: 地理位置查询的范围(圆形半径),单位: KM(千米,公里)
     * @return
     */
    protected abstract double geoRangeKm();
    
    /**
     * @Title: addSave
     * @author klw
     * @Description: 新增保存
     * @param vo
     * @return
     */
    protected <V extends IBaseCrudVo<E>> JsonResult addSave(V vo) {
	return JsonResult.sendSuccessfulResult(CallBackMessage.saveSuccess, service().save(vo.buildEntity()));
    }
    
    /**
     * @Title: modifySave
     * @author klw
     * @Description: 修改保存
     * @param vo
     * @return
     */
    public <V extends IBaseCrudVo<E>> JsonResult modifySave(V vo) {
	E entity = vo.buildEntity();
	if(!EntityUtil.isEntityCanModify(entity)) {
	    return JsonResult.sendParamError("保存失败: 修改时不能只有ID!");
	}
	ObjectId id = entity.getId();
	E findedEntity = service().findById(id);
	if(EntityUtil.isEntityEmpty(findedEntity)) {
	    return JsonResult.sendParamError("保存失败: ID对应的数据不存在!");
	}
	return JsonResult.sendSuccessfulResult(CallBackMessage.modifySuccess, service().updateById(entity));
    }
    
    /**
     * @Title: findByEntity
     * @author klw
     * @Description: 获取列表(不分页,支持条件), geo查询不加排序条件时默认由近及远排序
     * @param vo
     * @param isQueryRef
     * @return
     */
    public <V extends ListPrarmVo<E>> JsonResult findByEntity(V vo, boolean isQueryRef) {
	E entity = vo.buildEntity();
	if(vo.getLongitude() != null && vo.getLatitude() != null) {
	    // geo 查询
	    if(!(entity instanceof IGeoSearchSupport)) {
		return JsonResult.sendParamError("此接口不支持地理位置查询!");
	    }
	    if(SearchTypeEnum.TEXT_INDEX == searchType() && StringUtils.isNotBlank(vo.getKeywords())) {
		return JsonResult.sendParamError("地理位置查询和全文搜索不能一起查询!");
	    }
	    return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess,
		    service().findByEntityWithGeo(entity, vo.getSortDirection(), sortFiled(),
			    isQueryRef, geoPointFieldName(), vo.getLongitude(), vo.getLatitude(),
			    geoRangeKm()));
	}
	if(SearchTypeEnum.TEXT_INDEX == searchType() && StringUtils.isNotBlank(vo.getKeywords())) {
	    if(!(entity instanceof ITextIndexedSupport) && !(entity instanceof ITextIndexedCustomSupport)) {
		return JsonResult.sendParamError("此接口不支持全文搜索!");
	    }
	    return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess,
		    service().findByEntityWithTextMatching(entity, vo.getSortDirection(),
			    sortFiled(), isQueryRef, vo.getKeywords()));
	}
	return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess,
		service().findByEntity(entity, vo.getSortDirection(), sortFiled(), isQueryRef));
    }
    
    /**
     * @Title: queryForPageBySkip
     * @author klw
     * @Description: 分页查询skip方式, geo查询不加排序条件时默认由近及远排序
     * @param vo
     * @param isQueryRef
     * @return
     */
    public <V extends PagePrarmVo<E>> JsonResult queryForPageBySkip(V vo, boolean isQueryRef) {
	
	EntityByPage<E> entityByPage = EntityByPage.initParam(vo.buildEntity(), vo.getPage(), vo.getLimit(), vo.getKeywords(), searchField());
	
	PagePrarmBean pagePrarmBean = PagePrarmBean.create(vo.getKeywords(), searchField(), vo.getLimit(), vo.getPage(), vo.getSortDirection(), sortFiled());
	
	E entity = vo.buildEntity();
	if(vo.getLongitude() != null && vo.getLatitude() != null) {
	    // geo 查询
	    if(!(entity instanceof IGeoSearchSupport)) {
		return JsonResult.sendParamError("此接口不支持地理位置查询!");
	    }
	    if(SearchTypeEnum.TEXT_INDEX == searchType() && StringUtils.isNotBlank(vo.getKeywords())) {
		return JsonResult.sendParamError("地理位置查询和全文搜索不能一起查询!");
	    }
	    return returnPage(service().queryForPageWithGeo(entity, pagePrarmBean, isQueryRef, ForPageMode.SKIP().build(), geoPointFieldName(), vo.getLongitude(), vo.getLatitude(), geoRangeKm()), entityByPage);
	}
	if(SearchTypeEnum.TEXT_INDEX == searchType() && StringUtils.isNotBlank(vo.getKeywords())) {
	    if(!(entity instanceof ITextIndexedSupport) && !(entity instanceof ITextIndexedCustomSupport)) {
		return JsonResult.sendParamError("此接口不支持全文搜索!");
	    }
	    return returnPage(service().queryForPageWithTextMatching(entity, pagePrarmBean, isQueryRef, ForPageMode.SKIP().build(), vo.getKeywords()), entityByPage);
	}
	return returnPage(service().queryForPage(vo.buildEntity(), pagePrarmBean, isQueryRef, ForPageMode.SKIP().build()), entityByPage);
    }
    
    /**
     * @Title: queryForPageByComparative
     * @author klw
     * @Description: 分页查询比较方式, geo查询不加排序条件时默认由近及远排序
     * @param vo
     * @param isQueryRef
     * @return
     */
    public <V extends ComparativePagePrarmVo<E>> JsonResult queryForPageByComparative(V vo, boolean isQueryRef) {
	
	EntityByPage<E> entityByPage = EntityByPage.initParam(vo.buildEntity(), vo.getPage(), vo.getLimit(), vo.getKeywords(), searchField());
	ForPageMode forPageMode = ForPageMode.COMPARATIVE_UNIQUE_FIELD()
		.setDataPage(vo.getDataPage())
		.setDirection(vo.getComparisonMode())
		.setFieldName(comparativeFieldname())
		.setFieldValue(vo.getFieldValue())
		.setFieldDataType(comparativeFieldDataType());
	
	PagePrarmBean pagePrarmBean = PagePrarmBean.create(vo.getKeywords(), searchField(), vo.getLimit(), vo.getPage(), vo.getSortDirection(), sortFiled());
	
	E entity = vo.buildEntity();
	if(vo.getLongitude() != null && vo.getLatitude() != null) {
	    // geo 查询
	    if(!(entity instanceof IGeoSearchSupport)) {
		return JsonResult.sendParamError("此接口不支持地理位置查询!");
	    }
	    if(SearchTypeEnum.TEXT_INDEX == searchType() && StringUtils.isNotBlank(vo.getKeywords())) {
		return JsonResult.sendParamError("地理位置查询和全文搜索不能一起查询!");
	    }
	    return returnPage(service().queryForPageWithGeo(entity, pagePrarmBean, isQueryRef, forPageMode.build(), geoPointFieldName(), vo.getLongitude(), vo.getLatitude(), geoRangeKm()), entityByPage);
	}
	if(SearchTypeEnum.TEXT_INDEX == searchType() && StringUtils.isNotBlank(vo.getKeywords())) {
	    if(!(entity instanceof ITextIndexedSupport) && !(entity instanceof ITextIndexedCustomSupport)) {
		return JsonResult.sendParamError("此接口不支持全文搜索!");
	    }
	    return returnPage(service().queryForPageWithTextMatching(entity, pagePrarmBean, isQueryRef, forPageMode.build(), vo.getKeywords()), entityByPage);
	}
	
	return returnPage(service().queryForPage(vo.buildEntity(), pagePrarmBean, isQueryRef, forPageMode.build()), entityByPage);
	
    }
    
    private JsonResult returnPage(Page<E> page, EntityByPage<E> entityByPage) {
	if(page != null && CollectionUtils.isNotEmpty(page.getContent())) {
	    return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, entityByPage.createPageData(page.getTotalElements() , page.getContent()));
	} else {
	    return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, entityByPage.createPageData());
	}
    }
    
//    @ApiOperation(value = "批量新增", notes = "批量新增", httpMethod = "POST", produces = "application/json")
//    @PostMapping("/batchSave")
//    @AuthorityRegister(authorityName = "批量新增", authorityType = AuthorityType.URL, authorityShowIndex = 0)
//    public JsonResult batchSave(@ApiParam(required = true) @RequestBody List<T> list) {
//	return JsonResult.sendSuccessfulResult(CallBackMessage.saveSuccess, getService().batchSave(list));
//    }
//    
//    @ApiOperation(value = "批量更新", notes = "批量更新", httpMethod = "POST", produces = "application/json")
//    @PostMapping("/batchUpdate")
//    @AuthorityRegister(authorityName = "批量更新", authorityType = AuthorityType.URL, authorityShowIndex = 0)
//    public JsonResult batchUpdate(@ApiParam(required = true) @RequestBody List<T> list) {
//	return JsonResult.sendSuccessfulResult(CallBackMessage.modifySuccess, getService().batchUpdate(list));
//    }
    
}
