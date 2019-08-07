package ${package};

<#if voType == "vo">
import top.klw8.alita.starter.web.base.vo.IBaseCrudVo;
<#elseif voType == "addApi" || voType == "modifyApi">
import top.klw8.alita.starter.web.base.vo.IBaseCrudVo;
<#elseif voType == "listApi">
import top.klw8.alita.starter.web.base.vo.ListPrarmVo;
<#elseif voType == "skipPageApi">
import top.klw8.alita.starter.web.base.vo.PagePrarmVo;
<#elseif voType == "comparativePageApi">
import top.klw8.alita.starter.web.base.vo.ComparativePagePrarmVo;
</#if>
<#list importList as importStr>
import ${importStr};
</#list>
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName: ${className}
 * @Description: ${classComment} 的 vo
 * @author dev-tools
 * @date ${.now?string["yyyy年MM月dd日 HH:mm:ss"]}
 */
@Getter
@Setter
@ToString
<#if voType == "vo">
public class ${className} implements IBaseCrudVo<${entityName}> {
<#elseif voType == "addApi" || voType == "modifyApi">
public class ${className} implements IBaseCrudVo<${entityName}> {
<#elseif voType == "listApi">
public class ${className} extends ListPrarmVo<${entityName}> {
<#elseif voType == "skipPageApi">
public class ${className} extends PagePrarmVo<${entityName}> {
<#elseif voType == "comparativePageApi">
public class ${className} extends ComparativePagePrarmVo<${entityName}> {
</#if>
    private static final long serialVersionUID = 1L;
    
    <#list fieldList as field>
    @ApiModelProperty(value = "${field.comment}")
    private ${field.type} ${field.name};
    
    </#list>
    @Override
    public ${entityName} buildEntity() {
        ${entityName} e = new ${entityName}();
        <#list fieldList as field>
        e.set${field.name?cap_first}(${field.name});
        </#list>
        return e;
    }
}