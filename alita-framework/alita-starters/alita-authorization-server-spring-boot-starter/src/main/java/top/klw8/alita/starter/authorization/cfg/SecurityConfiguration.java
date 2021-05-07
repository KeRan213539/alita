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
package top.klw8.alita.starter.authorization.cfg;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.starter.authorization.common.AlitaUserDetailsService;
import top.klw8.alita.starter.authorization.filter.BaseFilter;

import java.util.Map;

/**
 * Security 配制
 * 2018年11月1日 下午5:49:06
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @DubboReference(async = true)
    private IAlitaUserProvider userService;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new AlitaUserDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Map<String, BaseFilter> filters = applicationContext.getBeansOfType(BaseFilter.class);
        if(filters != null && !filters.isEmpty()){
            filters.forEach((key, value) -> {
                http.addFilterBefore(value, UsernamePasswordAuthenticationFilter.class);
            });
        }
        http.csrf().disable();  // 认证服务器只有认证功能,都是对外开放的接口,所以保护直接关了
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
//	return new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence charSequence) {
//                return charSequence.toString();
//            }
// 
//            @Override
//            public boolean matches(CharSequence charSequence, String s) {
//                return Objects.equals(charSequence.toString(),s);
//            }
//        };
    }


}
