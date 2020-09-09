package top.klw8.alita.test;

import org.springframework.stereotype.Component;
import top.klw8.alita.starter.authorization.cfg.TokenEndpointFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * test.
 *
 * @author klw(213539 @ qq.com)
 * @ClassName: TestTokenFilter
 * @date 2020/9/8 16:56
 */
@Component
public class TestTokenFilter implements TokenEndpointFilter {
    
    public TestTokenFilter(){
        System.out.println("===========--TestTokenFilter--================");
    }
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println(servletRequest.getParameterMap());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
