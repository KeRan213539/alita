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
package top.klw8.alita.fileupload.cfg.resultCode;

import lombok.Getter;
import top.klw8.alita.service.result.IResultCode;

/**
 * 返回给前端的Code定义--类别
 * 2019/6/6 16:50
 */
@Getter
public enum ResultCodeEnum implements IResultCode {

    FILE_UPLOAD("101", "文件上传"),

    ;

    /**
     * 类别的code(3位)
     */
    private String code;


    /**
     * 类别名称
     */
    private String codeName;

    ResultCodeEnum(String code, String codeName){
        this.code = code;
        this.codeName = codeName;
    }

}
