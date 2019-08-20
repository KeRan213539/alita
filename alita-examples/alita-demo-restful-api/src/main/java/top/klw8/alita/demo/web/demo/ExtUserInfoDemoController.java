package top.klw8.alita.demo.web.demo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.klw8.alita.entitys.demo.mongo.ExtUserInfoDemo;
import top.klw8.alita.starter.annotations.AuthorityCatlogRegister;
import top.klw8.alita.starter.web.base.WebapiCrudBaseController;

/**
 * @author klw
 * @ClassName: NormalUserController
 * @Description: 客户相关
 * @date 2019年1月25日 下午3:56:27
 */
@Api(tags = {"alita-restful-API--后台接口-用户管理"})
@RestController
@RequestMapping("/admin/users")
@AuthorityCatlogRegister(name = "用户管理", showIndex = 1, remark = "用户管理")
@Slf4j
public class ExtUserInfoDemoController extends WebapiCrudBaseController<ExtUserInfoDemo> {

//    @Reference(async = true)
//    private IAlitaUserService accountService;
//
//    @Reference(async = true)
//    private IExtUserInfoDemoService extUserInfoDemoService;
//
//
//    @Override
//    protected IMongoBaseService<ExtUserInfoDemo> service() {
//        return extUserInfoDemoService;
//    }
//
//    @Override
//    protected SearchTypeEnum searchType() {
//        return SearchTypeEnum.TEXT_INDEX;
//    }
//
//    @Override
//    protected String searchField() {
//        return null;
//    }
//
//    @Override
//    protected String sortFiled() {
//        return MongoDBConstant.ID_KEY;
//    }
//
//    @Override
//    protected String comparativeFieldname() {
//        return MongoDBConstant.ID_KEY;
//    }
//
//    @Override
//    protected FieldDataType comparativeFieldDataType() {
//        return FieldDataType.LONG;
//    }
//
//    @Override
//    protected String geoPointFieldName() {
//        return null;
//    }
//
//    @Override
//    protected double geoRangeKm() {
//        return 0;
//    }
//
//
//    @ApiOperation(value = "新增用户的保存", notes = "新增用户的保存", httpMethod = "POST", produces = "application/json")
//    @PostMapping("/user")
//    @UseValidator
//    @AuthorityRegister(catlogName = "用户管理", catlogShowIndex = 1,
//            authorityName = "新增客户的保存", authorityType = AuthorityTypeEnum.URL,
//            authorityShowIndex = 0)
//    public Mono<JsonResult> addNormalUser(AlitaUserAccountVo accountVo, AddExtUserInfoDemoVo uservo) throws Exception {
//        AlitaUserAccount account = accountVo.buildEntity();
//        ExtUserInfoDemo  user    = uservo.buildEntity();
//        extUserInfoDemoService.addUser(account, user);
//        Future<ExtUserInfoDemo> task = RpcContext.getContext().getFuture();
//        if (task == null) {
//            return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//        }
//        return Mono.just(task.get()).map(e -> {
//            return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, e);
//        });
//    }
//
//    @ApiOperation(value = "修改用户的保存", notes = "修改用户的保存", httpMethod = "PUT", produces = "application/json")
//    @PutMapping("/user")
//    @UseValidator
//    @AuthorityRegister(catlogName = "用户管理", catlogShowIndex = 1,
//            authorityName = "修改客户的保存", authorityType = AuthorityTypeEnum.URL,
//            authorityShowIndex = 0)
//    public Mono<JsonResult> modifyNormalUser(ModifyExtUserInfoDemoVo uservo) throws Exception {
//        return super.modifySave(uservo);
//    }
//
//    @Override
//    @ApiOperation(value = "根据ID查询用户信息", notes = "根据ID查询用户信息", httpMethod = "GET", produces = "application/json")
//    @GetMapping("/user")
//    @UseValidator
//    @AuthorityRegister(catlogName = "用户管理", catlogShowIndex = 1,
//            authorityName = "根据ID查询用户信息", authorityType = AuthorityTypeEnum.URL,
//            authorityShowIndex = 0)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "extUserInfoId", value = "用户扩展信息demo ID", dataType = "String",
//                    paramType = "query")
//    })
//    public Mono<JsonResult> findById(String extUserInfoId) throws Exception {
//        return super.findById(extUserInfoId);
//    }
//
//    @ApiOperation(value = "获取用户列表(不分页,支持条件)", notes = "获取列表(不分页,支持条件)", httpMethod = "GET", produces = "application/json")
//    @GetMapping("/list")
//    @AuthorityRegister(catlogName = "用户管理", catlogShowIndex = 1, authorityName = "获取用户列表(不分页,支持条件)", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
//    @UseValidator
//    public Mono<JsonResult> findByEntity(NormalUserInfoListVo vo, boolean isQueryRef) throws Exception {
//        return super.findByEntity(vo, isQueryRef);
//    }
//
//    @ApiOperation(value = "分页查询用户skip方式", notes = "分页查询skip方式", httpMethod = "GET", produces = "application/json")
//    @GetMapping("/pageBySkip")
//    @AuthorityRegister(catlogName = "用户管理", catlogShowIndex = 1, authorityName = "分页查询用户skip方式", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
//    @UseValidator
//    public Mono<JsonResult> queryForPageBySkip(NormalUserInfoPageVo vo, boolean isQueryRef) throws Exception {
//        return super.queryForPageBySkip(vo, isQueryRef);
//    }
//
//    @ApiOperation(value = "分页查询用户比较方式", notes = "分页查询比较方式", httpMethod = "GET", produces = "application/json")
//    @GetMapping("/pageByComparative")
//    @AuthorityRegister(catlogName = "用户管理", catlogShowIndex = 1, authorityName = "分页查询用户比较方式", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
//    @UseValidator
//    public Mono<JsonResult> queryForPageByComparative(NormalUserInfoComPageVo vo, boolean isQueryRef) throws Exception {
//        return super.queryForPageByComparative(vo, isQueryRef);
//    }

}
