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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.service.result.code.CommonResultCodeEnum;


/**
 * alita统一消息返回格式包装
 * 2018年12月7日 下午1:36:48
 */
@Getter
@Setter
@ToString
public class JsonResult<T> implements java.io.Serializable {

    /**
     * 响应状态码
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    public JsonResult(){}

    public JsonResult(ISubResultCode subResultCode){
        this.code = subResultCode.getCode();
        this.message = subResultCode.getCodeMsg();
    }

    public JsonResult(ISubResultCode subResultCode, T data){
        this.code = subResultCode.getCode();
        this.message = subResultCode.getCodeMsg();
        this.data = data;
    }

    public JsonResult(ISubResultCode subResultCode, String message, T data){
        this.code = subResultCode.getCode();
        this.message = message;
        this.data = data;
    }

    public static JsonResult successfu() {
        return new JsonResult(CommonResultCodeEnum.OK, null);
    }

    public static <T> JsonResult successfu(T data) {
        return new JsonResult(CommonResultCodeEnum.OK, data);
    }

    public static <T>  JsonResult successfu(String message) {
        return new JsonResult(CommonResultCodeEnum.OK, message, null);
    }

    public static <T>  JsonResult successfu(String message, T data) {
        return new JsonResult(CommonResultCodeEnum.OK, message, data);
    }

    public static <T> JsonResult failed(ISubResultCode subResultCode, T data) {
        return new JsonResult(subResultCode, data);
    }

    public static JsonResult failed(ISubResultCode subResultCode) {
        return new JsonResult(subResultCode);
    }

    /**
     * 发送自定义code的和消息的Result
     * 2019/10/15 17:28
     * @param: subResultCode
     * @param: errorMsg
     * @return top.klw8.alita.service.result.JsonResult
     */
    public static JsonResult failed(ISubResultCode subResultCode, String errorMsg) {
        return new JsonResult(subResultCode, errorMsg, null);
    }

    /**
     * 发送code为500的Result
     * 2019/10/15 17:28
     * @param: errorMsg
     * @return top.klw8.alita.service.result.JsonResult
     */
    public static JsonResult failed(String errorMsg) {
        return new JsonResult(CommonResultCodeEnum.ERROR, errorMsg, null);
    }

    /**
     * 发送参数错误Result
     * 2019/10/15 17:11
     * @param: errorMsg
     * @return top.klw8.alita.service.result.JsonResult
     */
    public static JsonResult badParameter(String errorMsg) {
        return new JsonResult(CommonResultCodeEnum.BAD_PARAMETER, errorMsg, null);
    }

}
