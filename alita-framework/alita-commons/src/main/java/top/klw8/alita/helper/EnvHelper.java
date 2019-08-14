package top.klw8.alita.helper;

import org.springframework.core.env.Environment;
import top.klw8.alita.base.springctx.SpringApplicationContextUtil;

/**
 * @author freedom
 * @version 1.0
 * @ClassName EnvHelper
 * @Description 系统环境帮助
 * @date 2019-08-14 16:56
 */
public class EnvHelper {

    private static Boolean dev = null;

    static {
        Environment env            = SpringApplicationContextUtil.getBean(Environment.class);  //如果出问题,换成 StandardReactiveWebEnvironment 试试
        String[]    activeprofiles = env.getActiveProfiles();
        dev = Boolean.FALSE;
        for (String activeprofile : activeprofiles) {
            if (activeprofile.equals("dev")) {
                dev = Boolean.TRUE;
                break;
            }
        }
    }

    /**
     * @return
     * @Title: isDev
     * @author klw
     * @Description: 是否开发者模式
     */
    public static boolean isDev() {
        return dev.booleanValue();
    }

}
