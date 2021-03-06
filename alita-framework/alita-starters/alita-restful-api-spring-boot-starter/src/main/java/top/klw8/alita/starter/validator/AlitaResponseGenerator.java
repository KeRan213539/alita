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
package top.klw8.alita.starter.validator;

import reactor.core.publisher.Mono;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.result.code.CommonResultCodeEnum;
import top.klw8.alita.validator.IResponseMsgGenerator;
import top.klw8.alita.validator.ValidatorException;

/**
 * response 生成器
 * 2018年9月17日 下午5:20:43
 */
public class AlitaResponseGenerator implements IResponseMsgGenerator {

    /*
     * <p>Title: generatorResponse</p>
     * <p>Description: </p>
     * @param code
     * @param message
     * @return
     * @see top.klw8.alita.validator.IResponseMsgGenerator#generatorResponse(java.lang.String, java.lang.String)
     */
    @Override
    public Mono<Object> generatorResponse(String code, String message, ValidatorException ex) {
        JsonResult result = new JsonResult();
        result.setCode(code);
        result.setMessage(message);
        return Mono.just(result);
    }

}
