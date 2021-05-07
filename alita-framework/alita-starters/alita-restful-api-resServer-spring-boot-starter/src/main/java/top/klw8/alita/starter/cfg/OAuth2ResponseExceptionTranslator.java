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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.result.code.CommonResultCodeEnum;


/**
 * token返回消息转换器
 * 2018年12月7日 下午2:34:34
 */
public class OAuth2ResponseExceptionTranslator implements WebResponseExceptionTranslator<JsonResult> {

    @Override
    public ResponseEntity<JsonResult> translate(Exception e) {
        Throwable throwable = e.getCause();
        if (throwable instanceof InvalidTokenException) {
            return new ResponseEntity<>(JsonResult.failed(CommonResultCodeEnum.TOKEN_ERR), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(JsonResult.failed(CommonResultCodeEnum.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
