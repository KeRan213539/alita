package top.klw8.alita.providers.admin.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import top.klw8.alita.entitys.authority.SystemAuthoritysAppChannel;
import top.klw8.alita.service.api.authority.IAuthorityAppChannelProvider;
import top.klw8.alita.service.authority.IAuthorityAppChannelService;

import java.util.List;

/**
 * 应用渠道表 provider 实现.
 *
 * @author klw(213539 @ qq.com)
 * @ClassName: AuthorityAppChannelProviderImpl
 * @date 2020/9/9 17:43
 */
@Slf4j
@Service
public class AuthorityAppChannelProviderImpl implements IAuthorityAppChannelProvider {
    
    @Autowired
    private IAuthorityAppChannelService channelService;
    
    public List<SystemAuthoritysAppChannel> allChannel() {
        return channelService.list();
    }

}
