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
package top.klw8.alita.starter.authorization.web.base;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import top.klw8.alita.starter.authorization.web.dateEditor.CustomLocalDateEditor;
import top.klw8.alita.starter.authorization.web.dateEditor.CustomLocalDateTimeEditor;
import top.klw8.alita.starter.authorization.web.dateEditor.MyCustomDateEditor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * web api 用的 无操作BaseController
 * 2018年10月26日 下午3:32:04
 */
public class WebapiBaseController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
	// 自动去除空格
	binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Date.class, new MyCustomDateEditor());
        binder.registerCustomEditor(LocalDate.class, new CustomLocalDateEditor());
        binder.registerCustomEditor(LocalDateTime.class, new CustomLocalDateTimeEditor());
    }
    
}
