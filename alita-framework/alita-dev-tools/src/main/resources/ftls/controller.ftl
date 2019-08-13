package ${package};

<#if isGenerateApi>
import org.springframework.web.bind.annotation.PostMapping;

import MongoDBConstant;
import ForPageMode.FieldDataType;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.web.base.WebapiCrudBaseController;
import top.klw8.alita.starter.web.base.enums.SearchTypeEnum;
import top.klw8.alita.starter.web.common.JsonResult;
import top.klw8.alita.validator.UseValidator;
import IBaseService;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Mono;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
<#else>
import top.klw8.alita.starter.web.base.WebapiBaseController;
</#if>
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.klw8.alita.starter.annotations.AuthorityCatlogRegister;
import org.apache.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
<#list importList as importStr>
import ${importStr};
</#list>

/**
 * @ClassName: ${className}
 * @Description: ${classComment} 的 API
 * @author dev-tools
 * @date ${.now?string["yyyy年MM月dd日 HH:mm:ss"]}
 */
@Api(tags = {"alita-restful-API--${classComment}"})
@RestController
@RequestMapping("/${baseUri}")
@AuthorityCatlogRegister(name = "${classComment}", showIndex = ${catlogIndex}, remark = "${classComment}")
@Slf4j
<#if isGenerateApi>
public class ${className} extends WebapiCrudBaseController<${entityName}> {
<#else>
public class ${className} extends WebapiBaseController {
</#if>

    @Reference(async=true)
    private ${serviceInterfaceName} service;

<#if isGenerateApi>    
    @Override
    protected IBaseService<${entityName}> service() {
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
    
    <#if isGenerateAddApi>
    @ApiOperation(value = "新增保存", notes = "新增保存", httpMethod = "POST", produces = "application/json")
    @PostMapping("/save")
    @AuthorityRegister(authorityName = "新增保存", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> addSave(${addVoName} vo) throws Exception {
        return super.addSave(vo);
    }
    </#if>
    
    <#if isGenerateModifyApi>
    @ApiOperation(value = "修改保存", notes = "修改保存", httpMethod = "POST", produces = "application/json")
    @PostMapping("/modify")
    @AuthorityRegister(authorityName = "修改保存", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> modifySave(${modifyVoName} vo) throws Exception {
        return super.modifySave(vo);
    }
    </#if>
    
    <#if isGenerateList>
    @ApiOperation(value = "获取列表(不分页,支持条件), geo查询不加排序条件时默认由近及远排序", notes = "获取列表(不分页,支持条件)", httpMethod = "POST", produces = "application/json")
    @PostMapping("/list")
    @AuthorityRegister(authorityName = "获取列表(不分页,支持条件)", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> findByEntity(${listVoName} vo, boolean isQueryRef) throws Exception {
        return super.findByEntity(vo, isQueryRef);
    }
    </#if>
    
    <#if isGenerateSkipPageApi>
    @ApiOperation(value = "分页查询skip方式, geo查询不加排序条件时默认由近及远排序", notes = "分页查询skip方式", httpMethod = "POST", produces = "application/json")
    @PostMapping("/queryForPageBySkip")
    @AuthorityRegister(authorityName = "分页查询skip方式", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> queryForPageBySkip(${pageVoName} vo, boolean isQueryRef) throws Exception {
        return super.queryForPageBySkip(vo, isQueryRef);
    }
    </#if>
    
    <#if isGenerateComparativePageApi>
    @ApiOperation(value = "分页查询比较方式, geo查询不加排序条件时默认由近及远排序", notes = "分页查询比较方式", httpMethod = "POST", produces = "application/json")
    @PostMapping("/queryForPageByComparative")
    @AuthorityRegister(authorityName = "分页查询比较方式", authorityType = AuthorityTypeEnum.URL, authorityShowIndex = 0)
    @UseValidator
    public Mono<JsonResult> queryForPageByComparative(${comparativePageVoName} vo, boolean isQueryRef) throws Exception {
        return super.queryForPageByComparative(vo, isQueryRef);
    }
    </#if>
</#if>
}