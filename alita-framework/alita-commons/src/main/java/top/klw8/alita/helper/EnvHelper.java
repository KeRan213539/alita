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
