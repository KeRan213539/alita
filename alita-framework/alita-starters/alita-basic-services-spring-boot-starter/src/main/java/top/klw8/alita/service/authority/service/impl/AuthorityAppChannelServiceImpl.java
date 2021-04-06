package top.klw8.alita.service.authority.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.klw8.alita.entitys.authority.SystemAuthoritysAppChannel;
import top.klw8.alita.service.authority.IAuthorityAppChannelService;
import top.klw8.alita.service.authority.mapper.IAuthorityAppChannelMapper;

/**
 * 应用渠道表 service 实现.
 *
 * @author klw(213539 @ qq.com)
 * @ClassName: AuthorityAppChannelServiceImpl
 * @date 2020/9/9 16:58
 */
@Slf4j
@Service
public class AuthorityAppChannelServiceImpl extends ServiceImpl<IAuthorityAppChannelMapper, SystemAuthoritysAppChannel>
        implements IAuthorityAppChannelService {

}
