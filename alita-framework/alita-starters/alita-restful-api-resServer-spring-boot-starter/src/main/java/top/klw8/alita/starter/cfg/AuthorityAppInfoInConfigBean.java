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
import top.klw8.alita.entitys.authority.AlitaAuthoritysApp;

/**
 * 配制中的app信息
 * 2020/7/21 16:31
 */
@Getter
public class AuthorityAppInfoInConfigBean {

    private String appTag;

    private String appName;

    private String remark;

    private AlitaAuthoritysApp appEntity;

    public AuthorityAppInfoInConfigBean(String appTag, String appName, String remark){
        this.appTag = appTag;
        this.appName = appName;
        this.remark = remark;
        this.appEntity = new AlitaAuthoritysApp();
        this.appEntity.setAppTag(appTag);
        this.appEntity.setAppName(appName);
        this.appEntity.setRemark(remark);
    }

}
