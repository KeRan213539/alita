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
package top.klw8.alita.service.result;

/**
 * @author klw
 * @ClassName: IResultCode
 * @Description: ResultCode分类需要实现的接口
 * @date 2019/6/6 17:06
 */
public interface IResultCode {

    /**
     * @author klw
     * @Description: 获取分类code
     * @Date 2019/6/6 17:11
     * @Param []
     * @return java.lang.String
     */
    String getCode();

    /**
     * @author klw
     * @Description: 获取分类名称
     * @Date 2019/6/6 17:11
     * @Param []
     * @return java.lang.String
     */
    String getCodeName();

}
