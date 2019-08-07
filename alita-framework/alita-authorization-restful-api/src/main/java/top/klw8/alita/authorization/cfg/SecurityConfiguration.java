package top.klw8.alita.authorization.cfg;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import top.klw8.alita.authorization.common.AlitaUserDetailsService;
import top.klw8.alita.service.api.user.IAlitaUserService;

/**
 * @ClassName: SecurityConfiguration
 * @Description: Security 配制
 * @author klw
 * @date 2018年11月1日 下午5:49:06
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    
    @Reference(async=true)
    private IAlitaUserService userService;
    
    @Bean
    @Override
    protected UserDetailsService userDetailsService(){
        return new AlitaUserDetailsService(userService);
    }
   
    @Override
    protected void configure(HttpSecurity http) throws Exception {
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
