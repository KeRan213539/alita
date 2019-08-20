package top.klw8.alita.demo.web.demo;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.klw8.alita.entitys.demo.mongo.MongoDBTest;
import top.klw8.alita.service.api.demo.ISpringCloudProviderDemoService;
import top.klw8.alita.starter.annotations.AuthorityCatlogRegister;
import top.klw8.alita.starter.web.base.WebapiCrudBaseController;

/**
 * @author klw
 * @ClassName: MongoDemoController
 * @Description: mongodb demo
 * @date 2018年9月29日 下午4:33:23
 */
@Api(tags = {"alita-restful-API--demoAPI"})
@RestController
@RequestMapping("/demo")
@AuthorityCatlogRegister(name = "mongodb demo", showIndex = 98, remark = "demo")
@Slf4j
public class MongoDemoController extends WebapiCrudBaseController<MongoDBTest> {

//    @Reference(async = true)
//    private ISpringCloudProviderDemoService service;
//
//    @Override
//    protected IMongoBaseService<MongoDBTest> service() {
//        return service;
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
//        return null;
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
//        return "location";
//    }
//
//    @Override
//    protected double geoRangeKm() {
//        return 7D;
//    }
//
////    @Autowired
////    private RocketMQTemplate rocketMQTemplate;
//
//    @ApiOperation(value = "新增保存", notes = "新增保存", httpMethod = "POST", produces = "application/json")
//    @PostMapping("/save")
//    @AuthorityRegister(authorityName = "新增保存", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
//    @UseValidator
//    public Mono<JsonResult> addSave(MongoDBTestVo vo) throws Exception {
//        return super.addSave(vo);
//    }
//
//    @ApiOperation(value = "修改保存", notes = "修改保存", httpMethod = "POST", produces = "application/json")
//    @PostMapping("/modify")
//    @AuthorityRegister(authorityName = "修改保存", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
//    @UseValidator
//    public Mono<JsonResult> modifySave(MongoDBTestVo vo) throws Exception {
//        return super.modifySave(vo);
//    }
//
//    @ApiOperation(value = "获取列表(不分页,支持条件), geo查询不加排序条件时默认由近及远排序", notes = "获取列表(不分页,支持条件)", httpMethod = "POST", produces = "application/json")
//    @PostMapping("/list")
//    @AuthorityRegister(authorityName = "获取列表(不分页,支持条件)", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
//    @UseValidator
//    public Mono<JsonResult> findByEntity(MongoDBTestListVo vo, boolean isQueryRef) throws Exception {
//        return super.findByEntity(vo, isQueryRef);
//    }
//
//    @ApiOperation(value = "分页查询skip方式, geo查询不加排序条件时默认由近及远排序", notes = "分页查询skip方式", httpMethod = "POST", produces = "application/json")
//    @PostMapping("/queryForPageBySkip")
//    @AuthorityRegister(authorityName = "分页查询skip方式", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
//    @UseValidator
//    public Mono<JsonResult> queryForPageBySkip(MongoDBTestPageVo vo, boolean isQueryRef) throws Exception {
//        return super.queryForPageBySkip(vo, isQueryRef);
//    }
//
//    @ApiOperation(value = "分页查询比较方式, geo查询不加排序条件时默认由近及远排序", notes = "分页查询比较方式", httpMethod = "POST", produces = "application/json")
//    @PostMapping("/queryForPageByComparative")
//    @AuthorityRegister(authorityName = "分页查询比较方式", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
//    @UseValidator
//    public Mono<JsonResult> queryForPageByComparative(MongoDBTestComparativePageVo vo, boolean isQueryRef) throws Exception {
//        return super.queryForPageByComparative(vo, isQueryRef);
//    }
//
//
//    @ApiOperation(value = "testGeoFind", notes = "testGeoFind", httpMethod = "POST", produces = "application/json")
//    @PostMapping("/testGeoFind")
//    @AuthorityRegister(authorityName = "测试geo查询", authorityType = AuthorityTypeEnum.URL,
//            authorityShowIndex = 0)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "name", value = "名称", dataType = "String",
//                    paramType = "query", example = "汇都二栋住宅", defaultValue = "汇都二栋住宅"),
//            @ApiImplicitParam(name = "longitude", value = "经度", dataType = "double",
//                    paramType = "query", example = "25.02523", defaultValue = "25.02523", required = true),
//            @ApiImplicitParam(name = "latitude", value = "纬度", dataType = "double",
//                    paramType = "query", example = "102.70767", defaultValue = "102.70767", required = true),
//            @ApiImplicitParam(name = "rangeKm", value = "圆形范围半径的公里数", dataType = "double",
//                    paramType = "query", example = "1", defaultValue = "1", required = true)
//    })
//    public Mono<JsonResult> testGeoFind(String name, Double longitude, Double latitude, Double rangeKm) throws Exception {
//        MongoDBTest baseBean = new MongoDBTest();
//        baseBean.setName(name);
//        service.findByEntityWithGeo(baseBean, null, null, false, "location", longitude, latitude, rangeKm);
//        Future<List<MongoDBTest>> task = RpcContext.getContext().getFuture();
//        if (task == null) {
//            return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//        }
//        return Mono.just(task.get()).map(list -> {
//            return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, list);
//        });
//
//    }
//
//    @ApiOperation(value = "testTextSearch", notes = "testTextSearch", httpMethod = "POST", produces = "application/json")
//    @PostMapping("/testTextSearch")
//    @AuthorityRegister(authorityName = "测试全文搜索", authorityType = AuthorityTypeEnum.URL,
//            authorityShowIndex = 0)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "salary", value = "工资", dataType = "BigDecimal",
//                    paramType = "query", example = "50000", defaultValue = "50000"),
//            @ApiImplicitParam(name = "keyWords", value = "关键字", dataType = "String",
//                    paramType = "query", example = "延安", defaultValue = "延安", required = true)
//    })
//    public Mono<JsonResult> testTextSearch(BigDecimal salary, String keyWords) throws Exception {
//        MongoDBTest baseBean = new MongoDBTest();
//        baseBean.setSalary(salary);
//        service.findByEntityWithTextMatching(baseBean, "ASC", "id", false, keyWords);
//        Future<List<MongoDBTest>> task = RpcContext.getContext().getFuture();
//        if (task == null) {
//            return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//        }
//        return Mono.just(task.get()).map(list -> {
//            return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, list);
//        });
//    }
//
//    @ApiOperation(value = "两个异步保存,并合并保存结果", notes = "testSaveAsyncNew", httpMethod = "POST", produces = "application/json")
//    @PostMapping("/testSaveAsyncNew")
//    @AuthorityRegister(authorityName = "testSaveAsync", authorityType = AuthorityTypeEnum.URL,
//            authorityShowIndex = 0)
//    public Mono<JsonResult> testSaveAsyncNew() throws Exception {
//        // begin 异步方式调用 dubbo
//        List<MongoDBTest> list = new ArrayList<>();
//        MongoDBTest t1 = new MongoDBTest();
//        t1.setName("延安医院");
//        t1.setSalary(new BigDecimal(50000.0));
//        t1.setLocation(new GeoPoint(102.73079, 25.042475));
//        list.add(t1);
//        service.batchSave(list);
//        Future<List<MongoDBTest>> task = RpcContext.getContext().getFuture();  // 从这里获得 Future
//
//        MongoDBTest t2 = new MongoDBTest();
//        t2.setName("长水国际机场");
//        t2.setSalary(new BigDecimal(45000.0));
//        t2.setLocation(new GeoPoint(102.927858, 25.100317));
//        service.save(t2);
//        Future<MongoDBTest> task2 = RpcContext.getContext().getFuture();  // 从这里获得 Future
//
//        if (task == null || task2 == null) {
//            return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//        }
//        // do something... 这里可以做一些不需要service结果就可以做的事
//
//        return Mono.just(task.get()).zipWith(Mono.just(task2.get()), (result1, result2) -> {
//            result1.add(result2);
//            return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, result1);
//        });
//    }
//
//    @ApiOperation(value = "rocketMQ demo", notes = "rocketMQ demo", httpMethod = "POST", produces = "application/json")
//    @PostMapping("/mqDemo")
//    @AuthorityRegister(authorityName = "rocketMQ demo", authorityType = AuthorityTypeEnum.URL,
//            authorityShowIndex = 0)
//    public Mono<JsonResult> testMq() throws Exception {
////	rocketMQTemplate.convertAndSend("string-topic", "Hello, World!");
////	rocketMQTemplate.convertAndSend("bean-topic", new DemoMsgBean("Demo" , new BigDecimal("88.00")));
//        return Mono.just(JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, "MQ消息已发送"));
//    }
//
//    @ApiOperation(value = "使用swagger的注解生成入参说明和响应说明的demo", notes = "使用swagger的注解生成入参说明和响应说明的demo", httpMethod = "POST", produces = "application/json")
//    @PostMapping("/queryAll")
//    @AuthorityRegister(authorityName = "使用swagger的注解生成入参说明和响应说明的demo", authorityType = AuthorityTypeEnum.URL,
//            authorityShowIndex = 0)
//    public Mono<JsonResult> queryAll(@ApiParam(value = "如果为空就抛异常") DemoRequest req) throws Exception {
//        String abc = null;
////	log.info("写个日志玩玩8888888888888888");
////	service.findByEntity(null, null, null, false);
//        if (req != null) {
//            abc = req.getAbc();
//        }
//        service.queryAll(abc);
//        Future<List<MongoDBTest>> task = RpcContext.getContext().getFuture();
//        if (task == null) {
//            return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//        }
//        return Mono.just(task.get()).map(list -> {
//            for (MongoDBTest t : list) {
//                // do something...
//            }
//            return JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, list);
//        });
//    }
//
//    @ApiOperation(value = "testDeleteById", notes = "testDeleteById", httpMethod = "POST", produces = "application/json")
//    @PostMapping("/testDeleteById")
//    @AuthorityRegister(authorityName = "testDeleteById", authorityType = AuthorityTypeEnum.URL,
//            authorityShowIndex = 0)
//    public Mono<JsonResult> testDeleteById(String id) throws Exception {
//        service.deleteById(new ObjectId(id));
//        Future<Integer> task = RpcContext.getContext().getFuture();
//        if (task == null) {
//            return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//        }
//        return Mono.just(task.get()).map(result -> JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, result));
//    }
//
//    @ApiOperation(value = "testDeleteByIds", notes = "testDeleteByIds", httpMethod = "POST", produces = "application/json")
//    @PostMapping("/testDeleteByIds")
//    @AuthorityRegister(authorityName = "testDeleteByIds", authorityType = AuthorityTypeEnum.URL,
//            authorityShowIndex = 0)
//    public Mono<JsonResult> testDeleteByIds(String ids) throws Exception {
//        service.deleteByIds(ids);
//        Future<Integer> task = RpcContext.getContext().getFuture();
//        if (task == null) {
//            return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//        }
//        return Mono.just(task.get()).map(result -> JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, result));
//    }
//
//    @ApiOperation(value = "testFindById", notes = "testFindById", httpMethod = "POST", produces = "application/json")
//    @PostMapping("/testFindById")
//    @AuthorityRegister(authorityName = "testFindById", authorityType = AuthorityTypeEnum.URL,
//            authorityShowIndex = 0)
//    public Mono<JsonResult> testFindById(String id, boolean isQueryRef) throws Exception {
//        service.findById(new ObjectId(id), isQueryRef);
//        Future<MongoDBTest> task = RpcContext.getContext().getFuture();
//        if (task == null) {
//            return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//        }
//        return Mono.just(task.get()).map(result -> JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, result));
//    }
//
//    @ApiOperation(value = "testFindByIds", notes = "testFindByIds", httpMethod = "POST", produces = "application/json")
//    @PostMapping("/testFindByIds")
//    @AuthorityRegister(authorityName = "testFindByIds", authorityType = AuthorityTypeEnum.URL,
//            authorityShowIndex = 0)
//    public Mono<JsonResult> testFindByIds(String ids, boolean isQueryRef) throws Exception {
//        service.findByIds(ids, isQueryRef);
//        Future<List<MongoDBTest>> task = RpcContext.getContext().getFuture();
//        if (task == null) {
//            return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//        }
//        return Mono.just(task.get()).map(result -> JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, result));
//    }
//
//    @ApiOperation(value = "testFindAll", notes = "testFindAll", httpMethod = "POST", produces = "application/json")
//    @PostMapping("/testFindAll")
//    @AuthorityRegister(authorityName = "testFindAll", authorityType = AuthorityTypeEnum.URL,
//            authorityShowIndex = 0)
//    public Mono<JsonResult> testFindAll(boolean isQueryRef) throws Exception {
//        service.findAll(isQueryRef);
//        Future<List<MongoDBTest>> task = RpcContext.getContext().getFuture();
//        if (task == null) {
//            return Mono.just(JsonResult.sendFailedResult("服务调用失败", null));
//        }
//        return Mono.just(task.get()).map(result -> JsonResult.sendSuccessfulResult(CallBackMessage.querySuccess, result));
//    }
//
//    @ApiOperation(value = "测试SpringSecurity获取用户", notes = "SecurityContextHolder 方式", httpMethod = "GET", produces = "application/json")
//    @GetMapping("/testSpringSecurityUser1")
//    @AuthorityRegister(authorityName = "测试SpringSecurity获取用户", authorityType = AuthorityTypeEnum.URL,
//            authorityShowIndex = 0)
//    @ResponseBody
//    public Mono<Long> testSpringSecurityUser1(@ApiIgnore ServerWebExchange exchange) throws Exception {
//        return Mono.just(TokenUtil.getUserId(exchange.getRequest()));
//    }
//
//
//    @ApiOperation(value = "测试SpringSecurity获取用户", notes = "控制器方法参数 Authentication 方式", httpMethod = "GET", produces = "application/json")
//    @GetMapping("/testSpringSecurityUser2")
//    @AuthorityRegister(authorityName = "测试SpringSecurity获取用户", authorityType = AuthorityTypeEnum.URL,
//            authorityShowIndex = 0)
//    @ResponseBody
//    public Mono<String> testSpringSecurityUser3(@ApiIgnore JwtAuthenticationToken token) throws Exception {
//        String userName = token.getToken().getClaimAsString("user_name");
//        String userId = token.getToken().getClaimAsString("userId");
//        return Mono.just(userName).zipWith(Mono.just(userId), (username2, userId2) -> {
//            return username2 + "====" + userId2;
//        });
//    }

}
