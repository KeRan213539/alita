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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import top.klw8.alita.entitys.authority.AlitaAuthoritysCatlog;
import top.klw8.alita.entitys.authority.AlitaAuthoritysMenu;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.starter.auscan.IAuthoritysResourceSource;
import top.klw8.alita.starter.auscan.IAuthoritysResourceSourceItem;


/**
 * 将权限写入到数据库中,需要通过 /devHelper/registeAllAuthority 注册
 * 相关参数含义可参考 {@link AlitaAuthoritysMenu}
 * {@link AlitaAuthoritysCatlog}
 * 2018年12月7日 下午4:58:53
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface AuthorityRegister {

    /**
     * 菜单所属权限目录的名称,如果不存在自动创建
     * @return
     */
    String catlogName() default "";
    
    /**
     * 菜单的显示顺序
     * @return
     */
    int catlogShowIndex() default -1;

    /**
     * 菜单备注,可不设置
     * @return
     */
    String catlogRemark() default "";
    
    /**
     * 权限名称
     * @return
     */
    String authorityName();
    
    /**
     * 权限类型
     * @return
     */
    AuthorityTypeEnum authorityType();
    
    /**
     * 作为菜单的显示顺序,非菜单为0
     * @return
     */
    int authorityShowIndex();
    
    /**
     * 权限备注
     * @return
     */
    String authorityRemark() default "";

    /**
     * 静态资源权限来源, 实现该接口的来源结果在权限扫描时存入数据库.
     * 与 {@link #authoritysResourceSourceEnum()} 不冲突, 两者都处理
     */
    Class<? extends IAuthoritysResourceSource> authoritysResourceSource() default IAuthoritysResourceSource.class;

    /**
     * 枚举类型的静态数据来源, 实现该接口的枚举在权限扫描时存入数据库.
     * 与 {@link #authoritysResourceSource()} 不冲突, 两者都处理
     */
    Class<? extends IAuthoritysResourceSourceItem> authoritysResourceSourceEnum() default IAuthoritysResourceSourceItem.class;

}
