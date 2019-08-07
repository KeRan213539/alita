package top.klw8.alita.starter.common;

import org.springframework.core.env.Environment;

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
	Environment env = SpringApplicationContextUtil.getBean(Environment.class);  //如果出问题,换成 StandardReactiveWebEnvironment 试试
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
