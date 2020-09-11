package top.klw8.alita.service.api.authority;

import top.klw8.alita.entitys.authority.SystemAuthoritysAppChannel;

import java.util.List;

/**
 * 应用渠道表 provider.
 *
 * @author klw(213539 @ qq.com)
 * @ClassName: IAuthorityAppChannelProvider
 * @date 2020/9/9 17:42
 */
public interface IAuthorityAppChannelProvider {
    
    List<SystemAuthoritysAppChannel> allChannel();
    
}
