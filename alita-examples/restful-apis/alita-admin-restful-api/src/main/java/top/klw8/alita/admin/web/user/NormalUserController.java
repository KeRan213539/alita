package top.klw8.alita.admin.web.user;

import java.util.concurrent.Future;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import top.klw8.alita.admin.web.user.vo.AddNormalUserInfoVo;
import top.klw8.alita.admin.web.user.vo.AlitaUserAccountVo;
import top.klw8.alita.admin.web.user.vo.ModifyNormalUserInfoVo;
import top.klw8.alita.admin.web.user.vo.NormalUserInfoComPageVo;
import top.klw8.alita.admin.web.user.vo.NormalUserInfoListVo;
import top.klw8.alita.admin.web.user.vo.NormalUserInfoPageVo;
import top.klw8.alita.base.mongodb.MongoDBConstant;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.entitys.user.NormalUserInfo;
import top.klw8.alita.service.api.user.IAlitaUserService;
import top.klw8.alita.service.api.user.INormalUserInfoService;
import top.klw8.alita.service.base.api.IBaseService;
import top.klw8.alita.service.base.dao.prarm.ForPageMode.FieldDataType;
import top.klw8.alita.starter.annotations.AuthorityCatlogRegister;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.web.base.WebapiCrudBaseController;
import top.klw8.alita.starter.web.base.enums.SearchTypeEnum;
import top.klw8.alita.starter.web.common.CallBackMessage;
import top.klw8.alita.starter.web.common.JsonResult;
import top.klw8.alita.validator.UseValidator;

/**
 * @ClassName: NormalUserController
 * @Description: 客户相关
 * @author klw
 * @date 2019年1月25日 下午3:56:27
 */
@Api(tags = {"alita-restful-API--后台接口-客户相关"})
@RestController
@RequestMapping("/admin/normalUser")
@AuthorityCatlogRegister(name = "客服", showIndex = 1, remark = "客服")
@Slf4j
public class NormalUserController extends WebapiCrudBaseController<NormalUserInfo> {
    
    @Reference(async=true)
    private IAlitaUserService accountService;
    
    @Reference(async=true)
    private INormalUserInfoService normalUserService;
    
    
    @Override
    protected IBaseService<NormalUserInfo> service() {
	return normalUserService;
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
	return null;
    }

    @Override
    protected double geoRangeKm() {
	return 0;
    }
    

    @ApiOperation(value = "新增客户的保存", notes = "新增客户的保存", httpMethod = "POST", produces = "application/json")
    @PostMapping("/addNormalUser")
    @UseValidator
    @AuthorityRegister(catlogName = "客服", catlogShowIndex = 1,
	    authorityName = "新增客户的保存", authorityType = AuthorityTypeEnum.URL,
	    authorityShowIndex = 0)
    public Mono<JsonResult> addNormalUser(AlitaUserAccountVo accountVo, AddNormalUserInfoVo uservo) throws Exception {
	AlitaUserAccount account = accountVo.buildEntity(); 
	NormalUserInfo user = uservo.buildEntity();
	normalUserService.addSaveNormalUser(account, user);
	Future<NormalUserInfo> task = RpcContext.getContext().getFuture();
	if(task == null) {
	    return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
	}
	return Mono.just(task.get()).map(e ->{
	    return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, e);
	});
    }
    
    @ApiOperation(value = "修改客户的保存", notes = "修改客户的保存", httpMethod = "POST", produces = "application/json")
    @PostMapping("/modifyNormalUser")
    @UseValidator
    @AuthorityRegister(catlogName = "客服", catlogShowIndex = 1,
	    authorityName = "修改客户的保存", authorityType = AuthorityTypeEnum.URL,
	    authorityShowIndex = 0)
    public Mono<JsonResult> modifyNormalUser(ModifyNormalUserInfoVo uservo) throws Exception {
	return super.modifySave(uservo);
    }
    
    @ApiOperation(value = "获取客户列表(不分页,支持条件)", notes = "获取列表(不分页,支持条件)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/list")
    @AuthorityRegister(authorityName = "获取客户列表(不分页,支持条件)", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> findByEntity(NormalUserInfoListVo vo, boolean isQueryRef) throws Exception {
	return super.findByEntity(vo, isQueryRef);
    }
    
    @ApiOperation(value = "分页查询客户skip方式", notes = "分页查询skip方式", httpMethod = "POST", produces = "application/json")
    @PostMapping("/queryForPageBySkip")
    @AuthorityRegister(authorityName = "分页查询客户skip方式", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> queryForPageBySkip(NormalUserInfoPageVo vo, boolean isQueryRef) throws Exception {
	return super.queryForPageBySkip(vo, isQueryRef);
    }
    
    @ApiOperation(value = "分页查询客户比较方式", notes = "分页查询比较方式", httpMethod = "POST", produces = "application/json")
    @PostMapping("/queryForPageByComparative")
    @AuthorityRegister(authorityName = "分页查询客户比较方式", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> queryForPageByComparative(NormalUserInfoComPageVo vo, boolean isQueryRef) throws Exception {
	return super.queryForPageByComparative(vo, isQueryRef);
    }

}
