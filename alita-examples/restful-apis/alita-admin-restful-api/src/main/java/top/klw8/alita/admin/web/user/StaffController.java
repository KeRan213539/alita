package top.klw8.alita.admin.web.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Mono;
import top.klw8.alita.admin.web.user.vo.AddSelfSupportVo;
import top.klw8.alita.admin.web.user.vo.AddStaffInfoVo;
import top.klw8.alita.admin.web.user.vo.AlitaUserAccountVo;
import top.klw8.alita.admin.web.user.vo.ModifySelfSupportVo;
import top.klw8.alita.admin.web.user.vo.ModifyStaffInfoVo;
import top.klw8.alita.admin.web.user.vo.SelfSupportComPageVo;
import top.klw8.alita.admin.web.user.vo.SelfSupportListVo;
import top.klw8.alita.admin.web.user.vo.SelfSupportPageVo;
import top.klw8.alita.admin.web.user.vo.StaffComPageVo;
import top.klw8.alita.admin.web.user.vo.StaffListVo;
import top.klw8.alita.admin.web.user.vo.StaffPageVo;
import top.klw8.alita.base.mongodb.MongoDBConstant;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.product.ServiceType;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.entitys.user.StaffInfo;
import top.klw8.alita.entitys.user.StaffOrg;
import top.klw8.alita.entitys.user.StaffRegion;
import top.klw8.alita.service.api.authority.ISystemRoleService;
import top.klw8.alita.service.api.product.IServiceTypeService;
import top.klw8.alita.service.api.user.IAlitaUserService;
import top.klw8.alita.service.api.user.IStaffInfoService;
import top.klw8.alita.service.api.user.IStaffOrgService;
import top.klw8.alita.service.api.user.IStaffRegionService;
import top.klw8.alita.service.base.api.IBaseService;
import top.klw8.alita.service.base.dao.prarm.ForPageMode.FieldDataType;
import top.klw8.alita.service.utils.EntityUtil;
import top.klw8.alita.starter.annotations.AuthorityCatlogRegister;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.web.base.WebapiCrudBaseController;
import top.klw8.alita.starter.web.base.enums.SearchTypeEnum;
import top.klw8.alita.starter.web.common.CallBackMessage;
import top.klw8.alita.starter.web.common.JsonResult;
import top.klw8.alita.validator.UseValidator;

/**
 * @ClassName: StaffController
 * @Description: 员工相关
 * @author klw
 * @date 2019年1月25日 下午3:57:06
 */
@Api(tags = {"alita-restful-API--后台接口-员工相关"})
@RestController
@RequestMapping("/admin/staff")
@AuthorityCatlogRegister(name = "行政", showIndex = 3, remark = "行政")
public class StaffController extends WebapiCrudBaseController<StaffInfo> {
    
    @Reference(async=true)
    private IAlitaUserService accountService;
    
    @Reference(async=true)
    private IStaffInfoService staffService;
    
    @Reference(async=true)
    private IStaffOrgService orgService;
    
    @Reference(async=true)
    private IStaffRegionService regionService;
    
    @Reference(async=true)
    private ISystemRoleService roleService;
    
    @Reference(async=true)
    private IServiceTypeService serviceTypeService;
    
    @Override
    protected IBaseService<StaffInfo> service() {
	return staffService;
    }

    @Override
    protected SearchTypeEnum searchType() {
	return SearchTypeEnum.TEXT_INDEX;
    }

    @Override
    protected String searchField() {
	return null;
    }

    @Override
    protected String sortFiled() {
	return MongoDBConstant.ID_KEY;
    }

    @Override
    protected String comparativeFieldname() {
	return MongoDBConstant.ID_KEY;
    }

    @Override
    protected FieldDataType comparativeFieldDataType() {
	return FieldDataType.LONG;
    }

    @Override
    protected String geoPointFieldName() {
	return "addressLocation";
    }

    @Override
    protected double geoRangeKm() {
	return 7D;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @ApiOperation(value = "新增行政员工的保存", notes = "新增行政员工的保存", httpMethod = "POST", produces = "application/json")
    @PostMapping("/saveStaff")
    @UseValidator
    @AuthorityRegister(catlogName = "行政", catlogShowIndex = 3,
	    authorityName = "新增行政员工的保存", authorityType = AuthorityTypeEnum.URL,
	    authorityShowIndex = 0)
    public Mono<JsonResult> saveStaff(AlitaUserAccountVo accountVo, AddStaffInfoVo uservo) throws Exception{
	Long staffOrgId = uservo.getStaffOrgId();
	orgService.findById(staffOrgId);
	Future<StaffOrg> orgTask = RpcContext.getContext().getFuture();
	
	return Mono.create(sink ->{
	    StaffOrg org = null;
	    try {
		org = orgTask.get();
	    } catch (InterruptedException | ExecutionException e) {
		e.printStackTrace();
	    }
	    if (EntityUtil.isEntityEmpty(org)) {
		sink.success(JsonResult.sendParamError("部门不存在!"));
	    } else {
		sink.success(org);
	    }
	}).zipWith(Mono.create(sink ->{
	    Long staffRegionId = uservo.getStaffRegionId();
	    regionService.findById(staffRegionId);
	    Future<StaffRegion> regionTask = RpcContext.getContext().getFuture();
	    try {
		StaffRegion region = regionTask.get();
		if (EntityUtil.isEntityEmpty(region)) {
		    sink.success(JsonResult.sendParamError("地区不存在!"));
		} else {
		    sink.success(region);
		}
	    } catch (InterruptedException | ExecutionException e) {
		sink.error(e);
	    }
	}), (org, region) ->{
	    if(org instanceof JsonResult) {
		return (JsonResult)org;
	    }
	    if(region instanceof JsonResult) {
		return (JsonResult)region;
	    }
	    Map<String, Object> data = new HashMap<>();
	    data.put("org", org);
	    data.put("region", region);
	    return data;
	}).zipWith(Mono.create(sink ->{
	    Long roleId = uservo.getRoleId();
	    roleService.findById(roleId);
	    Future<SystemRole> roleTask = RpcContext.getContext().getFuture();
	    try {
		SystemRole role = roleTask.get();
		if (EntityUtil.isEntityEmpty(role)) {
		    sink.success(JsonResult.sendParamError("岗位不存在!"));
		} else {
		    sink.success(role);
		}
	    } catch (InterruptedException | ExecutionException e) {
		sink.error(e);
	    }
	}), (dataMono, role) ->{
	    if(dataMono instanceof JsonResult) {
		return (JsonResult)dataMono;
	    }
	    if(role instanceof JsonResult) {
		return (JsonResult)role;
	    }
	    Map<String, Object> data = (Map)dataMono;
	    StaffOrg org = (StaffOrg) data.get("org");
	    StaffRegion region = (StaffRegion) data.get("region");
	    AlitaUserAccount account = accountVo.buildEntity();
	    List<SystemRole> roleList = new ArrayList<>();
	    roleList.add((SystemRole)role);
	    account.setUserRoles(roleList);
	    StaffInfo user = uservo.buildEntity();
	    user.setStaffOrg(org);
	    user.setStaffRegion(region);
	    staffService.addSaveStaff(account, user);
	    Future<StaffInfo> task = RpcContext.getContext().getFuture();
	    try {
		StaffInfo staffSaved = task.get();
		if (EntityUtil.isEntityEmpty(staffSaved)) {
		    return "保存失败";
		}
		return staffSaved;
	    } catch (InterruptedException | ExecutionException e) {
		return e;
	    }
	}).map(staffSaved -> JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, staffSaved));
    }
    
    @ApiOperation(value = "新增自营人员的保存", notes = "新增自营人员的保存", httpMethod = "POST", produces = "application/json")
    @PostMapping("/addSelfSupport")
    @UseValidator
    @AuthorityRegister(catlogName = "行政", catlogShowIndex = 3,
	    authorityName = "新增自营人员的保存", authorityType = AuthorityTypeEnum.URL,
	    authorityShowIndex = 0)
    public Mono<JsonResult> addSelfSupport(AlitaUserAccountVo accountVo, AddSelfSupportVo uservo) throws Exception {
	Long staffRegionId = uservo.getStaffRegionId();
	regionService.findById(staffRegionId);
	Future<StaffRegion> regionTask = RpcContext.getContext().getFuture();
	return Mono.create(sink ->{
	    StaffRegion region = null;
	    try {
		region = regionTask.get();
	    } catch (InterruptedException | ExecutionException e) {
		e.printStackTrace();
	    }
	    if (EntityUtil.isEntityEmpty(region)) {
		sink.success(JsonResult.sendParamError("地区不存在!"));
	    } else {
		sink.success(region);
	    }
	}).map(region -> {
	    if(region instanceof JsonResult) {
		return (JsonResult)region;
	    }
	    List<Long> serviceTypeIds = uservo.getServiceTypeIds();
	    List<ServiceType> serviceTypeList = new ArrayList<>();
	    for (Long serviceTypeId : serviceTypeIds) {
		serviceTypeService.findById(serviceTypeId);
		Future<ServiceType> stTask = RpcContext.getContext().getFuture();
		ServiceType st = null;
		try {
		    st = stTask.get();
		} catch (InterruptedException | ExecutionException e) {
		    e.printStackTrace();
		}
		if (EntityUtil.isEntityEmpty(st)) {
		    return JsonResult.sendParamError("服务类型ID不正确!");
		}
		serviceTypeList.add(st);
	    }
	    AlitaUserAccount account = accountVo.buildEntity();
	    StaffInfo user = uservo.buildEntity();
	    user.setStaffRegion((StaffRegion)region);
	    user.setServiceTypeList(serviceTypeList);
	    staffService.addSaveStaff(account, user);
	    Future<StaffInfo> task = RpcContext.getContext().getFuture();
	    if (task == null) {
		return JsonResult.sendFailedResult("服务调用失败", null);
	    }
	    StaffInfo staffInfo = null;
	    try {
		staffInfo = task.get();
	    } catch (InterruptedException | ExecutionException e) {
		e.printStackTrace();
	    }
	    return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, staffInfo);
	});
    }
    
    @ApiOperation(value = "修改行政员工的保存", notes = "修改行政员工的保存", httpMethod = "POST", produces = "application/json")
    @PostMapping("/modifyStaff")
    @UseValidator
    @AuthorityRegister(catlogName = "行政", catlogShowIndex = 3,
	    authorityName = "修改行政员工的保存", authorityType = AuthorityTypeEnum.URL,
	    authorityShowIndex = 0)
    public Mono<JsonResult> modifyStaff(ModifyStaffInfoVo uservo) throws Exception {
	return super.modifySave(uservo);
    }
    
    @ApiOperation(value = "修改自营人员的保存", notes = "修改自营人员的保存", httpMethod = "POST", produces = "application/json")
    @PostMapping("/modifySelfSupport")
    @UseValidator
    @AuthorityRegister(catlogName = "行政", catlogShowIndex = 3,
	    authorityName = "修改自营人员的保存", authorityType = AuthorityTypeEnum.URL,
	    authorityShowIndex = 0)
    public Mono<JsonResult> modifySelfSupport(ModifySelfSupportVo uservo) throws Exception {
	return super.modifySave(uservo);
    }
    
    @ApiOperation(value = "获取行政员工列表(不分页,支持条件)", notes = "获取列表(不分页,支持条件)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/staffList")
    @AuthorityRegister(authorityName = "获取行政员工列表(不分页,支持条件)", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> findStaffByEntity(StaffListVo vo, boolean isQueryRef) throws Exception {
	return super.findByEntity(vo, isQueryRef);
    }
    
    @ApiOperation(value = "分页查询行政员工skip方式", notes = "分页查询skip方式", httpMethod = "POST", produces = "application/json")
    @PostMapping("/queryStaffForPageBySkip")
    @AuthorityRegister(authorityName = "分页查询行政员工skip方式", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> queryStaffForPageBySkip(StaffPageVo vo, boolean isQueryRef) throws Exception {
	return super.queryForPageBySkip(vo, isQueryRef);
    }
    
    @ApiOperation(value = "分页查询行政员工比较方式", notes = "分页查询比较方式", httpMethod = "POST", produces = "application/json")
    @PostMapping("/queryStaffForPageByComparative")
    @AuthorityRegister(authorityName = "分页查询行政员工比较方式", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> queryStaffForPageByComparative(StaffComPageVo vo, boolean isQueryRef) throws Exception {
	return super.queryForPageByComparative(vo, isQueryRef);
    }
    
    @ApiOperation(value = "获取自营人员列表(不分页,支持条件)", notes = "获取列表(不分页,支持条件)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/selfSupportList")
    @AuthorityRegister(authorityName = "获取自营人员列表(不分页,支持条件)", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> findSelfSupportByEntity(SelfSupportListVo vo, boolean isQueryRef) throws Exception {
	return super.findByEntity(vo, isQueryRef);
    }
    
    @ApiOperation(value = "分页查询自营人员skip方式", notes = "分页查询skip方式", httpMethod = "POST", produces = "application/json")
    @PostMapping("/querySelfSupportForPageBySkip")
    @AuthorityRegister(authorityName = "分页查询自营人员skip方式", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> querySelfSupportForPageBySkip(SelfSupportPageVo vo, boolean isQueryRef) throws Exception {
	return super.queryForPageBySkip(vo, isQueryRef);
    }
    
    @ApiOperation(value = "分页查询自营人员比较方式", notes = "分页查询比较方式", httpMethod = "POST", produces = "application/json")
    @PostMapping("/querySelfSupportForPageByComparative")
    @AuthorityRegister(authorityName = "分页查询自营人员比较方式", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> querySelfSupportForPageByComparative(SelfSupportComPageVo vo, boolean isQueryRef) throws Exception {
	return super.queryForPageByComparative(vo, isQueryRef);
    }

}
