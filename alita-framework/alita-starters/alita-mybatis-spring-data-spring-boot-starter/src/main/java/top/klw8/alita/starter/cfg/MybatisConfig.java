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

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Mybatis配制
 * 2019/8/8 11:27
 */
@EnableTransactionManagement
@Configuration
@Slf4j
public class MybatisConfig {

    /**
     * mybatis-plus 分页插件(< v3.4.0)
     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        // paginationInterceptor.setLimit(你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制);
//        return paginationInterceptor;
//    }

    @Value("${alita.mybatisPlus.dbType:}")
    private String myBatisDbTypeStr;

    /**
     * mybatis-plus 分页插件(>= v3.4.0)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        DbType myBatisDbType = DbType.MYSQL;
        if (StringUtils.isBlank(myBatisDbTypeStr)) {
            log.warn("!!! alita.mybatisPlus.dbType 未设置,将使用默认类型【MYSQL】!!!");
        } else {
            try {
                myBatisDbType = DbType.valueOf(myBatisDbTypeStr.toUpperCase());
            } catch (IllegalArgumentException ex) {
                log.error("!!! alita.mybatisPlus.dbType 设置的值不正确(参考: " +
                        "com.baomidou.mybatisplus.annotation.DbType),将使用默认类型【MYSQL】!!!");
            }
        }
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(myBatisDbType));
        return interceptor;
    }

    /**
     * 防止 修改与删除时对全表进行操作(防止未加条件的 UPDATE 和 DELETE 操作
     */
    @Bean
    public BlockAttackInnerInterceptor blockAttackInnerInterceptor(){
        return new BlockAttackInnerInterceptor();
    }

}
