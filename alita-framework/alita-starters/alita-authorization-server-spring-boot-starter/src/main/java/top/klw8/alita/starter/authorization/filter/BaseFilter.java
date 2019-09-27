package top.klw8.alita.starter.authorization.filter;

import org.springframework.web.filter.OncePerRequestFilter;
import top.klw8.alita.starter.authorization.utils.JacksonUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author klw
 * @ClassName: BaseFilter
 * @Description: 拦截器基类
 * @date 2019-09-24 20:20:58
 */
public abstract class BaseFilter extends OncePerRequestFilter {

    protected void sendJsonStr(HttpServletResponse response, Object obj) {
        try {
            sendJsonStr(response, JacksonUtil.writeValueAsString(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendJsonStr(HttpServletResponse response, String str) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        java.io.PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(str);
            pw.flush();
        } catch (Exception e) {
            //
        } finally {
            pw.close();
        }
    }

    protected Cookie getCookieByName(HttpServletRequest req, String name) {
        Cookie[] cookies = req.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
