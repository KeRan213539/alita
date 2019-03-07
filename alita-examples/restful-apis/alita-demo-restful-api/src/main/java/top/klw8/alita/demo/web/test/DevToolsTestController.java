package top.klw8.alita.demo.web.test;

import org.springframework.web.bind.annotation.PostMapping;

import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Mono;
import top.klw8.alita.base.mongodb.MongoDBConstant;
import top.klw8.alita.demo.web.test.vo.AddSaveVo;
import top.klw8.alita.demo.web.test.vo.ComparativePage;
import top.klw8.alita.demo.web.test.vo.ListVo;
import top.klw8.alita.demo.web.test.vo.ModifySaveVo;
import top.klw8.alita.demo.web.test.vo.SkipPageVo;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.demo.DevToolsTestEntity;
import top.klw8.alita.service.base.api.IBaseService;
import top.klw8.alita.service.base.dao.prarm.ForPageMode.FieldDataType;
import top.klw8.alita.service.test.IDevToolsTestService;
import top.klw8.alita.starter.annotations.AuthorityCatlogRegister;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.web.base.WebapiCrudBaseController;
import top.klw8.alita.starter.web.base.enums.SearchTypeEnum;
import top.klw8.alita.starter.web.common.JsonResult;
import top.klw8.alita.validator.UseValidator;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: DevToolsTestController
 * @Description: 代码生成器测试用 的 API
 * @author dev-tools
 * @date 2019年03月05日 11:44:29
 */
@Api(tags = {"alita-restful-API--代码生成器测试用"})
@RestController
@RequestMapping("/test")
@AuthorityCatlogRegister(name = "代码生成器测试用", showIndex = 999, remark = "代码生成器测试用")
@Slf4j
public class DevToolsTestController extends WebapiCrudBaseController<DevToolsTestEntity> {

    @Reference(async=true)
    private IDevToolsTestService service;

    @Override
    protected IBaseService<DevToolsTestEntity> service() {
        return service;
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
        return null;
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
        return "location";
    }

    @Override
    protected double geoRangeKm() {
        return 7D;
    }
    
    @ApiOperation(value = "新增保存", notes = "新增保存", httpMethod = "POST", produces = "application/json")
    @PostMapping("/save")
    @AuthorityRegister(authorityName = "新增保存", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> addSave(AddSaveVo vo) throws Exception {
        return super.addSave(vo);
    }
    
    @ApiOperation(value = "修改保存", notes = "修改保存", httpMethod = "POST", produces = "application/json")
    @PostMapping("/modify")
    @AuthorityRegister(authorityName = "修改保存", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> modifySave(ModifySaveVo vo) throws Exception {
        return super.modifySave(vo);
    }
    
    @ApiOperation(value = "获取列表(不分页,支持条件), geo查询不加排序条件时默认由近及远排序", notes = "获取列表(不分页,支持条件)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/list")
    @AuthorityRegister(authorityName = "获取列表(不分页,支持条件)", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> findByEntity(ListVo vo, boolean isQueryRef) throws Exception {
        return super.findByEntity(vo, isQueryRef);
    }
    
    @ApiOperation(value = "分页查询skip方式, geo查询不加排序条件时默认由近及远排序", notes = "分页查询skip方式", httpMethod = "POST", produces = "application/json")
    @PostMapping("/queryForPageBySkip")
    @AuthorityRegister(authorityName = "分页查询skip方式", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> queryForPageBySkip(SkipPageVo vo, boolean isQueryRef) throws Exception {
        return super.queryForPageBySkip(vo, isQueryRef);
    }
    
    @ApiOperation(value = "分页查询比较方式, geo查询不加排序条件时默认由近及远排序", notes = "分页查询比较方式", httpMethod = "POST", produces = "application/json")
    @PostMapping("/queryForPageByComparative")
    @AuthorityRegister(authorityName = "分页查询比较方式", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> queryForPageByComparative(ComparativePage vo, boolean isQueryRef) throws Exception {
        return super.queryForPageByComparative(vo, isQueryRef);
    }
}