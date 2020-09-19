package top.klw8.alita.web.authority.ds;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.entitys.authority.SystemDataSecured;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.service.api.authority.IAuthorityAdminDataSecuredProvider;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;
import top.klw8.alita.starter.datasecured.IResourceParser;
import top.klw8.alita.starter.datasecured.IResourceParserData;
import top.klw8.alita.starter.datasecured.ResourceParserResult;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AppTagByIdParser
 * @Description: 根据传入的Id查询并返回对应的appTag
 * @date 2020/7/27 14:50
 */
@Component
public class AppTagByIdParser extends DsBaseParser implements IResourceParser {

    @Value("${alita.authority.dataSecured.enable:false}")
    private boolean auDsEnable;

    @Reference
    private IAuthorityAdminDataSecuredProvider adminDataSecuredProvider;

    @Override
    public ResourceParserResult parseResource(IResourceParserData parserPojo) {
        if(!auDsEnable){
            // 没有开启数据验证,直接返回万能钥匙
            return new ResourceParserResult(true);
        }
        String requestUrl = parserPojo.getRequestUrl();
        String appTag = null;
        if(StringUtils.contains(requestUrl, "/admin/au/roleInfo") ||
                StringUtils.contains(requestUrl, "/admin/au/delRole")){
            List<String> queryPrarmList = parserPojo.getQueryPrarm("roleId");
            if(CollectionUtils.isNotEmpty(queryPrarmList)){
                String roleId = queryPrarmList.get(0);
                if(StringUtils.isNotBlank(roleId)){
                    SystemRole role = adminDataSecuredProvider.roleById(roleId);
                    appTag = role.getAppTag();
                }
            }
        } else if(StringUtils.contains(requestUrl, "/admin/au/delCatlog") ||
                StringUtils.contains(requestUrl, "/admin/au/catlogInfo")){
            List<String> queryPrarmList = parserPojo.getQueryPrarm("catlogId");
            if(CollectionUtils.isNotEmpty(queryPrarmList)){
                String catlogId = queryPrarmList.get(0);
                if(StringUtils.isNotBlank(catlogId)){
                    SystemAuthoritysCatlog catlog = adminDataSecuredProvider.catlogById(catlogId);
                    appTag = catlog.getAppTag();
                }
            }
        } else if(StringUtils.contains(requestUrl, "/admin/au/delAuthority") ||
                StringUtils.contains(requestUrl, "/admin/au/auInfo")){
            List<String> queryPrarmList = parserPojo.getQueryPrarm("auId");
            if(CollectionUtils.isNotEmpty(queryPrarmList)){
                String auId = queryPrarmList.get(0);
                if(StringUtils.isNotBlank(auId)){
                    SystemAuthoritys au = adminDataSecuredProvider.auById(auId);
                    appTag = au.getAppTag();
                }
            }
        } else if(StringUtils.contains(requestUrl, "/admin/au/dataSecuredInfo") ||
                StringUtils.contains(requestUrl, "/admin/au/delDataSecured")){
            List<String> queryPrarmList = parserPojo.getQueryPrarm("dsId");
            if(CollectionUtils.isNotEmpty(queryPrarmList)){
                String dsId = queryPrarmList.get(0);
                if(StringUtils.isNotBlank(dsId)){
                    SystemDataSecured ds = adminDataSecuredProvider.dsById(dsId);
                    appTag = ds.getAppTag();
                }
            }
        }

        if(StringUtils.isBlank(appTag)){
            return new ResourceParserResult();
        } else {
            return new ResourceParserResult(new String[]{appTag});
        }
    }

}