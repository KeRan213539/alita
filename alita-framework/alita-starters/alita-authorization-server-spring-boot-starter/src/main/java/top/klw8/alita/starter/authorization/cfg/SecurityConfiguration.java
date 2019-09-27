package top.klw8.alita.starter.authorization.cfg;

import org.apache.dubbo.config.annotation.Reference;
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
 * @author klw
 * @ClassName: SecurityConfiguration
 * @Description: Security 配制
 * @date 2018年11月1日 下午5:49:06
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Reference(async = true)
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
    public static PasswordEncoder passwordEncoder() {
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
