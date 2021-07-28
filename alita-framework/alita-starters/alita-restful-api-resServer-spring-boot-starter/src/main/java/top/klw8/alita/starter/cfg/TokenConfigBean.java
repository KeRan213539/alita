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
package top.klw8.alita.starter.cfg;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * alita.oauth2.token 配制.
 *
 * 2020/9/11 11:10
 */
@ConfigurationProperties(prefix="alita.oauth2.token")
public class TokenConfigBean {
    
    @Getter
    @Setter
    private boolean storeInRedis = false;
    
    private List<String> defaultCheckExcludePaths = Arrays
            .asList(new String[] {"/devHelper/**", "/swagger-ui.html**", "/webjars/**", "/swagger-ui/**",
                    "/swagger-resources/**", "/static/**", "/js/**"});
    
    private List<String> mergedCheckExcludePaths = defaultCheckExcludePaths;
    
    public List<String> getCheckExcludePaths(){
        return this.mergedCheckExcludePaths;
    }
    
    public void setCheckExcludePaths(List<String> list){
        if(CollectionUtils.isEmpty(list)) {
            mergedCheckExcludePaths = defaultCheckExcludePaths;
        } else {
            mergedCheckExcludePaths = new ArrayList<>(defaultCheckExcludePaths.size() + list.size());
            mergedCheckExcludePaths.addAll(defaultCheckExcludePaths);
            mergedCheckExcludePaths.addAll(list);
        }
    }
    
}
