package top.klw8.alita.starter.web.base;

import top.klw8.alita.entitys.base.BaseEntity;


/**
 * @author klw
 * @ClassName: WebapiCrudBaseController
 * @Description: web api 用的 带常用操作的BaseController
 * @date 2018年10月26日 下午3:32:04
 */
public abstract class WebapiCrudBaseController<E extends BaseEntity> extends WebapiBaseController {

//    /**
//     * @return
//     * @Title: getService
//     * @author klw
//     * @Description: 该控制器对应的数据服务
//     */
//    protected abstract IMongoBaseService<E> service();

//    /**
//     * @return
//     * @Title: searchType
//     * @author klw
//     * @Description: 搜索方式
//     */
//    protected abstract SearchTypeEnum searchType();
//
//    /**
//     * @return
//     * @Title: searchField
//     * @author klw
//     * @Description: 如果搜索方式是指定字段, 搜索的字段名(全文搜索不需要该参数)
//     */
//    protected abstract String searchField();
//
//    /**
//     * @return
//     * @Title: sortFiled
//     * @author klw
//     * @Description: 排序字段
//     */
//    protected abstract String sortFiled();
//
//    /**
//     * @return
//     * @Title: comparativeFieldname
//     * @author klw
//     * @Description: 比较分页方式时比较的字段名
//     */
//    protected abstract String comparativeFieldname();
//
//    /**
//     * @return
//     * @Title: comparativeFieldDataType
//     * @author klw
//     * @Description: 比较分页方式时的字段类型
//     */
//    protected abstract FieldDataType comparativeFieldDataType();
//
//    /**
//     * @return
//     * @Title: geoPointFieldName
//     * @author klw
//     * @Description: 地理位置查询的 位置所在字段名称
//     */
//    protected abstract String geoPointFieldName();
//
//    /**
//     * @return
//     * @Title: geoRangeKm
//     * @author klw
//     * @Description: 地理位置查询的范围(圆形半径), 单位: KM(千米,公里)
//     */
//    protected abstract double geoRangeKm();
//
//    /**
//     * @param vo
//     * @return
//     * @Title: addSave
//     * @author klw
//     * @Description: 新增保存
//     */
//    protected <V extends IBaseCrudVo<E>> Mono<JsonResult> addSave(V vo) throws Exception {
//        service().save(vo.buildEntity());
//        Future<E> task = RpcContext.getContext().getFuture();
//        if (task == null) {
//            return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//        }
//        return Mono.just(task.get()).map(result -> JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, result));
//    }
//
//    /**
//     * @param vo
//     * @return
//     * @Title: modifySave
//     * @author klw
//     * @Description: 修改保存
//     */
//    protected <V extends IBaseCrudVo<E>> Mono<JsonResult> modifySave(V vo) throws Exception {
//        E entity = vo.buildEntity();
//        if (!EntityUtil.isEntityCanModify(entity)) {
//            return Mono.just(JsonResult.sendParamError("保存失败: 修改时不能只有ID!"));
//        }
//        ObjectId id = entity.getId();
//        service().findById(id);
//        Future<E> findByIdTask = RpcContext.getContext().getFuture();
//        return Mono.just(findByIdTask.get()).map(findedEntity -> {
//            if (EntityUtil.isEntityEmpty(findedEntity)) {
//                return JsonResult.sendParamError("保存失败: ID对应的数据不存在!");
//            }
//            service().updateById(entity);
//            return RpcContext.getContext().getFuture();
//        }).map(obj -> {
//            if (obj == null) {
//                return JsonResult.sendFailedResult("服务调用失败", null);
//            }
//            if (obj instanceof JsonResult) {
//                return (JsonResult) obj;
//            } else {
//                return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, ((RpcContext) obj).get());
//            }
//        });
//
//    }
//
//    /**
//     * @param strId
//     * @return
//     * @throws Exception
//     * @Title: findById
//     * @author klw
//     * @Description: 根据ID查找
//     */
//    protected <V extends IBaseCrudVo<E>> Mono<JsonResult> findById(String strId) throws Exception {
//
//        if (StringUtils.isBlank(strId)) {
//            return Mono.just(JsonResult.sendParamError("ID不能为空"));
//        }
//        ObjectId id = null;
//        try {
//            id = new ObjectId(strId);
//        } catch (Exception e) {
//            return Mono.just(JsonResult.sendParamError("ID不正确"));
//        }
//
//        service().findById(id)
//		Future<E> task = RpcContext.getContext().getFuture();
//        if (task == null) {
//            return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//        }
//        return Mono.just(task.get()).map(findedEntity -> {
//            if (EntityUtil.isEntityEmpty(findedEntity)) {
//                return JsonResult.sendParamError("ID对应的数据不存在!");
//            }
//            return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, findedEntity);
//        });
//    }
//
//    /**
//     * @param vo
//     * @param isQueryRef
//     * @return
//     * @Title: findByEntity
//     * @author klw
//     * @Description: 获取列表(不分页, 支持条件), geo查询不加排序条件时默认由近及远排序
//     */
//    protected <V extends ListPrarmVo<E>> Mono<JsonResult> findByEntity(V vo, boolean isQueryRef)
//            throws Exception {
//        E entity = vo.buildEntity();
//        if (vo.getLongitude() != null && vo.getLatitude() != null) {
//            // geo 查询
//            if (!(entity instanceof IGeoSearchSupport)) {
//                return Mono.just(JsonResult.sendParamError("此接口不支持地理位置查询!"));
//            }
//            if (SearchTypeEnum.TEXT_INDEX == searchType()
//                    && StringUtils.isNotBlank(vo.getKeywords())) {
//                return Mono.just(JsonResult.sendParamError("地理位置查询和全文搜索不能一起查询!"));
//            }
//            service().findByEntityWithGeo(entity, vo.getSortDirection(), sortFiled(), isQueryRef,
//                                          geoPointFieldName(), vo.getLongitude(), vo.getLatitude(), geoRangeKm());
//            Future<List<E>> task = RpcContext.getContext().getFuture();
//            if (task == null) {
//                return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//            }
//            return Mono.just(task.get()).map(result -> JsonResult
//                    .sendSuccessfulResult(CallBackMessage.querySuccess, result));
//        }
//        if (SearchTypeEnum.TEXT_INDEX == searchType() && StringUtils.isNotBlank(vo.getKeywords())) {
//            if (!(entity instanceof ITextIndexedSupport)
//                    && !(entity instanceof ITextIndexedCustomSupport)) {
//                return Mono.just(JsonResult.sendParamError("此接口不支持全文搜索!"));
//            }
//            service().findByEntityWithTextMatching(entity, vo.getSortDirection(), sortFiled(),
//                                                   isQueryRef, vo.getKeywords());
//            Future<List<E>> task = RpcContext.getContext().getFuture();
//            if (task == null) {
//                return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//            }
//            return Mono.just(task.get()).map(result -> JsonResult
//                    .sendSuccessfulResult(CallBackMessage.querySuccess, result));
//        }
//        service().findByEntity(entity, vo.getSortDirection(), sortFiled(), isQueryRef);
//        Future<List<E>> task = RpcContext.getContext().getFuture();
//        if (task == null) {
//            return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//        }
//        return Mono.just(task.get()).map(
//                result -> JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, result));
//    }
//
//    /**
//     * @param vo
//     * @param isQueryRef
//     * @return
//     * @Title: queryForPageBySkip
//     * @author klw
//     * @Description: 分页查询skip方式, geo查询不加排序条件时默认由近及远排序
//     */
//    protected <V extends PagePrarmVo<E>> Mono<JsonResult> queryForPageBySkip(V vo, boolean isQueryRef) throws Exception {
//
//        EntityByPage<E> entityByPage = EntityByPage.initParam(vo.buildEntity(), vo.getPage(), vo.getLimit(), vo.getKeywords(), searchField());
//
//        PagePrarmBean pagePrarmBean = PagePrarmBean.create(vo.getKeywords(), searchField(), vo.getLimit(), vo.getPage(), vo.getSortDirection(), sortFiled());
//
//        E entity = vo.buildEntity();
//        if (vo.getLongitude() != null && vo.getLatitude() != null) {
//            // geo 查询
//            if (!(entity instanceof IGeoSearchSupport)) {
//                return Mono.just(JsonResult.sendParamError("此接口不支持地理位置查询!"));
//            }
//            if (SearchTypeEnum.TEXT_INDEX == searchType() && StringUtils.isNotBlank(vo.getKeywords())) {
//                return Mono.just(JsonResult.sendParamError("地理位置查询和全文搜索不能一起查询!"));
//            }
//            service().queryForPageWithGeo(entity, pagePrarmBean, isQueryRef, ForPageMode.SKIP().build(), geoPointFieldName(), vo.getLongitude(), vo.getLatitude(), geoRangeKm());
//            Future<Page<E>> task = RpcContext.getContext().getFuture();
//            if (task == null) {
//                return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//            }
//            return returnPage(Mono.just(task.get()), entityByPage);
//        }
//        if (SearchTypeEnum.TEXT_INDEX == searchType() && StringUtils.isNotBlank(vo.getKeywords())) {
//            if (!(entity instanceof ITextIndexedSupport) && !(entity instanceof ITextIndexedCustomSupport)) {
//                return Mono.just(JsonResult.sendParamError("此接口不支持全文搜索!"));
//            }
//            service().queryForPageWithTextMatching(entity, pagePrarmBean, isQueryRef, ForPageMode.SKIP().build(), vo.getKeywords());
//            Future<Page<E>> task = RpcContext.getContext().getFuture();
//            if (task == null) {
//                return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//            }
//            return returnPage(Mono.just(task.get()), entityByPage);
//        }
//        service().queryForPage(vo.buildEntity(), pagePrarmBean, isQueryRef, ForPageMode.SKIP().build());
//        Future<Page<E>> task = RpcContext.getContext().getFuture();
//        if (task == null) {
//            return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//        }
//        return returnPage(Mono.just(task.get()), entityByPage);
//    }
//
//    /**
//     * @param vo
//     * @param isQueryRef
//     * @return
//     * @Title: queryForPageByComparative
//     * @author klw
//     * @Description: 分页查询比较方式, geo查询不加排序条件时默认由近及远排序
//     */
//    protected <V extends ComparativePagePrarmVo<E>> Mono<JsonResult> queryForPageByComparative(V vo, boolean isQueryRef) throws Exception {
//
//        EntityByPage<E> entityByPage = EntityByPage.initParam(vo.buildEntity(), vo.getPage(), vo.getLimit(), vo.getKeywords(), searchField());
//        ForPageMode forPageMode = ForPageMode.COMPARATIVE_UNIQUE_FIELD()
//                .setDataPage(vo.getDataPage())
//                .setDirection(vo.getComparisonMode())
//                .setFieldName(comparativeFieldname())
//                .setFieldValue(vo.getFieldValue())
//                .setFieldDataType(comparativeFieldDataType());
//
//        PagePrarmBean pagePrarmBean = PagePrarmBean.create(vo.getKeywords(), searchField(), vo.getLimit(), vo.getPage(), vo.getSortDirection(), sortFiled());
//
//        E entity = vo.buildEntity();
//        if (vo.getLongitude() != null && vo.getLatitude() != null) {
//            // geo 查询
//            if (!(entity instanceof IGeoSearchSupport)) {
//                return Mono.just(JsonResult.sendParamError("此接口不支持地理位置查询!"));
//            }
//            if (SearchTypeEnum.TEXT_INDEX == searchType() && StringUtils.isNotBlank(vo.getKeywords())) {
//                return Mono.just(JsonResult.sendParamError("地理位置查询和全文搜索不能一起查询!"));
//            }
//            service().queryForPageWithGeo(entity, pagePrarmBean, isQueryRef, forPageMode.build(), geoPointFieldName(), vo.getLongitude(), vo.getLatitude(), geoRangeKm());
//            Future<Page<E>> task = RpcContext.getContext().getFuture();
//            if (task == null) {
//                return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//            }
//            return returnPage(Mono.just(task.get()), entityByPage);
//        }
//        if (SearchTypeEnum.TEXT_INDEX == searchType() && StringUtils.isNotBlank(vo.getKeywords())) {
//            if (!(entity instanceof ITextIndexedSupport) && !(entity instanceof ITextIndexedCustomSupport)) {
//                return Mono.just(JsonResult.sendParamError("此接口不支持全文搜索!"));
//            }
//            service().queryForPageWithTextMatching(entity, pagePrarmBean, isQueryRef, forPageMode.build(), vo.getKeywords());
//            Future<Page<E>> task = RpcContext.getContext().getFuture();
//            if (task == null) {
//                return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//            }
//            return returnPage(Mono.just(task.get()), entityByPage);
//        }
//        service().queryForPage(vo.buildEntity(), pagePrarmBean, isQueryRef, forPageMode.build());
//        Future<Page<E>> task = RpcContext.getContext().getFuture();
//        if (task == null) {
//            return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//        }
//        return returnPage(Mono.just(task.get()), entityByPage);
//
//    }
//
//    private Mono<JsonResult> returnPage(Mono<Page<E>> pageMono, EntityByPage<E> entityByPage)
//            throws Exception {
//        return pageMono.map(page -> {
//            if (page != null && CollectionUtils.isNotEmpty(page.getContent())) {
//                return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess,
//                                                       entityByPage.createPageData(page.getTotalElements(), page.getContent()));
//            } else {
//                return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess,
//                                                       entityByPage.createPageData());
//            }
//        });
//    }
//
////    @ApiOperation(value = "批量新增", notes = "批量新增", httpMethod = "POST", produces = "application/json")
////    @PostMapping("/batchSave")
////    @AuthorityRegister(authorityName = "批量新增", authorityType = AuthorityType.URL, authorityShowIndex = 0)
////    public JsonResult batchSave(@ApiParam(required = true) @RequestBody List<T> list) {
////	return JsonResult.sendSuccessfulResult(CallBackMessage.saveSuccess, getService().batchSave(list));
////    }
////
////    @ApiOperation(value = "批量更新", notes = "批量更新", httpMethod = "POST", produces = "application/json")
////    @PostMapping("/batchUpdate")
////    @AuthorityRegister(authorityName = "批量更新", authorityType = AuthorityType.URL, authorityShowIndex = 0)
////    public JsonResult batchUpdate(@ApiParam(required = true) @RequestBody List<T> list) {
////	return JsonResult.sendSuccessfulResult(CallBackMessage.modifySuccess, getService().batchUpdate(list));
////    }

}
