package top.klw8.alita.starter.authorization.cfg;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 登录和刷新token的拦截器.
 *
 * @author klw(213539 @ qq.com)
 * @ClassName: TokenEndpointFilter
 * @date 2020/9/8 17:17
 */
public interface TokenEndpointFilter {
    
    void doFilter(ServletRequest var1, ServletResponse var2, FilterChain var3) throws IOException, ServletException;

}
