package top.klw8.alita.starter.common;

import org.springframework.core.env.Environment;
import org.springframework.web.context.support.StandardServletEnvironment;

import top.klw8.alita.base.springctx.SpringApplicationContextUtil;

/**
 * @ClassName: EnvHelper
 * @Description: 系统环境帮助
 * @author klw
 * @date 2019年1月10日 下午5:21:46
 */
public class EnvHelper {

    private static Boolean dev = null;

    static {
	Environment env = (Environment) SpringApplicationContextUtil.getBean(StandardServletEnvironment.class);
	String[] activeprofiles = env.getActiveProfiles();
	dev = Boolean.FALSE;
	for (String activeprofile : activeprofiles) {
	    if (activeprofile.equals("dev")) {
		dev = Boolean.TRUE;
		break;
	    }
	}
    }

    /**
     * @Title: isDev
     * @author klw
     * @Description: 是否开发者模式
     * @return
     */
    public static boolean isDev() {
	return dev.booleanValue();
    }

}
