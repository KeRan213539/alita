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
package top.klw8.alita.service.authority;

import com.baomidou.mybatisplus.extension.service.IService;
import top.klw8.alita.entitys.authority.AlitaAuthoritysCatlog;

/**
 * @ClassName: ISystemAuthoritysCatlogService
 * @Description: 权限的目录Service
 * @author klw
 * @date 2018年11月28日 下午3:48:33
 */
public interface ISystemAuthoritysCatlogService extends IService<AlitaAuthoritysCatlog> {

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据目录名称和appTag查找目录
     * @Date 2020/7/22 17:12
     * @param: catlogName
     * @param: appTag
     * @return top.klw8.alita.entitys.authority.SystemAuthoritysCatlog
     */
    AlitaAuthoritysCatlog findByCatlogNameAndAppTag(String catlogName, String appTag);

}
