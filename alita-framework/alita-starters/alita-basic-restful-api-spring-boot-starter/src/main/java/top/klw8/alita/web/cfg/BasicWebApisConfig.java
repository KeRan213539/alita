package top.klw8.alita.web.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.klw8.alita.web.authority.AuthorityAdminController;
import top.klw8.alita.web.authority.AuthorityAppController;
import top.klw8.alita.web.user.SysUserAdminController;
import top.klw8.alita.web.user.SysUserController;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: BasicWebApisConfig
 * @Description: 基础web接口配制
 * @date 2019/11/4 14:14
 */
@Configuration
@Import({AuthorityAdminController.class, SysUserAdminController.class, SysUserController.class, AuthorityAppController.class})
public class BasicWebApisConfig {
}
