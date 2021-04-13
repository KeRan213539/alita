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
package top.klw8.alita.starter.authorization.web.dateEditor;

import top.klw8.alita.utils.LocalDateTimeUtil;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 自定义 LocalDate 编辑器
 * 2019年1月31日 下午6:41:58
 */
public class CustomLocalDateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(LocalDateTimeUtil.formatToLD(text));
    }
    
    @Override
    public String getAsText() {
	LocalDate date = (LocalDate)getValue();
	return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    
}
