package top.klw8.alita.admin.web.user;

import java.util.concurrent.Future;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import top.klw8.alita.admin.web.user.vo.AddExtUserInfoDemoVo;
import top.klw8.alita.admin.web.user.vo.AlitaUserAccountVo;
import top.klw8.alita.admin.web.user.vo.ModifyExtUserInfoDemoVo;
import top.klw8.alita.admin.web.user.vo.NormalUserInfoComPageVo;
import top.klw8.alita.admin.web.user.vo.NormalUserInfoListVo;
import top.klw8.alita.admin.web.user.vo.NormalUserInfoPageVo;
import top.klw8.alita.base.mongodb.MongoDBConstant;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.entitys.user.ExtUserInfoDemo;
import top.klw8.alita.service.api.user.IAlitaUserService;
import top.klw8.alita.service.api.user.IExtUserInfoDemoService;
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
@Api(tags = {"alita-restful-API--后台接口-用户管理"})
@RestController
@RequestMapping("/admin/users")
@AuthorityCatlogRegister(name = "用户管理", showIndex = 1, remark = "用户管理")
@Slf4j
public class ExtUserInfoDemoController extends WebapiCrudBaseController<ExtUserInfoDemo> {
    
    @Reference(async=true)
    private IAlitaUserService accountService;
    
    @Reference(async=true)
    private IExtUserInfoDemoService extUserInfoDemoService;
    
    
    @Override
    protected IBaseService<ExtUserInfoDemo> service() {
	return extUserInfoDemoService;
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
    

    @ApiOperation(value = "新增用户的保存", notes = "新增用户的保存", httpMethod = "POST", produces = "application/json")
    @PostMapping("/user")
    @UseValidator
    @AuthorityRegister(catlogName = "用户管理", catlogShowIndex = 1,
	    authorityName = "新增客户的保存", authorityType = AuthorityTypeEnum.URL,
	    authorityShowIndex = 0)
    public Mono<JsonResult> addNormalUser(AlitaUserAccountVo accountVo, AddExtUserInfoDemoVo uservo) throws Exception {
	AlitaUserAccount account = accountVo.buildEntity(); 
	ExtUserInfoDemo user = uservo.buildEntity();
	extUserInfoDemoService.addUser(account, user);
	Future<ExtUserInfoDemo> task = RpcContext.getContext().getFuture();
	if(task == null) {
	    return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
	}
	return Mono.just(task.get()).map(e ->{
	    return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, e);
	});
    }
    
    @ApiOperation(value = "修改用户的保存", notes = "修改用户的保存", httpMethod = "PUT", produces = "application/json")
    @PutMapping("/user")
    @UseValidator
    @AuthorityRegister(catlogName = "用户管理", catlogShowIndex = 1,
	    authorityName = "修改客户的保存", authorityType = AuthorityTypeEnum.URL,
	    authorityShowIndex = 0)
    public Mono<JsonResult> modifyNormalUser(ModifyExtUserInfoDemoVo uservo) throws Exception {
	return super.modifySave(uservo);
    }
    
    @ApiOperation(value = "根据ID查询用户信息", notes = "根据ID查询用户信息", httpMethod = "GET", produces = "application/json")
    @GetMapping("/user")
    @UseValidator
    @AuthorityRegister(catlogName = "用户管理", catlogShowIndex = 1,
	    authorityName = "根据ID查询用户信息", authorityType = AuthorityTypeEnum.URL,
	    authorityShowIndex = 0)
    @ApiImplicitParams({
	@ApiImplicitParam(name="extUserInfoId",value="用户扩展信息demo ID",dataType="String", 
		paramType = "query")
    })
    public Mono<JsonResult> findById(String extUserInfoId) throws Exception {
	return super.findById(extUserInfoId);
    }
    
    @ApiOperation(value = "获取用户列表(不分页,支持条件)", notes = "获取列表(不分页,支持条件)", httpMethod = "GET", produces = "application/json")
    @GetMapping("/list")
    @AuthorityRegister(catlogName = "用户管理", catlogShowIndex = 1, authorityName = "获取用户列表(不分页,支持条件)", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> findByEntity(NormalUserInfoListVo vo, boolean isQueryRef) throws Exception {
	return super.findByEntity(vo, isQueryRef);
    }
    
    @ApiOperation(value = "分页查询用户skip方式", notes = "分页查询skip方式", httpMethod = "GET", produces = "application/json")
    @GetMapping("/pageBySkip")
    @AuthorityRegister(catlogName = "用户管理", catlogShowIndex = 1,authorityName = "分页查询用户skip方式", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> queryForPageBySkip(NormalUserInfoPageVo vo, boolean isQueryRef) throws Exception {
	return super.queryForPageBySkip(vo, isQueryRef);
    }
    
    @ApiOperation(value = "分页查询用户比较方式", notes = "分页查询比较方式", httpMethod = "GET", produces = "application/json")
    @GetMapping("/pageByComparative")
    @AuthorityRegister(catlogName = "用户管理", catlogShowIndex = 1,authorityName = "分页查询用户比较方式", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> queryForPageByComparative(NormalUserInfoComPageVo vo, boolean isQueryRef) throws Exception {
	return super.queryForPageByComparative(vo, isQueryRef);
    }

}
