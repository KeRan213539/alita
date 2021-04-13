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
package top.klw8.alita.entitys.authority.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.io.Serializable;

/**
 * 权限类型
 * 2018年11月28日 下午3:01:49
 */
public enum AuthorityTypeEnum implements IEnum {

    /**
     * MENU : 菜单(作为菜单显示)
     */
    MENU,
    
    /**
     * URL : URL
     */
    URL;

    @Override
    public Serializable getValue() {
        return this.name();
    }
}
