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
 * @ClassName: ISubResultCode
 * @Description: 业务ResultCode需要实现的接口
 * @date 2019/6/6 17:06
 */
public interface ISubResultCode {

    /**
     * @author klw
     * @Description: 获取完整code(分类+业务)
     * @Date 2019/6/6 17:11
     * @Param []
     * @return java.lang.String
     */
    String getCode();

    /**
     * @author klw
     * @Description: 获取code对应的消息
     * @Date 2019/6/6 17:11
     * @Param []
     * @return java.lang.String
     */
    String getCodeMsg();

    /**
     * @author klw
     * @Description: 获取类别
     * @Date 2019/6/10 12:55
     * @Param []
     * @return com.ejkj.common.resultCode.ResultCodeEnum
     */
    IResultCode getClassify();

}
