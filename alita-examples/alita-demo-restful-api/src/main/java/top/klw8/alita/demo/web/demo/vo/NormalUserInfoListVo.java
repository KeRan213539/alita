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
package top.klw8.alita.demo.web.demo.vo;

import top.klw8.alita.entitys.demo.mongo.ExtUserInfoDemo;
import top.klw8.alita.starter.web.base.vo.ListPrarmVo;

/**
 * 查询客户列表(不分页)VO
 * 2019年1月31日 下午2:23:32
 */
public class NormalUserInfoListVo extends ListPrarmVo<ExtUserInfoDemo> {

    private static final long serialVersionUID = -8753740521433616073L;

    @Override
    public ExtUserInfoDemo buildEntity() {
	return new ExtUserInfoDemo();
    }

}
