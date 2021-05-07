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
package top.klw8.alita.starter.annotations;

import top.klw8.alita.entitys.authority.AlitaAuthoritysCatlog;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 将权限目录写入到数据库中,需要通过 /devHelper/registeAllAuthority 注册
 * 相关参数含义可参考 {@link AlitaAuthoritysCatlog}
 * 2018年12月11日 下午1:26:24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface AuthorityCatlogRegister {

    /**
     * 菜单所属权限目录的名称,如果不存在自动创建
     * @return
     */
    String name();
    
    /**
     * 菜单的显示顺序
     * @return
     */
    int showIndex();
    
    /**
     * 菜单备注,可不设置
     * @return
     */
    String remark() default "";
    
}
