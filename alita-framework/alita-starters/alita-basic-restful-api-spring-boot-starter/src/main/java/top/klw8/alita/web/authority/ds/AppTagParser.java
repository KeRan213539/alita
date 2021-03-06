/*
 * Copyright 2018-2021, ranke (213539@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.web.authority.ds;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.klw8.alita.starter.aures.IResourceParser;
import top.klw8.alita.starter.aures.IResourceParserData;
import top.klw8.alita.starter.aures.ResourceParserResult;

import java.util.List;

/**
 * 从参数中解析appTag的资源权限解析器
 * 2020/7/27 14:47
 */
@Component
public class AppTagParser extends DsBaseParser implements IResourceParser {

    @Value("${alita.authority.authoritysResource.enable:false}")
    private boolean auDsEnable;

    private final String APP_TAG_KEY = "appTag";

    @Override
    public ResourceParserResult parseResource(IResourceParserData parserPojo) {
        if(!auDsEnable){
            // 没有开启数据验证,直接返回万能钥匙
            return new ResourceParserResult(true);
        }
        String appTag = null;

        //从表单参数拿 appTag
        List<String> formDataList = parserPojo.getFormData(APP_TAG_KEY);
         if(CollectionUtils.isNotEmpty(formDataList)){
            appTag = formDataList.get(0);
        }

        //表单参数没有,从url参数中拿
        if(StringUtils.isBlank(appTag)){
            List<String> queryPrarmList = parserPojo.getQueryPrarm(APP_TAG_KEY);
            if(CollectionUtils.isNotEmpty(queryPrarmList)){
                appTag = queryPrarmList.get(0);
            }
        }

        //还没有, 从 路径参数 中拿
        if(StringUtils.isBlank(appTag)){
            appTag = parserPojo.getPathPrarm(APP_TAG_KEY);
        }

        // 还没有, 从body中的json中拿
        if(StringUtils.isBlank(appTag)){
            String jsonString = parserPojo.getJsonString();
            if(StringUtils.isNotBlank(jsonString)){
                JSONObject jsonObject = JSON.parseObject(jsonString);
                appTag = jsonObject.getString(APP_TAG_KEY);
            }
        }

        if(StringUtils.isBlank(appTag)){
            // 还是拿不到,说明没传
            return new ResourceParserResult();
        } else {
            // 拿到了,给出去
            return new ResourceParserResult(new String[]{appTag});
        }

    }

}
